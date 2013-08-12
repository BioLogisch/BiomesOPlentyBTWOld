package net.minecraft.src.biomesoplenty.utils;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.itemblock.ItemMultiBlock;

public class GameRegistry 
{
	public static void registerBlock(Block block) 
	{
		Item.itemsList[block.blockID] = new ItemBlock(block.blockID - 256);
	}
	
	public static void registerCustomBlock(Block block, ItemBlock type)
	{	
		Item.itemsList[block.blockID] = type;
	}
	
	public static void registerMultiBlock(Block block, String[] types)
	{
		Item.itemsList[block.blockID] = new ItemMultiBlock(block, types);
	}
	
    public static void addRecipe(ItemStack output, Object... params)
    {
        addShapedRecipe(output, params);
    }

    public static IRecipe addShapedRecipe(ItemStack output, Object... params)
    {
        return CraftingManager.getInstance().addRecipe(output, params);
    }

    public static void addShapelessRecipe(ItemStack output, Object... params)
    {
        CraftingManager.getInstance().addShapelessRecipe(output, params);
    }

    public static void addRecipe(IRecipe recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(recipe);
    }
}
