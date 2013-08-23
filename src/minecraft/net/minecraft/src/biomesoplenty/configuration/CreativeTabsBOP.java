package net.minecraft.src.biomesoplenty.configuration;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;

public class CreativeTabsBOP extends CreativeTabs
{
	public CreativeTabsBOP(int position, String tabID)
	{
		super(position, tabID);
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(BOPAPIBlocks.saplings, 1, 6);
	}
}
