package net.minecraft.src.biomesoplenty.utils;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;

public class GameRegistry 
{
	public static void registerBlock(Block block) 
	{
		Item.itemsList[block.blockID] = new ItemBlock(block.blockID - 256);
	}
}
