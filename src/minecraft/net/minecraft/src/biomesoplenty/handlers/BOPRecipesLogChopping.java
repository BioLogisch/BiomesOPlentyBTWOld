package net.minecraft.src.biomesoplenty.handlers;

import net.minecraft.src.Block;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLog;

public class BOPRecipesLogChopping implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting var1, World var2)
    {
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < var1.getSizeInventory(); ++var5)
        {
            ItemStack var6 = var1.getStackInSlot(var5);

            if (var6 != null)
            {
                if (this.IsAxe(var6))
                {
                    if (var3 != null)
                    {
                        return false;
                    }

                    var3 = var6;
                }
                else
                {
                    if (!this.IsLog(var6))
                    {
                        return false;
                    }

                    if (var4 != null)
                    {
                        return false;
                    }

                    var4 = var6;
                }
            }
        }

        return var3 != null && var4 != null;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {
        ItemStack var2 = null;
        ItemStack var3 = null;
        ItemStack var4 = null;

        for (int var5 = 0; var5 < var1.getSizeInventory(); ++var5)
        {
            ItemStack var6 = var1.getStackInSlot(var5);

            if (var6 != null)
            {
                if (this.IsAxe(var6))
                {
                    if (var3 != null)
                    {
                        return null;
                    }

                    var3 = var6;
                }
                else
                {
                    if (!this.IsLog(var6))
                    {
                        return null;
                    }

                    if (var4 != null)
                    {
                        return null;
                    }

                    var4 = var6;
                    int var7 = var6.itemID;

                    Block block = Block.blocksList[var6.itemID];
                    if (block != null && block instanceof BlockBOPLog)
                    {
                    	BlockBOPLog logblock = (BlockBOPLog)block;
                    	
                    	int typeIndex = logblock.getPlankMetaFromLogMeta(var6.getItemDamage());
                    	
                    	if (typeIndex != -1 && typeIndex != -2)
                    	{
                    		var2 = new ItemStack(BOPAPIBlocks.planks.blockID, 2, typeIndex);
                    	}
                    	else if (typeIndex == -1)
                    	{
                    		var2 = new ItemStack(FCBetterThanWolves.fcSawDust, 8);
                    	}
                    }
                }
            }
        }

        if (var4 != null && var3 != null)
        {
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 2;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }

    public boolean matches(IRecipe var1)
    {
        return false;
    }

    private boolean IsAxe(ItemStack var1)
    {
        int var2 = var1.itemID;
        return var2 == FCBetterThanWolves.fcBattleAxe.itemID || var1.getItem() instanceof ItemAxe;
    }

    private boolean IsLog(ItemStack var1)
    {
        int var2 = var1.itemID;
        return var2 == BOPAPIBlocks.logs1.blockID || var2 == BOPAPIBlocks.logs2.blockID || var2 == BOPAPIBlocks.logs3.blockID || var2 == BOPAPIBlocks.logs4.blockID;
    }
}

