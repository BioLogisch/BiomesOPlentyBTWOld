package net.minecraft.src.biomesoplenty.utils;

import java.lang.reflect.Field;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFire;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FCIBlockFluidSource;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.StatBase;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatCrafting;
import net.minecraft.src.StatList;

public class GameRegistry 
{
	public static void registerBlock(Block block) 
	{
		Item.itemsList[block.blockID] = new ItemBlock(block.blockID - 256);
	}
}
