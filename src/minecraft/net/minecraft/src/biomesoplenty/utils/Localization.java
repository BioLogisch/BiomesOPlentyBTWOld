package net.minecraft.src.biomesoplenty.utils;

public class Localization 
{
	private String target;
	private String lang;
	private String name;
	
	public Localization(String target, String lang, String name) 
	{
		this.target = target;
		this.lang = lang;
		this.name = name;
	}
	
	public Localization(String target, String name) 
	{
		this.target = target;
		this.lang = "en_US";
		this.name = name;
	}
	
	public String getTarget()
	{
		return this.target;
	}
	
	public String getLang()
	{
		return this.name;
	}
	
	public String getName()
	{
		return this.name;
	}
}
