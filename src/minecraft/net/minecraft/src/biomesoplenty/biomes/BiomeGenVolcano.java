package net.minecraft.src.biomesoplenty.biomes;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenVolcano;

public class BiomeGenVolcano extends BiomeGenBase
{
	private BiomeDecoratorBOP customBiomeDecorator;

	public BiomeGenVolcano(int par1)
	{
		super(par1);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.stone.blockID;
		fillerBlock = (byte)Block.stone.blockID;
		theBiomeDecorator = new BiomeDecoratorBOP(this);
		customBiomeDecorator = (BiomeDecoratorBOP)theBiomeDecorator;
		customBiomeDecorator.treesPerChunk = 1;
		customBiomeDecorator.flowersPerChunk = -999;
		customBiomeDecorator.grassPerChunk = -999;
		customBiomeDecorator.volcanoLavaPerChunk = 50;
		//customBiomeDecorator.generateAsh = true;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	 @Override
	 public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	 {
		 return new WorldGenVolcano();
	 }

	 /**
	  * takes temperature, returns color
	  */
	 @Override
	 public int getSkyColorByTemp(float par1)
	 {
		 //if (BOPConfiguration.skyColors)
			 return 8026746;
		 /*else
		 {
			 par1 /= 3.0F;

			 if (par1 < -1.0F)
			 {
				 par1 = -1.0F;
			 }

			 if (par1 > 1.0F)
			 {
				 par1 = 1.0F;
			 }

			 return Color.getHSBColor(0.62222224F - par1 * 0.05F, 0.5F + par1 * 0.1F, 1.0F).getRGB();
		 }*/
	 }
}
