package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.WorldGenShrub;
import net.minecraft.src.WorldGenerator;

public class BiomeGenThicket extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenThicket(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 17;
		customBiomeDecorator.grassPerChunk = 1;
		//customBiomeDecorator.thornsPerChunk = 25;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	 @Override
	 public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		 return par1Random.nextInt(5) == 0 ? worldGeneratorTrees : new WorldGenShrub(0, 0);
	}
}
