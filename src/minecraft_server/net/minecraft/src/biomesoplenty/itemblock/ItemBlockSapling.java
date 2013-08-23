package net.minecraft.src.biomesoplenty.itemblock;

import net.minecraft.src.Block;
import net.minecraft.src.Icon;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemBlockSapling extends ItemBlock
{
	private static final String[] saplings = new String[] {"apple", "yellowautumn", "bamboo", "magic", "dark", "dead", "fir", "holy", "orangeautumn", "origin", "pinkcherry", "maple", "whitecherry", "hellbark", "jacaranda"};
	private static final int MAX = 14;

	public ItemBlockSapling(int par1)
	{
		super(par1 - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta & 15;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamageForDisplay() > MAX ? 0 : itemStack.getItemDamageForDisplay();
		return super.getUnlocalizedName() + "." + (new StringBuilder()).append(saplings[meta]).append("Sapling").toString();
	}
}
