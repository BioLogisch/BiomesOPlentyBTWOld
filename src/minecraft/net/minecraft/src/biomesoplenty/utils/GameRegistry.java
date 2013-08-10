package net.minecraft.src.biomesoplenty.utils;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.itemblock.ItemMultiBlock;

public class GameRegistry 
{
	public static void registerBlock(Block block) 
	{
		Item.itemsList[block.blockID] = new ItemBlock(block.blockID - 256);
	}
	
	public static void registerMultiBlock(Block block, String[] types)
	{
		Item.itemsList[block.blockID] = new ItemMultiBlock(block, types);
	}
}
