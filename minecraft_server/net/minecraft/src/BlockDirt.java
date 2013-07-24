package net.minecraft.src;

import net.minecraft.src.biomesoplenty.BiomesOPlenty;

public class BlockDirt extends Block
{
    protected BlockDirt(int par1)
    {
        super(par1, Material.ground);
        this.setCreativeTab(CreativeTabs.tabBlock);
        BiomesOPlenty.vanillaConstruct();//BIOMES O PLENTY
    }
}
