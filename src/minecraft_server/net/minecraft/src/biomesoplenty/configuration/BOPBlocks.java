package net.minecraft.src.biomesoplenty.configuration;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPColorizedLeaves;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLeaves;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLog;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLeaves.LeafCategory;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLog.LogCategory;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPPlank;
import net.minecraft.src.biomesoplenty.itemblock.ItemBlockColorizedLeaves;
import net.minecraft.src.biomesoplenty.itemblock.ItemBlockLeaves;
import net.minecraft.src.biomesoplenty.itemblock.ItemBlockLog;
import net.minecraft.src.biomesoplenty.itemblock.ItemBlockPlank;
import net.minecraft.src.biomesoplenty.utils.ConfigUtils;
import net.minecraft.src.biomesoplenty.utils.GameRegistry;
import net.minecraft.src.biomesoplenty.utils.LanguageRegistry;

public class BOPBlocks 
{
	public static void init()
	{
		initializeBlocks();
		registerBlocks();
		setToolEffectiveness();
		setBuoyancy();
		localizeNames();
	}

	private static void initializeBlocks()
	{
		BOPAPIBlocks.leaves1 = new BlockBOPLeaves(ConfigUtils.getBlockID("leaves1ID"), LeafCategory.CAT1).setUnlocalizedName("bop.leaves1");
		BOPAPIBlocks.leaves2 = new BlockBOPLeaves(ConfigUtils.getBlockID("leaves2ID"), LeafCategory.CAT2).setUnlocalizedName("bop.leaves2");
		BOPAPIBlocks.leavesColourized = new BlockBOPColorizedLeaves(ConfigUtils.getBlockID("leavesColourizedID")).setUnlocalizedName("bop.leavesColourized");
		
	    BOPAPIBlocks.planks = new BlockBOPPlank(ConfigUtils.getBlockID("planksID")).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("bop.planks").setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
		BOPAPIBlocks.logs1 = new BlockBOPLog(ConfigUtils.getBlockID("logs1ID"), LogCategory.CAT1).setUnlocalizedName("bop.wood1");
		BOPAPIBlocks.logs2 = new BlockBOPLog(ConfigUtils.getBlockID("logs2ID"), LogCategory.CAT2).setUnlocalizedName("bop.wood2");
		BOPAPIBlocks.logs3 = new BlockBOPLog(ConfigUtils.getBlockID("logs3ID"), LogCategory.CAT3).setUnlocalizedName("bop.wood3");
		BOPAPIBlocks.logs4 = new BlockBOPLog(ConfigUtils.getBlockID("logs4ID"), LogCategory.CAT4).setUnlocalizedName("bop.wood4");
	}
	
	private static void registerBlocks()
	{
		GameRegistry.registerCustomBlock(BOPAPIBlocks.leaves1, new ItemBlockLeaves(BOPAPIBlocks.leaves1.blockID));
		GameRegistry.registerCustomBlock(BOPAPIBlocks.leaves2, new ItemBlockLeaves(BOPAPIBlocks.leaves2.blockID));
		GameRegistry.registerCustomBlock(BOPAPIBlocks.leavesColourized, new ItemBlockColorizedLeaves(BOPAPIBlocks.leavesColourized.blockID));
		
		GameRegistry.registerCustomBlock(BOPAPIBlocks.planks, new ItemBlockPlank(BOPAPIBlocks.planks.blockID));
		GameRegistry.registerCustomBlock(BOPAPIBlocks.logs1, new ItemBlockLog(BOPAPIBlocks.logs1.blockID));
		GameRegistry.registerCustomBlock(BOPAPIBlocks.logs2, new ItemBlockLog(BOPAPIBlocks.logs2.blockID));
		GameRegistry.registerCustomBlock(BOPAPIBlocks.logs3, new ItemBlockLog(BOPAPIBlocks.logs3.blockID));
		GameRegistry.registerCustomBlock(BOPAPIBlocks.logs4, new ItemBlockLog(BOPAPIBlocks.logs4.blockID));
	}
	
	private static void setToolEffectiveness()
	{
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.leaves1);
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.leaves2);
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.leavesColourized);
		
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.planks);
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.logs1);
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.logs2);
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.logs3);
        ItemAxe.SetAllAxesToBeEffectiveVsBlock(BOPAPIBlocks.logs4);
	}
	
	private static void setBuoyancy()
	{
        Item.itemsList[BOPAPIBlocks.leaves1.blockID].SetBuoyancy(1.0F);
        Item.itemsList[BOPAPIBlocks.leaves2.blockID].SetBuoyancy(1.0F);
        Item.itemsList[BOPAPIBlocks.leavesColourized.blockID].SetBuoyancy(1.0F);
		
        Item.itemsList[BOPAPIBlocks.planks.blockID].SetBuoyancy(1.0F);
        Item.itemsList[BOPAPIBlocks.logs1.blockID].SetBuoyancy(1.0F);
        Item.itemsList[BOPAPIBlocks.logs2.blockID].SetBuoyancy(1.0F);
        Item.itemsList[BOPAPIBlocks.logs3.blockID].SetBuoyancy(1.0F);
        Item.itemsList[BOPAPIBlocks.logs4.blockID].SetBuoyancy(1.0F);
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
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs1, 1, 0), "Acacia Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs1, 1, 1), "Cherry Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs1, 1, 2), "Dark Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs1, 1, 3), "Fir Wood");
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs2, 1, 0), "Loftwood Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs2, 1, 1), "Magic Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs2, 1, 2), "Mangrove Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs2, 1, 3), "Palm Wood");
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs3, 1, 0), "Redwood Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs3, 1, 1), "Willow Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs3, 1, 2), "Dead Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs3, 1, 3), "Giant Flower Stem");
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs4, 1, 0), "Pine Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs4, 1, 1), "Hellbark Wood");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.logs4, 1, 2), "Jacaranda Wood");
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 0), "Yellow Autumn Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 1), "Bamboo Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 2), "Magic Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 3), "Dark Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 4), "Dying Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 5), "Fir Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 6), "Loftwood Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves1, 1, 7), "Orange Autumn Leaves");
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves2, 1, 0), "Origin Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves2, 1, 1), "Pink Cherry Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves2, 1, 2), "Maple Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves2, 1, 3), "White Cherry Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves2, 1, 4), "Hellbark Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leaves2, 1, 5), "Jacaranda Leaves");
		
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leavesColourized, 1, 0), "Acacia Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leavesColourized, 1, 1), "Mangrove Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leavesColourized, 1, 2), "Palm Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leavesColourized, 1, 3), "Redwood Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leavesColourized, 1, 4), "Willow Leaves");
		LanguageRegistry.addName(new ItemStack(BOPAPIBlocks.leavesColourized, 1, 5), "Pine Leaves");
	}
}
