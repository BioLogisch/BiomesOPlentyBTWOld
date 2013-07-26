package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenOriginTree;

public class BiomeGenOriginValley extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenOriginValley(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		topBlock = (byte)Block.grass.blockID;
		customBiomeDecorator.treesPerChunk = 4;
		customBiomeDecorator.grassPerChunk = -999;
		customBiomeDecorator.generatePumpkins = false;
		customBiomeDecorator.sandPerChunk = 0;
		customBiomeDecorator.sandPerChunk2 = 0;
		customBiomeDecorator.clayPerChunk = 0;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return new WorldGenOriginTree(false);
	}
}
