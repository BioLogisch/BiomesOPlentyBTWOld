package net.minecraft.src.betterore;

import net.minecraft.src.FCAddOn;
import net.minecraft.src.FCAddOnHandler;

public class BetterOre extends FCAddOn
{

    public static String bodVersionString = "0.0.1";
    public static BetterOre m_instance = new BetterOre();
    
    public static void vanillaConstruct()
    {
    	//Called by Block Dirt to kick things off
    }
    
    
    
    @Override
    public void InitializeConfigs()
    {
    	
    }
    
    @Override
    public void PreInitialize() 
    {
		
    }
	
	@Override
	public void Initialize() {
		FCAddOnHandler.LogMessage("[BetterOre] Better Ore Version " + bodVersionString + " Initializing...");
        FCAddOnHandler.LogMessage("[BetterOre] Better Ore Initialization Complete.");
		
	}
	
	@Override
	public void PostInitialize()
	{
		BORecipes.init();
		
		BOOreGen.init();
	}

}
