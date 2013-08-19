package net.minecraft.src.betterore;

import net.minecraft.src.Block;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class BORecipes {

	public static void init() {
		AddSmeltingRecipes();
	}

	private static void AddSmeltingRecipes()
	{
		FurnaceRecipes.smelting().addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1), 0, 7); //Take a little more than one day to cook one coal    
	}

}
