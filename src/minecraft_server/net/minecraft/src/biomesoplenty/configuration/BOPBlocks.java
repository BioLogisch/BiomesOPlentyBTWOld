package net.minecraft.src.biomesoplenty.configuration;

import net.minecraft.src.Block;
import net.minecraft.src.BlockDirt;
import net.minecraft.src.BlockStone;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.FCBlockPane;
import net.minecraft.src.Material;
import net.minecraft.src.biomesoplenty.utils.GameRegistry;

public class BOPBlocks 
{
	public static Block test;
	
	public static void init()
	{
		initializeBlocks();
		registerBlocks();
	}
	
	private static void initializeBlocks()
	{
	    test = new BlockStone(3000).setUnlocalizedName("test").setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	private static void registerBlocks()
	{
		GameRegistry.registerBlock(test);
	}
}
