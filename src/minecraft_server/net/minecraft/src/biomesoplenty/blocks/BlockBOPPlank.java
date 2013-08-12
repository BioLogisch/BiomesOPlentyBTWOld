package net.minecraft.src.biomesoplenty.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.Icon;
import net.minecraft.src.Material;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;

public class BlockBOPPlank extends Block
{
	private static final String[] woodTypes = new String[] {"plank_acacia", "plank_cherry", "plank_dark", "plank_fir", "plank_holy", "plank_magic", "plank_mangrove", "plank_palm", "plank_redwood", "plank_willow", "bamboothatching", "plank_pine", "plank_hell_bark", "plank_jacaranda"};
	private Icon[] textures;

	public BlockBOPPlank(int blockID)
	{
		super(blockID, Material.wood);
		setBurnProperties(blockID, 5, 20);
		setHardness(1.0F);
		setResistance(5.0F);
		this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
}
