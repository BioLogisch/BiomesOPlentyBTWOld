package net.minecraft.src.biomesoplenty.utils;

import java.util.Properties;

import net.minecraft.src.biomesoplenty.configuration.BOPConfiguration;

public class ConfigUtils 
{
	public static void setBlockID(Properties properties, String name, int id)
	{
		properties.setProperty("Block." + name, String.valueOf(id));
	}
	
	public static int getBlockID(String name)
	{
		return BOPConfiguration.idConfigFile.getInt("Block." + name);
	}
	
	public static void setItemID(Properties properties, String name, int id)
	{
		properties.setProperty("Item." + name, String.valueOf(id));
	}
	
	public static int getItemID(String name)
	{
		return BOPConfiguration.idConfigFile.getInt("Item." + name);
	}
}
