package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenPalmTree1;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenPalmTree3;

public class BiomeGenTropics extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	@SuppressWarnings("unchecked")
	public BiomeGenTropics(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 8;
		customBiomeDecorator.grassPerChunk = 7;
		customBiomeDecorator.flowersPerChunk = 10;
		customBiomeDecorator.sandPerChunk = 50;
		customBiomeDecorator.sandPerChunk2 = 50;
		//customBiomeDecorator.orangeFlowersPerChunk = 10;
		//customBiomeDecorator.whiteFlowersPerChunk = 4;
		//customBiomeDecorator.sunflowersPerChunk = 2;
		customBiomeDecorator.generatePumpkins = false;
		spawnableCreatureList.clear();
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
		return par1Random.nextInt(3) == 0 ? new WorldGenPalmTree1() : new WorldGenPalmTree3();
	}
}
