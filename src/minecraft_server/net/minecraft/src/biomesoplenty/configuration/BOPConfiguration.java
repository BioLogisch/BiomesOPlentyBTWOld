package net.minecraft.src.biomesoplenty.configuration;

import java.io.File;

import net.minecraft.src.biomesoplenty.utils.ConfigFile;
import net.minecraft.src.biomesoplenty.utils.ConfigUtils;

public class BOPConfiguration
{
	public static ConfigFile mainConfigFile;
	public static ConfigFile biomeGenConfigFile;
	public static ConfigFile terrainGenConfigFile;
	public static ConfigFile idConfigFile;
	public static ConfigFile miscConfigFile;
	
	public static void init()
	{
	    mainConfigFile = new BOPConfiguration.Main();
	    biomeGenConfigFile = new BOPConfiguration.BiomeGen();
	    idConfigFile = new BOPConfiguration.IDs();
	}

	public static class Main extends ConfigFile
	{
		@Override
		public void setDefaults() 
		{
			properties.setProperty("enableCustomContent", "false");
		}

		@Override
		public File getConfigFile() 
		{
			return new File(this.baseConfigDir + File.separator + "biomesoplenty" + File.separator + "main.cfg");
		}

		@Override
		public String getHeader()
		{
			return "Biomes O Plenty Main Config";
		}
	}

	public static class BiomeGen extends ConfigFile
	{
		@Override
		public void setDefaults() 
		{
			properties.setProperty("alpsGeneration", "true");
			properties.setProperty("arcticGeneration", "true");
			properties.setProperty("birchForestGeneration", "true");
			properties.setProperty("bogGeneration", "true");
			properties.setProperty("borealForestGeneration", "true");
			properties.setProperty("brushlandGeneration", "true");
			properties.setProperty("chaparralGeneration", "true");
			properties.setProperty("coniferousForestGeneration", "true");
			properties.setProperty("coniferousForestSnowGeneration", "true");
			properties.setProperty("deciduousForestGeneration", "true");
			properties.setProperty("dunesGeneration", "true");
			properties.setProperty("fenGeneration", "true");
			properties.setProperty("fieldGeneration", "true");
			properties.setProperty("frostForestGeneration", "true");
			properties.setProperty("glacierGeneration", "true");
			properties.setProperty("grasslandGeneration", "true");
			properties.setProperty("groveGeneration", "true");
			properties.setProperty("heathlandGeneration", "true");
			properties.setProperty("highlandGeneration", "true");
			//properties.setProperty("icyHillsGeneration", "true");
			properties.setProperty("jadeCliffsGeneration", "true");
			properties.setProperty("lushSwampGeneration", "true");
			properties.setProperty("marshGeneration", "true");
			properties.setProperty("meadowGeneration", "true");
			properties.setProperty("moorGeneration", "true");
			properties.setProperty("mountainGeneration", "true");
			properties.setProperty("mysticGroveGeneration", "true");
			//properties.setProperty("oasisGeneration", "true");
			properties.setProperty("ominousWoodsGeneration", "true");
			properties.setProperty("originValleyGeneration", "true");
			properties.setProperty("polarGeneration", "true");
			properties.setProperty("prairieGeneration", "true");
			properties.setProperty("rainforestGeneration", "true");
			properties.setProperty("redwoodForestGeneration", "true");
			properties.setProperty("sacredSpringsGeneration", "true");
			properties.setProperty("savannaGeneration", "true");
			properties.setProperty("scrublandGeneration", "true");
			properties.setProperty("shieldGeneration", "true");
			properties.setProperty("shrublandGeneration", "true");
			properties.setProperty("sludgepitGeneration", "true");
			properties.setProperty("spruceWoodsGeneration", "true");
			properties.setProperty("steppeGeneration", "true");
			properties.setProperty("temperateRainforestGeneration", "true");
			properties.setProperty("thicketGeneration", "true");
			properties.setProperty("timberGeneration", "true");
			properties.setProperty("tropicalRainforestGeneration", "true");
			properties.setProperty("tropicsGeneration", "true");
			properties.setProperty("tundraGeneration", "true");
			properties.setProperty("volcanoGeneration", "true");
			properties.setProperty("wetlandGeneration", "true");
			properties.setProperty("woodlandGeneration", "true");
		}

		@Override
		public File getConfigFile() 
		{
			return new File(this.baseConfigDir + File.separator + "biomesoplenty" + File.separator + "biomegen.cfg");
		}

		@Override
		public String getHeader()
		{
			return "Biomes O Plenty Biome Gen Config";
		}
	}

	public static class IDs extends ConfigFile
	{
		@Override
		public void setDefaults() 
		{
			ConfigUtils.setItemID(properties, "barkID", 25000);
			
			ConfigUtils.setBlockID(properties, "leaves1ID", 1923);
			ConfigUtils.setBlockID(properties, "leaves2ID", 1924);
			
			ConfigUtils.setBlockID(properties, "planksID", 1947);
			ConfigUtils.setBlockID(properties, "logs1ID", 1933);
			ConfigUtils.setBlockID(properties, "logs2ID", 1934);
			ConfigUtils.setBlockID(properties, "logs3ID", 1935);
			ConfigUtils.setBlockID(properties, "logs4ID", 1936);
			
			ConfigUtils.setBlockID(properties, "leavesColourizedID", 1962);
		}

		@Override
		public File getConfigFile() 
		{
			return new File(this.baseConfigDir + File.separator + "biomesoplenty" + File.separator + "ids.cfg");
		}

		@Override
		public String getHeader()
		{
			return "Biomes O Plenty ID Config";
		}
	}
}
