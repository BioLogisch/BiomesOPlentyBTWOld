package net.minecraft.src.biomesoplenty.configuration;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FCRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.api.BOPAPIItems;
import net.minecraft.src.biomesoplenty.handlers.BOPRecipesLogChopping;
import net.minecraft.src.biomesoplenty.oredict.OreDictionary;

public class BOPCrafting 
{
	public static void init()
	{
		addOreRegistrations();
		addCraftingRecipes();
		addCauldronRecipes();
		addKilnRecipes();
		addCustomRecipeHandlers();
	}
	
	private static void addCraftingRecipes()
	{
		//Redwood
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab2.get(), 6, 0), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 8)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.redwoodStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 8)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.redwoodStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 8)});

		//Willow
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab2.get(),6,1), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 9)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.willowStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 9)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.willowStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 9)});

		//Acacia
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,0), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 0)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.acaciaStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 0)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.acaciaStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 0)});

		//Fir
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,3), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 3)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.firStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 3)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.firStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 3)});

		//Cherry
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,1), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 1)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.cherryStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 1)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.cherryStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 1)});

		//Dark
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,2), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 2)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.darkStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 2)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.darkStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 2)});

		//Magic
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,5), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 5)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.magicStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 5)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.magicStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 5)});

		//Palm
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,7), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 7)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.palmStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 7)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.palmStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 7)});

		//Mangrove
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,6), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 6)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.mangroveStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 6)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.mangroveStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 6)});

		//Holy
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab1.get(),6,4), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks,1,4)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.holyStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks,1,4)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.holyStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks,1,4)});

		//GameRegistry.addRecipe(new ItemStack(Blocks.redRock.get(), 4, 2), new Object[] {"RR", "RR", 'R', new ItemStack(Blocks.redRock.get(),1,0)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.holyStone.get(), 4, 2), new Object[] {"RR", "RR", 'R', new ItemStack(Blocks.holyStone.get(),1,0)});

		//Pine
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab2.get(), 6, 2), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 11)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.pineStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 11)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.pineStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 11)});

		//Hellbark
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab2.get(), 6, 3), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 12)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.hellBarkStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 12)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.hellBarkStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 12)});

		//Jacaranda
		//GameRegistry.addRecipe(new ItemStack(Blocks.woodenSingleSlab2.get(), 6, 4), new Object[] {"RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 13)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.jacarandaStairs.get(), 4), new Object[] {"  R", " RR", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 13)});
		//GameRegistry.addRecipe(new ItemStack(Blocks.jacarandaStairs.get(), 4), new Object[] {"R  ", "RR ", "RRR", 'R', new ItemStack(BOPAPIBlocks.planks, 1, 13)});
	}
	
	private static void addCauldronRecipes()
	{
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 2, 0)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 8, 1)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 8, 2)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 3, 3)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 2, 4)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 2, 5)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 8, 6)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 2, 7)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 2, 8)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 3, 9)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 3, 10)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 12, 11)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcTannedLeather, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcScouredLeather, 1), new ItemStack(BOPAPIItems.bark, 8, 12)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 2, 0)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 8, 1)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 8, 2)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 3, 3)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 2, 4)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 2, 5)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 8, 6)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 2, 7)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 2, 8)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 3, 9)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 3, 10)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 12, 11)});
        FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemTannedLeatherCut, 2), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcDung, 1), new ItemStack(FCBetterThanWolves.fcItemScouredLeatherCut, 2), new ItemStack(BOPAPIItems.bark, 8, 12)});
	}
	
	private static void addKilnRecipes()
	{
		addKilnRecipe(BOPAPIBlocks.logs1, Item.coal.itemID, 1);
		addKilnRecipe(BOPAPIBlocks.logs2, Item.coal.itemID, 1);
		addKilnRecipe(BOPAPIBlocks.logs3, Item.coal.itemID, 1);
		addKilnRecipe(BOPAPIBlocks.logs4, Item.coal.itemID, 1);
	}
	
	private static void addCustomRecipeHandlers()
	{
        CraftingManager.getInstance().getRecipeList().add(new BOPRecipesLogChopping());
	}
	
	private static void addOreRegistrations()
	{
		OreDictionary.registerOre("plankWood", new ItemStack(BOPAPIBlocks.planks, 1, OreDictionary.WILDCARD_VALUE));
		
		for (int i = 0; i <= 3; i++)
		{
			OreDictionary.registerOre("logWood", new ItemStack(BOPAPIBlocks.logs1, 1, i));
			OreDictionary.registerOre("logWood", new ItemStack(BOPAPIBlocks.logs2, 1, i));
			OreDictionary.registerOre("logWood", new ItemStack(BOPAPIBlocks.logs3, 1, i));
			if (i < 3)
			{
				OreDictionary.registerOre("logWood", new ItemStack(BOPAPIBlocks.logs4, 1, i));
			}
		}
	}
	
	public static void addKilnRecipe(Block block, int id, int meta)
	{
        block.SetCanBeCookedByKiln(true);
        block.SetItemIndexDroppedWhenCookedByKiln(id);
        block.SetItemDamageDroppedWhenCookedByKiln(meta);
	}
}
