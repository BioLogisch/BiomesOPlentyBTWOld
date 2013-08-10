package net.minecraft.src.biomesoplenty.configuration;

import java.io.File;

import net.minecraft.src.biomesoplenty.utils.ConfigFile;

public class BOPConfiguration extends ConfigFile
{
	@Override
	public void setDefaults() 
	{
	}

	@Override
	public File getConfigFile() 
	{
		return new File(this.baseConfigDir + File.separator + "biomesoplenty" + File.separator + "base.cfg");
	}
	
	@Override
	public String getHeader()
	{
		return "Biomes O Plenty Base Config";
	}
}
