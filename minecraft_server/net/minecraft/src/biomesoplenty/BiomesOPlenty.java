package net.minecraft.src.biomesoplenty;

import net.minecraft.src.FCAddOn;
import net.minecraft.src.FCAddOnHandler;
import net.minecraft.src.biomesoplenty.configuration.BOPBiomes;

public class BiomesOPlenty extends FCAddOn
{
    public static String bopVersionString = "0.0.7";
    public static BiomesOPlenty m_instance = new BiomesOPlenty();
    
    public static void vanillaConstruct()
    {
    	//Called by Block Dirt to kick things off
    }
    
    @Override
    public void PreInitialize() 
    {
    	BOPBiomes.init();
    }
	
	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage("Biomes O Plenty Version " + bopVersionString + " Initializing...");
        FCAddOnHandler.LogMessage("Biomes O Plenty Complete.");
	}
	
	@Override
	public void PostInitialize()
	{
	}
}
