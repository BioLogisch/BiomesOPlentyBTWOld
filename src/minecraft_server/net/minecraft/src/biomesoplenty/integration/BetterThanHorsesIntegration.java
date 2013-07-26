package net.minecraft.src.biomesoplenty.integration;

import java.util.ArrayList;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.FCAddOnHandler;
import net.minecraft.src.biomesoplenty.configuration.BOPBiomes;

public class BetterThanHorsesIntegration 
{
	public static BiomeGenBase[] horseBiomeList = new BiomeGenBase[] { BOPBiomes.field, BOPBiomes.grassland, BOPBiomes.heathland, BOPBiomes.prairie, BOPBiomes.shrubland, BOPBiomes.steppe };
	
	public static void init()
	{
		addHorsesToBiomes();
	}
	
	private static void addHorsesToBiomes()
	{
		try 
		{
			Class BTH = Class.forName("net.minecraft.src.BTHBetterThanHorses");
			ArrayList biomeSpawnList = (ArrayList)BTH.getField("biomeSpawnList").get(null);
			biomeSpawnList.add(new Object[] {horseBiomeList, 5, 2, 6});   
		} 
		catch (ClassNotFoundException e) 
		{
			//shouldn't happen
		} 
		catch (Exception e) 
		{
			FCAddOnHandler.LogMessage("[BiomesOPlenty] There was an error while integrating Better Than Horses with Biomes O' Plenty!");
			e.printStackTrace();
		}
	}
}
