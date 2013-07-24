package net.minecraft.src.biomesoplenty.overrides;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.MapGenMineshaft;
import net.minecraft.src.MapGenScatteredFeature;
import net.minecraft.src.MapGenStronghold;
import net.minecraft.src.MapGenVillage;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.src.SpawnerAnimals;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenDungeons;
import net.minecraft.src.WorldGenLakes;
import net.minecraft.src.biomesoplenty.configuration.BOPBiomes;

public class ChunkProviderGenerateBOP extends ChunkProviderGenerate implements IChunkProvider
{
    /** RNG. */
    private Random rand;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen1;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen2;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen3;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen4;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen5;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    /** Reference to the World object. */
    private World worldObj;

    /** Holds the overall noise array used in chunk generation */
    private double[] noiseArray;
    private double[] stoneNoise = new double[256];

    /** A double array that hold terrain noise from noiseGen3 */
    double[] noise3;

    /** A double array that hold terrain noise */
    double[] noise1;

    /** A double array that hold terrain noise from noiseGen2 */
    double[] noise2;

    /** A double array that hold terrain noise from noiseGen5 */
    double[] noise5;

    /** A double array that holds terrain noise from noiseGen6 */
    double[] noise6;

    /**
     * Used to store the 5x5 parabolic field that is used during terrain generation.
     */
    float[] parabolicField;
    int[][] field_73219_j = new int[32][32];

    public ChunkProviderGenerateBOP(World par1World, long par2, boolean par4)
    {
    	super(par1World, par2, par4);
        this.worldObj = par1World;
        this.rand = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)par2 * var7 + (long)par3 * var9 ^ this.worldObj.getSeed());
        boolean var11 = false;
        boolean mapFeaturesEnabled;
        MapGenMineshaft mineshaftGenerator;
        MapGenVillage villageGenerator;
        MapGenStronghold strongholdGenerator;
        MapGenScatteredFeature scatteredFeatureGenerator;
        
        mapFeaturesEnabled = (Boolean) getPrivateField("mapFeaturesEnabled", "q", boolean.class);
        mineshaftGenerator = (MapGenMineshaft) getPrivateField("mineshaftGenerator", "w", MapGenMineshaft.class);
        villageGenerator = (MapGenVillage) getPrivateField("villageGenerator", "v", MapGenVillage.class);
        strongholdGenerator = (MapGenStronghold) getPrivateField("strongholdGenerator", "u", MapGenStronghold.class);
        scatteredFeatureGenerator = (MapGenScatteredFeature) getPrivateField("scatteredFeatureGenerator", "x", MapGenScatteredFeature.class);

        if (mapFeaturesEnabled)
        {
        	mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        	var11 = villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        	strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        	scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        }

        int var12;
        int var13;
        int var14;

        if (!var11 && this.rand.nextInt(4) == 0)
        {
            var12 = var4 + this.rand.nextInt(16) + 8;
            var13 = this.rand.nextInt(128);
            var14 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(Block.waterStill.blockID)).generate(this.worldObj, this.rand, var12, var13, var14);
        }

        if (!var11 && this.rand.nextInt(8) == 0)
        {
            var12 = var4 + this.rand.nextInt(16) + 8;
            var13 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            var14 = var5 + this.rand.nextInt(16) + 8;

            if (var13 < 63 || this.rand.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.lavaStill.blockID)).generate(this.worldObj, this.rand, var12, var13, var14);
            }
        }

        for (var12 = 0; var12 < 8; ++var12)
        {
            var13 = var4 + this.rand.nextInt(16) + 8;
            var14 = this.rand.nextInt(128);
            int var15 = var5 + this.rand.nextInt(16) + 8;

            if ((new WorldGenDungeons()).generate(this.worldObj, this.rand, var13, var14, var15))
            {
                ;
            }
        }

        var6.decorate(this.worldObj, this.rand, var4, var5);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, var6, var4 + 8, var5 + 8, 16, 16, this.rand);
        var4 += 8;
        var5 += 8;

        for (var12 = 0; var12 < 16; ++var12)
        {
            for (var13 = 0; var13 < 16; ++var13)
            {
                var14 = this.worldObj.getPrecipitationHeight(var4 + var12, var5 + var13);

                if (this.worldObj.isBlockFreezable(var12 + var4, var14 - 1, var13 + var5))
                {
                    this.worldObj.setBlock(var12 + var4, var14 - 1, var13 + var5, Block.ice.blockID, 0, 2);
                }

                if (this.worldObj.canSnowAt(var12 + var4, var14, var13 + var5))
                {
                    this.worldObj.setBlock(var12 + var4, var14, var13 + var5, Block.snow.blockID, 0, 2);
                }
            }
        }
        
        Method method;
        
        try 
        {
        	method = this.getClass().getSuperclass().getDeclaredMethod("BTWPostProcessChunk", World.class, int.class, int.class);
        	
        	method.setAccessible(true);

        	method.invoke(this, this.worldObj, var4, var5);
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }

        BlockSand.fallInstantly = false;
        this.fixVolcanoBlock(this.worldObj, Block.stone.blockID, var4, var5);
    }
    
    private Object getPrivateField(String fieldName, String obfFieldName, Class cast)
    {
        Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
        
        try
        {
        	if (fields != null) 
        	{
        		for (Field field: fields) 
        		{
        			Class<?> fieldType = field.getType();

        			if (fieldType == cast) 
        			{
        				if (field.getName() == fieldName || field.getName() == obfFieldName)
        				{
        					//System.out.println("Field: " + field.getName() + " is of type " + cast.getName());
        					field.setAccessible(true);
        					return field.get(this);
        				}
        			}
        		}
        	}
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        return null;
    }
    
    private void fixVolcanoBlock(World world, int blockID, int x, int z)
    {
    	Chunk var4 = world.getChunkFromChunkCoords(x >> 4, z >> 4);

    	if (world.provider.dimensionId == 0)
    	{
    		if (world.getBiomeGenForCoords(x, z) == BOPBiomes.volcano || world.getBiomeGenForCoords(x - 32, z) == BOPBiomes.volcano || world.getBiomeGenForCoords(x + 32, z) == BOPBiomes.volcano || world.getBiomeGenForCoords(x, z - 32) == BOPBiomes.volcano || world.getBiomeGenForCoords(x, z + 32) == BOPBiomes.volcano)
    		{
    			for (int var5 = 0; var5 < 32; ++var5)
    			{
    				for (int var6 = 0; var6 < 32; ++var6)
    				{
    					int var7 = 128;
    					int var8;
    					int var9;

    					for (var8 = 55 + world.rand.nextInt(2); var7 >= var8; --var7)
    					{
    						var9 = var4.getBlockID(var5, var7, var6);

    						if (var9 == blockID)
    						{
    							var4.setBlockMetadata(var5, var7, var6, 1);
    						}
    					}
    				}
    			}
    		}
    	}
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    public void func_104112_b() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "RandomLevelSource";
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }
}
