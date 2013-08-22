package net.minecraft.src.betterore.common;

import net.minecraft.src.FCAddOn;
import net.minecraft.src.FCAddOnHandler;
import net.minecraft.src.betterore.common.util.BOConfig;

public class BetterOre extends FCAddOn
{

    public static String bodVersionString = "0.0.1";
    public static BetterOre m_instance = new BetterOre();
    
    public static void vanillaConstruct()
    {
    	//Called by Block Stone to kick things off
    }

    @Override
    public void InitializeConfigs()
    {
		FCAddOnHandler.LogMessage("[BetterOre] Better Ore Version " + bodVersionString + " Loading configs...");
    	BOConfig.init();
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
		
	}

}
