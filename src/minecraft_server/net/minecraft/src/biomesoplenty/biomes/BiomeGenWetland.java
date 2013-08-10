package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenMoss;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenTaiga5;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenWillow;

public class BiomeGenWetland extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenWetland(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 10;
		customBiomeDecorator.grassPerChunk = 10;
		customBiomeDecorator.flowersPerChunk = -999;
		customBiomeDecorator.mushroomsPerChunk = 5;
		//customBiomeDecorator.toadstoolsPerChunk = 1;
		customBiomeDecorator.reedsPerChunk = 15;
		//customBiomeDecorator.reedsBOPPerChunk = 15;
		customBiomeDecorator.clayPerChunk = 2;
		customBiomeDecorator.sandPerChunk = -999;
		customBiomeDecorator.sandPerChunk2 = -999;
		//customBiomeDecorator.mudPerChunk = 5;
		//customBiomeDecorator.mudPerChunk2 = 5;
		//customBiomeDecorator.waterlilyPerChunk = 4;
		//customBiomeDecorator.lilyflowersPerChunk = 4;
		//customBiomeDecorator.cattailsPerChunk = 20;
		//customBiomeDecorator.highCattailsPerChunk = 10;
		//customBiomeDecorator.blueFlowersPerChunk = 6;
		//customBiomeDecorator.blueMilksPerChunk = 1;
		//customBiomeDecorator.portobellosPerChunk = 1;
		//customBiomeDecorator.berryBushesPerChunk = 1;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10, 1, 3));
		waterColorMultiplier = 6512772;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	 @Override
	 public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	 {
		 //return (WorldGenerator)(par1Random.nextInt(2) == 0 ? new WorldGenWillow2() : (par1Random.nextInt(4) == 0 ? new WorldGenLarch1() : (par1Random.nextInt(2) == 0 ? new WorldGenLarch2() : new WorldGenWillow1())));
		 return par1Random.nextInt(2) == 0 ? new WorldGenTaiga5(false) : new WorldGenWillow();
	 }

	 @Override
	 public void decorate(World par1World, Random par2Random, int par3, int par4)
	 {
		 super.decorate(par1World, par2Random, par3, par4);
		 WorldGenMoss var5 = new WorldGenMoss();
		 
		 int var55 = 12 + par2Random.nextInt(6);

		for (int var66 = 0; var66 < var55; ++var66)
		{
			int var77 = par3 + par2Random.nextInt(16);
			int var88 = par2Random.nextInt(28) + 4;
			int var99 = par4 + par2Random.nextInt(16);
			int var100 = par1World.getBlockId(var77, var88, var99);
		}

		 for (int var6 = 0; var6 < 20; ++var6)
		 {
			 int var7 = par3 + par2Random.nextInt(16) + 8;
			 byte var8 = 58;
			 int var9 = par4 + par2Random.nextInt(16) + 8;
			 var5.generate(par1World, par2Random, var7, var8, var9);
		 }
	 }
}
