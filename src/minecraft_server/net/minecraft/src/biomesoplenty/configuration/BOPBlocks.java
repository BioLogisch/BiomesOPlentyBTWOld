package net.minecraft.src.biomesoplenty.configuration;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.blocks.BOPBlockPlank;
import net.minecraft.src.biomesoplenty.utils.ConfigUtils;
import net.minecraft.src.biomesoplenty.utils.GameRegistry;
import net.minecraft.src.biomesoplenty.utils.LanguageRegistry;

public class BOPBlocks 
{
	public static void init()
	{
		initializeBlocks();
		registerBlocks();
		localizeNames();
	}
	
	private static void initializeBlocks()
	{
	    BOPAPIBlocks.planks = new BOPBlockPlank(ConfigUtils.getBlockID("planksID")).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("bop.planks").setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
	}
	
	private static void registerBlocks()
	{
		GameRegistry.registerMultiBlock(BOPAPIBlocks.planks, new String[] {"acaciaPlank", "cherryPlank", "darkPlank", "firPlank", "holyPlank", "magicPlank", "mangrovePlank", "palmPlank", "redwoodPlank", "willowPlank", "bambooThatching", "pinePlank", "hellBarkPlank", "jacarandaPlank"});
	}
	
	private static void localizeNames()
	{
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 0), "Acacia Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 1), "Cherry Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 2), "Dark Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 3), "Fir Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 4), "Loftwood Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 5), "Magic Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 6), "Mangrove Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 7), "Palm Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 8), "Redwood Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 9), "Willow Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 10), "Bamboo Thatching");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 11), "Pine Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 12), "Hellbark Wood Planks");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.planks, 1, 13), "Jacaranda Wood Planks");
	}
}
