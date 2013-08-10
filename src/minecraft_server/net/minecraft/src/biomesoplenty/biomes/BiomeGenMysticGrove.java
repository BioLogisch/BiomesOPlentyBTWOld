package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.WorldGenTallGrass;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenMystic1;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenMystic2;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenSwampTall;

public class BiomeGenMysticGrove extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	@SuppressWarnings("unchecked")
	public BiomeGenMysticGrove(int par1)
	{
		super(par1);
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 8;

		customBiomeDecorator.grassPerChunk = 7;
		customBiomeDecorator.flowersPerChunk = 8;
		//customBiomeDecorator.pinkFlowersPerChunk = 6;
		//customBiomeDecorator.glowFlowersPerChunk = 15;
		customBiomeDecorator.rosesPerChunk = 8;
		customBiomeDecorator.sandPerChunk = -999;
		customBiomeDecorator.sandPerChunk2 = -999;
		//customBiomeDecorator.sproutsPerChunk = 3;
		//customBiomeDecorator.hydrangeasPerChunk = 3;
		//customBiomeDecorator.blueMilksPerChunk = 4;
		//customBiomeDecorator.glowshroomsPerChunk = 2;
		//customBiomeDecorator.lilyflowersPerChunk = 3;
		//customBiomeDecorator.hotSpringsPerChunk = 2;
		//customBiomeDecorator.poisonWaterPerChunk = 1;
		waterColorMultiplier = 15349914;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		//spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 10, 4, 4));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(5) == 0 ? new WorldGenMystic2(false) : (par1Random.nextInt(7) == 0 ? new WorldGenSwampTall() : new WorldGenMystic1(false));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	{
		return par1Random.nextInt(2) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	}
}
