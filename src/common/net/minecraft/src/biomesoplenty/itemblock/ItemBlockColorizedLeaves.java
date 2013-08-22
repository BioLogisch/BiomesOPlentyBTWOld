package net.minecraft.src.biomesoplenty.itemblock;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPColorizedLeaves;

public class ItemBlockColorizedLeaves extends ItemBlock
{
	public ItemBlockColorizedLeaves(int par1)
	{
		super(par1 - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		BlockBOPColorizedLeaves block = (BlockBOPColorizedLeaves)Block.blocksList[itemStack.itemID];
		
		return super.getUnlocalizedName() + "." + block.getLeafType(itemStack.getItemDamage());
	}
}
