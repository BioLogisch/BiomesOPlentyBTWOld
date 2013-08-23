package net.minecraft.src.betterore.common;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BlockSand;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RegionFileCache;
import net.minecraft.src.SaveFormatOld;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldServer;
import net.minecraft.src.betterore.common.config.BOWorldConfig;
import net.minecraft.src.betterore.common.generator.IBOOreDistribution;
import net.minecraft.src.betterore.common.registry.IBOEventHandler;
import net.minecraft.src.betterore.common.util.BOLogger;
import net.minecraft.src.betterore.common.util.BORuntime;
import net.minecraft.src.betterore.util.BOErrorHandler;

public class BOOreGenEventHandler implements IBOEventHandler {

	private MinecraftServer server = null;
	private Map<Integer, HashMap<ChunkCoordIntPair, int[]>> populatedChunks = new HashMap<Integer, HashMap<ChunkCoordIntPair, int[]>>();

	@Override
	public void onClientTickEnd(boolean paused) {

		Object minecraft = BORuntime.getObject(BORuntime.getMincraftClass(), "getMinecraft");
		if (minecraft != null && BORuntime.getBoolean(minecraft, "isSingleplayer"))
		{
			this.onServerTickEnd();
		}
	}

	@Override
	public void onClientTickStart(boolean paused) {

	}

	@Override
	public void onServerWillStart(MinecraftServer server) {
		checkIfServerChanged(server, (WorldInfo)null);
	}

	@Override
	public void onServerDidStart(MinecraftServer server) {
		checkIfServerChanged(server, (WorldInfo)null);
	}

	@Override
	public void onServerTickStart() {
		checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}

	@Override
	public void onServerTickEnd() {
		checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}

	@Override
	public void onWorldsDidLoad() {
		checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}


	public void populateDistributions(Collection<IBOOreDistribution> distributions, World world, int chunkX, int chunkZ)
	{
		BlockSand.fallInstantly = true;
		world.scheduledUpdatesAreImmediate = true;
		for (IBOOreDistribution dist : distributions) {
			dist.generate(world, chunkX, chunkZ);
			dist.populate(world, chunkX, chunkZ);
			dist.cull();
		}

		world.scheduledUpdatesAreImmediate = false;
		BlockSand.fallInstantly = false;
	}
	
	
	
	@Override
	public void onPostProcessChunk(World world, Random rand, int chunkX, int chunkZ) {
		checkIfServerChanged(server, null);

		
    	BOWorldConfig cfg = BOWorldConfig.getWorldConfig(world);
    	
    	
        Integer dimesionId = Integer.valueOf(world.provider.dimensionId);
        
        HashMap<ChunkCoordIntPair, int[]> dimChunkMap = populatedChunks.get(dimesionId);
        if (dimChunkMap == null)
        {
            dimChunkMap = new HashMap();
            populatedChunks.put(dimesionId, dimChunkMap);
        }


        ChunkCoordIntPair neighborMax = new ChunkCoordIntPair(chunkX, chunkZ);

        int[] chunkXList = dimChunkMap.get(neighborMax);
        if (chunkXList == null)
        {
        	chunkXList = new int[16];
        	dimChunkMap.put(neighborMax, chunkXList);
        }
        chunkXList[chunkX & 15] |= 65537 << (chunkZ & 15);

        int chunkDistance = (int)(cfg.deferredPopulationRange + 16);
        

        
        int expectedNeigbourCount = 4 * (chunkDistance / 16) * ((chunkDistance / 16) + 1) + 1;
        //BOLogger.log.fine("expectedNeigbourCount : " + expectedNeigbourCount );

        for (int cX = chunkX - chunkDistance; cX <= chunkX + chunkDistance; cX += 16)
        {
            for (int cZ = chunkZ - chunkDistance; cZ <= chunkZ + chunkDistance; cZ += 16)
            {
                int neighborCount = 0;
                for (int iX = cX - chunkDistance; iX <= cX + chunkDistance; iX += 16)
                {
                    for (int iZ = cZ - chunkDistance; iZ <= cZ + chunkDistance; iZ += 16)
                    {

                    	
                		///BOLogger.log.fine("onPostProcessChunk" + chunkX + ":" + chunkZ + " Check against: " + iX + ":" + iZ);

                        ChunkCoordIntPair chunkKey = new ChunkCoordIntPair(iX, iZ);

                        int[] chunkData = dimChunkMap.get(chunkKey);

                        if (chunkData == null)
                        {
                            chunkData = new int[16];
                            dimChunkMap.put(chunkKey, chunkData);
                        }

                        if ((chunkData[iX & 15] >>> (iZ & 15) & 65536) == 0)
                        {
                            boolean populated = isChunkSavedPopulated(world, iX, iZ);

                            chunkData[iX & 15] |= (populated ? 65537 : 65536) << (iZ & 15);
                        }

                        if ((chunkData[iX & 15] >>> (iZ & 15) & 1) != 0)
                        {
                            ++neighborCount;
                        }
                    }
                }
        		///BOLogger.log.fine("onPostProcessChunk" + chunkX + ":" + chunkZ + " Check against: " + neighborCount + ":" + expectedNeigbourCount);

                if (neighborCount == expectedNeigbourCount)
                {
                    populateDistributions(cfg.getOreDistributions(), world, cX, cZ);
                }
            }
        }

    }
	
	public boolean checkIfServerChanged(MinecraftServer currentServer, WorldInfo worldInfo)
    {
        if (server == currentServer)
        {
            return false;
        }
        else
        {
            if (currentServer != null && worldInfo == null)
            {
                if (currentServer.worldServers == null)
                {
                    return false;
                }

                for (WorldServer world : currentServer.worldServers) {
                    if (world != null)
                    {
                        worldInfo = world.getWorldInfo();
                    }

                    if (worldInfo != null)
                    {
                        break;
                    }                	
                }
                
                if (worldInfo == null)
                {
                    return false;
                }
            }

            onServerChanged(currentServer, worldInfo);
            return true;
        }
    }
	
	public void onServerChanged(MinecraftServer server, WorldInfo worldInfo)
    {
    	BOWorldConfig.clearWorldConfigs();;
        BOWorldConfig.loadedOptionOverrides[1] = BOWorldConfig.loadedOptionOverrides[2] = null;
        populatedChunks.clear();

        this.server = server;
       // BOLogger.log.finer("Server world changed to " + worldInfo.getWorldName());
        BiomeGenBase[] biomeList = BiomeGenBase.biomeList;
        int saveFormat = biomeList.length;

        for (int cfg = 0; cfg < saveFormat; ++cfg)
        {
            BiomeGenBase ex = biomeList[cfg];

            if (ex != null && ex.theBiomeDecorator != null)
            {
                patchBiomeDecorator(ex.theBiomeDecorator);
            }
        }

        File saveDir = null;
        ISaveFormat var9 = server.getActiveAnvilConverter();

        if (var9 != null && var9 instanceof SaveFormatOld)
        {
            saveDir = ((SaveFormatOld)var9).getSavesDirectory();
        }

        saveDir = new File(saveDir, server.getFolderName());
        BOWorldConfig worldConfig = null;

        while (worldConfig == null)
        {
            try
            {
                worldConfig = new BOWorldConfig(worldInfo, saveDir);
                BOWorldConfig.validateOptions(worldConfig.getConfigOptions(), false);
                BOWorldConfig.validateDistributions(worldConfig.getOreDistributions(), false);

            }
            catch (Exception var7)
            {
                if (!BOErrorHandler.onConfigError(var7))
                {
                    break;
                }
                var7.printStackTrace();
                worldConfig = null;
            }
        }
    }
	
	private static void patchBiomeDecorator(BiomeDecorator decorator)
    {
		
        BOLogger.log.throwing("patchBiomeDecorator", "not supported",new Exception("Not supported"));

		/*
        try
        {
        //    WorldGenerator ex = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 10);
        //    WorldGenerator ironGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 11);
        //    WorldGenerator goldGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 12);
        //    WorldGenerator redstoneGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 13);
        //    WorldGenerator diamondGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 14);
        //    WorldGenerator lapisGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 15);
        //    PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 10, new WorldGenEmpty(ex));
        //    PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 11, new WorldGenEmpty(ironGen));
        //    PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 12, new WorldGenEmpty(goldGen));
        //    PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 13, new WorldGenEmpty(redstoneGen));
        //    PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 14, new WorldGenEmpty(diamondGen));
        //    PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 15, new WorldGenEmpty(lapisGen));
        }
        catch (Exception var7)
        {
            BOLogger.log.throwing("CustomOreGenBase", "patchBiomeDecorator", var7);
        }
        */
    }


	private boolean isChunkSavedPopulated(World world, int chunkX, int chunkZ)
	{
		File saveFolder = BOWorldConfig.getWorldConfig(world).dimensionDir;

		DataInputStream stream = RegionFileCache.getChunkInputStream(saveFolder, chunkX, chunkZ);
		if (stream != null)
		{
			BOLogger.log.fine("Read data for chunk : " + chunkX + ":" + chunkZ);
			try
			{
				NBTTagCompound ex = CompressedStreamTools.read(stream);
				if (ex.hasKey("Level") && ex.getCompoundTag("Level").getBoolean("TerrainPopulated"))
				{
					return true;
				}
			}
			catch (IOException e)
			{
				BOLogger.log.throwing("Error on:", "isChunkSavedPopulated: " + chunkX + ":" + chunkZ, e);;
			}
		}

		return false;
	}

	

}
