package net.minecraft.src.biomesoplenty.world.layer;

import net.minecraft.src.GenLayer;
import net.minecraft.src.WorldType;

public abstract class BiomeLayer extends GenLayer
{
	public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldtype)
	{
		BiomeLayer obj = new BiomeLayerCreate(1L, true);
		obj = new BiomeLayerFuzzyZoom(2000L, (obj));
		obj = new BiomeLayerIsland(1L, (obj));
		obj = new BiomeLayerZoom(2001L, (obj));
		obj = new BiomeLayerIsland(2L, (obj));
		obj = new BiomeLayerZoom(2002L, (obj));
		obj = new BiomeLayerIsland(3L, (obj));
		obj = new BiomeLayerZoom(2003L, (obj));
		obj = new BiomeLayerIsland(4L, (obj));

		byte size = 4;

		BiomeLayer obj1 = obj;
		obj1 = BiomeLayerZoom.func_75915_a(1000L, ((obj1)), 0);
		obj1 = new BiomeLayerRiverInit(100L, ((obj1)));
		obj1 = BiomeLayerZoom.func_75915_a(1000L, ((obj1)), size + 2);
		obj1 = new BiomeLayerRiver(1L, ((obj1)));
		obj1 = new BiomeLayerSmooth(1000L, ((obj1)));
		BiomeLayer obj2 = obj;
		obj2 = BiomeLayerZoom.func_75915_a(1000L, ((obj2)), 0);
		obj2 = new BiomeLayerBiomes(200L, ((obj2)), worldtype);
		obj2 = BiomeLayerZoom.func_75915_a(1000L, ((obj2)), 2);
		obj2 = new BiomeLayerZoom(1000, ((obj2)));
		obj2 = new BiomeLayerShore(1000L, ((BiomeLayer)(obj2)));

		for (int i = 0 + 1; i < size; i++)
		{
			obj2 = new BiomeLayerZoom(1000 + i, ((obj2)));
		}

		obj2 = new BiomeLayerSmooth(1000L, ((obj2)));
		obj2 = new BiomeLayerRiverMix(100L, ((obj2)), ((obj1)));
		BiomeLayerRiverMix bwg4layerrivermix = ((BiomeLayerRiverMix)(obj2));
		BiomeLayerVoronoiZoom genlayervoronoizoom = new BiomeLayerVoronoiZoom(10L, ((obj2)));
		(obj2).initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);
		return (new GenLayer[]
				{
				obj2, genlayervoronoizoom, bwg4layerrivermix
				});
	}

	public BiomeLayer(long seed)
	{
	    super(seed);
	}
}
