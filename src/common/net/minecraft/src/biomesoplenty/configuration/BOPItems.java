package net.minecraft.src.biomesoplenty.configuration;

import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.api.BOPAPIItems;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLog;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLog.LogCategory;
import net.minecraft.src.biomesoplenty.items.ItemBOPBark;
import net.minecraft.src.biomesoplenty.utils.ConfigUtils;
import net.minecraft.src.biomesoplenty.utils.LanguageRegistry;

public class BOPItems 
{
	public static void init()
	{
		initializeItems();
		setBurnTimes();
		setBuoyancy();
		setBellowsBlowDistance();
		localizeNames();
	}
	
	private static void initializeItems()
	{
		BOPAPIItems.bark = new ItemBOPBark(ConfigUtils.getItemID("barkID")).setUnlocalizedName("bark");
	}
	
	private static void setBurnTimes()
	{
		BOPAPIItems.bark.SetDefaultFurnaceBurnTime(25);
	}
	
	private static void setBuoyancy()
	{
        BOPAPIItems.bark.SetBuoyancy(1.0F);
	}
	
	private static void setBellowsBlowDistance()
	{
        BOPAPIItems.bark.SetBellowsBlowDistance(2);
	}
	
	private static void localizeNames()
	{
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 0), "Acacia Bark");
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 1), "Cherry Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 2), "Dark Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 3), "Fir Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 4), "Loftwood Bark");
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 5), "Magic Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 6), "Mangrove Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 7), "Palm Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 8), "Redwood Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 9), "Willow Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 10), "Pine Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 11), "Hellbark Bark"); 
		LanguageRegistry.addName(new ItemStack(BOPAPIItems.bark, 1, 12), "Jacaranda Bark");
	}
}
