package net.minecraft.src.biomesoplenty.items;

import net.minecraft.src.Icon;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;

public class ItemBOPBark extends Item
{
	private static final String[] barkTypes = new String[] {"acacia", "cherry", "dark", "fir", "holy", "magic", "mangrove", "palm", "redwood", "willow", "pine", "hellbark", "jacaranda"};

	private Icon[] textures;

	public ItemBOPBark(int par1)
	{
		super(par1);
		setHasSubtypes(true);
        setMaxDamage(0);
		setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= barkTypes.length) {
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + barkTypes[meta];
	}
}
