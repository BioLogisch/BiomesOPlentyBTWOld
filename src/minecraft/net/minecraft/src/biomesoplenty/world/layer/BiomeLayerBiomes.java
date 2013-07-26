package net.minecraft.src.biomesoplenty.world.layer;

import java.util.ArrayList;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IntCache;
import net.minecraft.src.WorldType;
import net.minecraft.src.biomesoplenty.configuration.BOPBiomes;

public class BiomeLayerBiomes extends BiomeLayer
{
	private ArrayList<BiomeGenBase> biomelist;

    public BiomeLayerBiomes(long par1, BiomeLayer par3GenLayer, WorldType par4WorldType)
    {
        super(par1);
		parent = par3GenLayer;
		biomelist = BOPBiomes.getBiomesForWorldType();
	} 

    @Override
	public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);
        
        for (int var7 = 0; var7 < par4; ++var7)
        {
            for (int var8 = 0; var8 < par3; ++var8)
            {
                this.initChunkSeed((long)(var8 + par1), (long)(var7 + par2));
                int var9 = var5[var8 + var7 * par3];
				if (var9 == 0)
				{
					var6[var8 + var7 * par3] = 0;
				}
				else 
				{
					var6[var8 + var7 * par3] = biomelist.get(this.nextInt(biomelist.size())).biomeID;
				}
            }
        }
        return var6;
    }
}