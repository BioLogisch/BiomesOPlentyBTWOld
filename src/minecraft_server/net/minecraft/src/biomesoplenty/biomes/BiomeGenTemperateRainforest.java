package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenShrub;
import net.minecraft.src.WorldGenTallGrass;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenMoss;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenTemperate;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenThickTree;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenWillow;

public class BiomeGenTemperateRainforest extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenTemperateRainforest(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 22;
		customBiomeDecorator.grassPerChunk = 25;
		customBiomeDecorator.generatePumpkins = false;
		//customBiomeDecorator.blueMilksPerChunk = 3;
		//customBiomeDecorator.poisonIvyPerChunk = 1;
		//customBiomeDecorator.carrotsPerChunk = 1;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		//return (WorldGenerator)(par1Random.nextInt(3) == 0 ? new WorldGenGrandFir1() : (par1Random.nextInt(4) == 0 ? new WorldGenAlaskanCedar2() : (par1Random.nextInt(8) == 0 ? new WorldGenAlaskanCedar1() : (par1Random.nextInt(2) == 0 ? new WorldGenShrub(0,0) : new WorldGenGrandFir2()))));
		return par1Random.nextInt(10) == 0 ? new WorldGenWillow() : (par1Random.nextInt(6) == 0 ? new WorldGenThickTree(false) : (par1Random.nextInt(2) == 0 ? new WorldGenTemperate(false) : new WorldGenShrub(0, 0)));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	{
		return (par1Random.nextInt(6) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 1) : (par1Random.nextInt(2) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : (par1Random.nextInt(4) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 1) : new WorldGenTallGrass(Block.tallGrass.blockID, 2))));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);
		WorldGenMoss var999 = new WorldGenMoss();

		for (int var66 = 0; var66 < 20; ++var66)
		{
			int var77 = par3 + par2Random.nextInt(16) + 8;
			byte var88 = 58;
			int var99 = par4 + par2Random.nextInt(16) + 8;
			var999.generate(par1World, par2Random, var77, var88, var99);
		}

		for (int var6 = 0; var6 < var5; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(16);
			int var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			int var10 = par1World.getBlockId(var7, var8, var9);

            if (var10 == Block.stone.blockID)
            {
                int var11 = 0;

                if (var8 <= 48 + par1World.rand.nextInt(2))
                {
                    byte var12 = 1;

                    if (var8 <= 24 + par1World.rand.nextInt(2))
                    {
                        var12 = 2;
                    }

                    var11 = Block.oreEmerald.GetMetadataConversionForStrataLevel(var12, 0);
                }

                par1World.setBlock(var7, var8, var9, Block.oreEmerald.blockID, var11, 2);
            }
		}
	}
}
