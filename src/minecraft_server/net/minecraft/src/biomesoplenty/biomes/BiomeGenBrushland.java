package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenBrush1;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenBrush2;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenChaparral2;

public class BiomeGenBrushland extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenBrushland(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 10;
		customBiomeDecorator.grassPerChunk = 6;
		//customBiomeDecorator.thornsPerChunk = 4;
		customBiomeDecorator.flowersPerChunk = -999;
		//customBiomeDecorator.generateQuicksand = true;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(2) == 0 ? new WorldGenBrush2() : (par1Random.nextInt(5) == 0 ?  new WorldGenBrush1() : new WorldGenChaparral2());
	}
}
