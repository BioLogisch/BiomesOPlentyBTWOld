package net.minecraft.src.biomesoplenty.items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
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
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < barkTypes.length; ++i)
		{
			par3List.add(new ItemStack(par1, 1, i));
		}
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

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		textures = new Icon[barkTypes.length];

		for (int i = 0; i < barkTypes.length; ++i) {
			textures[i] = iconRegister.registerIcon("biomesoplenty:" +  "bark_" + barkTypes[i]);
		}
	}

	@Override
	public Icon getIconFromDamage(int meta)
	{
		return textures[meta];
	}
}
