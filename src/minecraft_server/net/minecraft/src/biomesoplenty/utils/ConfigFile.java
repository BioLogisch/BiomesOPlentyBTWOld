package net.minecraft.src.biomesoplenty.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public abstract class ConfigFile 
{
	public static String baseConfigDir = new File(".").getAbsolutePath() + File.separator + "config";
	public Properties properties = new Properties();
	
	protected ConfigFile()
	{
		ConfigFileManager.addConfigFile(this);
	}
	
	/**
	 * @param  properties  properties used for setting defaults
	 */
	public abstract void setDefaults();
	
	/**
	 * @return config file
	 */
	public abstract File getConfigFile();
	
	/**
	 * @return  header header of the file (e.g. "Mod Configuration")
	 */
	public String getHeader() { return "Mod Configuration"; }
	
	/**
	 * @param  property to retrieve the value of
	 * @return  value of the property specified
	 */
	public final String getString(String property)
	{
		FileReader reader = null;
		
		try
		{
			reader = new FileReader(getConfigFile());
			properties.load(reader);
			return properties.getProperty(property);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * @param  property to retrieve the value of
	 * @return  value of the property specified
	 */
	public final boolean getBoolean(String property)
	{
		return Boolean.parseBoolean(getString(property));
	}
	
	/**
	 * @param  property to retrieve the value of
	 * @return  value of the property specified
	 */
	public final int getInt(String property)
	{
		return Integer.parseInt(getString(property));
	}
}
