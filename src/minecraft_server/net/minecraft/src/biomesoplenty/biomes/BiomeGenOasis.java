package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenPalmTree3;

public class BiomeGenOasis extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenOasis(int par1)
	{
		super(par1);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.sand.blockID;
		fillerBlock = (byte)Block.sand.blockID;
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 3;
		customBiomeDecorator.grassPerChunk = 15;
		customBiomeDecorator.reedsPerChunk = 100;
		customBiomeDecorator.oasesPerChunk = 999;
		customBiomeDecorator.oasesPerChunk2 = 999;
		customBiomeDecorator.cactiPerChunk = 7;
		//customBiomeDecorator.desertSproutsPerChunk = 3;
		//customBiomeDecorator.tinyCactiPerChunk = 2;
		customBiomeDecorator.generatePumpkins = false;
		customBiomeDecorator.generateMelons = true;
		//customBiomeDecorator.generateQuicksand = true;
		customBiomeDecorator.waterLakesPerChunk = 10;
		//customBiomeDecorator.aloePerChunk = 4;
		//customBiomeDecorator.hotSpringsPerChunk = 1;
	}
	
	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 12 + par2Random.nextInt(6);

		for (int var6 = 0; var6 < var5; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(16);
			int var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			int var10 = par1World.getBlockId(var7, var8, var9);
		}
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	 @Override
	 public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	 {
		 return new WorldGenPalmTree3();
	 }
}
