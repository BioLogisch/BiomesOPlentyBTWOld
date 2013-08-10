package net.minecraft.src.biomesoplenty;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.FCAddOn;
import net.minecraft.src.FCAddOnHandler;
import net.minecraft.src.WorldType;
import net.minecraft.src.biomesoplenty.configuration.BOPBiomes;
import net.minecraft.src.biomesoplenty.configuration.BOPBlocks;
import net.minecraft.src.biomesoplenty.configuration.BOPConfiguration;
import net.minecraft.src.biomesoplenty.configuration.CreativeTabsBOP;
import net.minecraft.src.biomesoplenty.integration.BetterThanHorsesIntegration;
import net.minecraft.src.biomesoplenty.world.WorldTypeBOP;

public class BiomesOPlenty extends FCAddOn
{
    public static String bopVersionString = "0.1.2";
    public static BiomesOPlenty m_instance = new BiomesOPlenty();
    
    public static final WorldType BIOMEOP = (new WorldTypeBOP());  
    
	public static CreativeTabs tabBiomesOPlenty;
    
    public static void vanillaConstruct()
    {
    	//Called by Block Dirt to kick things off
    }
    
    @Override
    public void InitializeConfigs()
    {
    	BOPConfiguration.init();
    }
    
    @Override
    public void PreInitialize() 
    {
		tabBiomesOPlenty = new CreativeTabsBOP(CreativeTabs.getNextID(), "tabBiomesOPlenty");
    	
    	BOPBlocks.init();
    	BOPBiomes.init();
    	BetterThanHorsesIntegration.init();
    }
	
	@Override
	public void Initialize() 
	{
		FCAddOnHandler.LogMessage("[BiomesOPlenty] Biomes O Plenty Version " + bopVersionString + " Initializing...");
        FCAddOnHandler.LogMessage("[BiomesOPlenty] Biomes O Plenty Initialization Complete.");
	}
	
	@Override
	public void PostInitialize()
	{
	}
}
