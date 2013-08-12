package net.minecraft.src.biomesoplenty.oredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.Block;
import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ShapelessRecipes;
import net.minecraft.src.World;

public class ShapelessOreRecipe implements IRecipe
{
    private ItemStack output = null;
    private ArrayList input = new ArrayList();

    public ShapelessOreRecipe(Block result, Object... recipe){ this(new ItemStack(result), recipe); }
    public ShapelessOreRecipe(Item  result, Object... recipe){ this(new ItemStack(result), recipe); }

    public ShapelessOreRecipe(ItemStack result, Object... recipe)
    {
        output = result.copy();
        for (Object in : recipe)
        {
            if (in instanceof ItemStack)
            {
                input.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                input.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
                input.add(OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    ShapelessOreRecipe(ShapelessRecipes recipe, Map<ItemStack, String> replacements)
    {
        output = recipe.getRecipeOutput();

        for(ItemStack ingred : ((List<ItemStack>)recipe.recipeItems))
        {
            Object finalObj = ingred;
            for(Entry<ItemStack, String> replace : replacements.entrySet())
            {
                if(OreDictionary.itemMatches(replace.getKey(), ingred, false))
                {
                    finalObj = OreDictionary.getOres(replace.getValue());
                    break;
                }
            }
            input.add(finalObj);
        }
    }

    @Override
    public int getRecipeSize(){ return input.size(); }

    @Override
    public ItemStack getRecipeOutput(){ return output; }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1){ return output.copy(); }

    @Override
    public boolean matches(InventoryCrafting var1, World world)
    {
        ArrayList required = new ArrayList(input);

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        match = checkItemEquals((ItemStack)next, slot);
                    }
                    else if (next instanceof ArrayList)
                    {
                        for (ItemStack item : (ArrayList<ItemStack>)next)
                        {
                            match = match || checkItemEquals(item, slot);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    private boolean checkItemEquals(ItemStack target, ItemStack input)
    {
        return (target.itemID == input.itemID && (target.getItemDamage() == OreDictionary.WILDCARD_VALUE || target.getItemDamage() == input.getItemDamage()));
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     * @return The recipes input vales.
     */
    public ArrayList getInput()
    {
    	return this.input;
    }
    
    @Override
    public boolean matches(IRecipe var1)
    {
    	if (var1 instanceof ShapelessRecipes)
    	{
    		ShapelessRecipes var2 = (ShapelessRecipes)var1;

    		if (output.getItem().itemID == var2.recipeOutput.getItem().itemID && output.stackSize == var2.recipeOutput.stackSize && output.getItemDamage() == var2.recipeOutput.getItemDamage() && var2.recipeItems.size() == var2.recipeItems.size())
    		{
    			for (int var3 = 0; var3 < var2.recipeItems.size(); ++var3)
    			{
    				ItemStack var4 = (ItemStack)var2.recipeItems.get(var3);
    				ItemStack var5 = (ItemStack)var2.recipeItems.get(var3);

    				if (var4 != null && var5 != null)
    				{
    					if (var4.getItem().itemID != var5.getItem().itemID || var4.stackSize != var5.stackSize || var4.getItemDamage() != var5.getItemDamage())
    					{
    						return false;
    					}
    				}
    				else if (var4 != null || var5 != null)
    				{
    					return false;
    				}
    			}

    			return true;
    		}
    	}

    	return false;
    }
}
