package net.minecraft.src.biomesoplenty.utils;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ConfigFileManager 
{
    public static List configFileList = new LinkedList();
    
    public static void addConfigFile(ConfigFile cfg)
    {
    	configFileList.add(cfg);
    }
    
    public static void setDefaultValues()
    {
    	FileWriter writer = null;
        Iterator iterator = configFileList.iterator();
    	
        while (iterator.hasNext())
        {
            ConfigFile configFile = (ConfigFile)iterator.next();
            
            try
            {
            	if (!configFile.getConfigFile().getParentFile().exists()) configFile.getConfigFile().getParentFile().mkdirs();

            	if (!configFile.getConfigFile().exists())
            	{
            		writer = new FileWriter(configFile.getConfigFile());
            		configFile.setDefaults();
            		configFile.properties.store(writer, configFile.getHeader());
            	}
            }
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    		finally
    		{
    			if (writer != null)
    			{
    				try
    				{
    					writer.close();
    				}
    				catch (Exception e)
    				{
    					e.printStackTrace();
    				}
    			}
    		}
        }
    }
}
