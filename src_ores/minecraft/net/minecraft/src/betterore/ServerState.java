package net.minecraft.src.betterore;

import java.awt.Frame;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BlockSand;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.GuiCreateWorld;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RegionFileCache;
import net.minecraft.src.SaveFormatOld;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldServer;
import net.minecraft.src.betterore.config.ui.ConfigErrorDialog;
import net.minecraft.src.betterore.config.ui.ConfigOption;
import net.minecraft.src.betterore.config.ui.GuiCustomOreGenSettings;
import net.minecraft.src.betterore.config.ui.WorldConfig;
import net.minecraft.src.betterore.config.ui.GuiCustomOreGenSettings.GuiOpenMenuButton;
import net.minecraft.src.betterore.generator.IOreDistribution;
import net.minecraft.src.betterore.generator.WorldGenEmpty;
import net.minecraft.src.betterore.util.Logger;
import net.minecraft.src.betterore.util.PrivateAccess;





public class ServerState
{
    private static MinecraftServer _server = null;
    private static Map _worldConfigs = new HashMap();
    private static Map _populatedChunks = new HashMap();

    private static boolean isChunkSavedPopulated(World world, int chunkX, int chunkZ)
    {
        File saveFolder = getWorldConfig(world).dimensionDir;
        DataInputStream stream = RegionFileCache.getChunkInputStream(saveFolder, chunkX, chunkZ);

        if (stream != null)
        {
            try
            {
                NBTTagCompound ex = CompressedStreamTools.read(stream);

                if (ex.hasKey("Level") && ex.getCompoundTag("Level").getBoolean("TerrainPopulated"))
                {
                    return true;
                }
            }
            catch (IOException var6)
            {
                ;
            }
        }

        return false;
    }

    private static void patchBiomeDecorator(BiomeDecorator decorator)
    {
        try
        {
            WorldGenerator ex = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 10);
            WorldGenerator ironGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 11);
            WorldGenerator goldGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 12);
            WorldGenerator redstoneGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 13);
            WorldGenerator diamondGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 14);
            WorldGenerator lapisGen = (WorldGenerator)PrivateAccess.getPrivateValue(BiomeDecorator.class, decorator, 15);
            PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 10, new WorldGenEmpty(ex));
            PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 11, new WorldGenEmpty(ironGen));
            PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 12, new WorldGenEmpty(goldGen));
            PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 13, new WorldGenEmpty(redstoneGen));
            PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 14, new WorldGenEmpty(diamondGen));
            PrivateAccess.setPrivateValue(BiomeDecorator.class, decorator, 15, new WorldGenEmpty(lapisGen));
        }
        catch (Exception var7)
        {
            Logger.log.throwing("CustomOreGenBase", "patchBiomeDecorator", var7);
        }
    }

    public static WorldConfig getWorldConfig(World world)
    {
        WorldConfig cfg = (WorldConfig)_worldConfigs.get(world);

        while (cfg == null)
        {
            try
            {
                cfg = new WorldConfig(world);
                validateOptions(cfg.getConfigOptions(), true);
                validateDistributions(cfg.getOreDistributions(), true);
            }
            catch (Exception var4)
            {
                if (onConfigError(var4))
                {
                    cfg = null;
                    continue;
                }

                cfg = WorldConfig.createEmptyConfig();
            }

            _worldConfigs.put(world, cfg);
        }

        return cfg;
    }

    public static void clearWorldConfig(World world)
    {
        _worldConfigs.remove(world);
    }

    public static boolean onConfigError(Throwable error)
    {
        Logger.log.throwing("CustomOreGen.ServerState", "loadWorldConfig", error);
        Frame[] frames = Frame.getFrames();

        if (frames != null && frames.length > 0)
        {
            switch ((new ConfigErrorDialog()).showDialog(frames[0], error))
            {
                case 1:
                    return true;

                case 2:
                    return false;
            }
        }
        error.printStackTrace();
        return false;
    }

    public static void validateDistributions(Collection distributions, boolean cull) throws IllegalStateException
    {
        Iterator it = distributions.iterator();

        while (it.hasNext())
        {
            IOreDistribution dist = (IOreDistribution)it.next();

            if (!dist.validate() && cull)
            {
                it.remove();
            }
        }
    }

    public static void validateOptions(Collection options, boolean cull)
    {
        Iterator it = options.iterator();

        while (it.hasNext())
        {
            ConfigOption option = (ConfigOption)it.next();

            if (cull && option instanceof ConfigOption.DisplayGroup)
            {
                it.remove();
            }
        }
    }

    public static void populateDistributions(Collection<IOreDistribution> distributions, World world, int chunkX, int chunkZ)
    {
    	
    	Logger.log.fine("Pop :" + distributions);
        BlockSand.fallInstantly = true;
        world.scheduledUpdatesAreImmediate = true;
        
        for (IOreDistribution dist : distributions) {
        	dist.generate(world, chunkX, chunkZ);
            dist.populate(world, chunkX, chunkZ);
            dist.cull();
        }
        
        world.scheduledUpdatesAreImmediate = false;
        BlockSand.fallInstantly = false;
    }

    

    public static void onPopulateChunk(World world, int chunkX, int chunkZ)
    {
    	WorldConfig cfg = getWorldConfig(world);
        Object dimChunkMap = null;
        Integer cRange = Integer.valueOf(world.provider.dimensionId);
        dimChunkMap = (Map)_populatedChunks.get(cRange);

        if (dimChunkMap == null)
        {
            dimChunkMap = new HashMap();
            _populatedChunks.put(cRange, dimChunkMap);
        }

        ChunkCoordIntPair neighborMax = new ChunkCoordIntPair(chunkX >>> 4, chunkZ >>> 4);
        int[] cX = (int[])((Map)dimChunkMap).get(neighborMax);

        if (cX == null)
        {
            cX = new int[16];
            ((Map)dimChunkMap).put(neighborMax, cX);
        }

        cX[chunkX & 15] |= 65537 << (chunkZ & 15);
        int var16 = (cfg.deferredPopulationRange + 15) / 16;
        int var17 = 4 * var16 * (var16 + 1) + 1;

        for (int var18 = chunkX - var16; var18 <= chunkX + var16; ++var18)
        {
            for (int cZ = chunkZ - var16; cZ <= chunkZ + var16; ++cZ)
            {
                int neighborCount = 0;

                for (int iX = var18 - var16; iX <= var18 + var16; ++iX)
                {
                    for (int iZ = cZ - var16; iZ <= cZ + var16; ++iZ)
                    {
                        ChunkCoordIntPair chunkKey = new ChunkCoordIntPair(iX >>> 4, iZ >>> 4);
                        int[] chunkData = (int[])((Map)dimChunkMap).get(chunkKey);

                        if (chunkData == null)
                        {
                            chunkData = new int[16];
                            ((Map)dimChunkMap).put(chunkKey, chunkData);
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
            	Logger.log.fine("Pop :" + neighborCount + "=" + var17);

                if (neighborCount == var17)
                {
                    populateDistributions(cfg.getOreDistributions(), world, var18, cZ);
                }
            }
        }
    }

    public static boolean checkIfServerChanged(MinecraftServer currentServer, WorldInfo worldInfo)
    {
        if (_server == currentServer)
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

    public static void onServerChanged(MinecraftServer server, WorldInfo worldInfo)
    {
        _worldConfigs.clear();
        WorldConfig.loadedOptionOverrides[1] = WorldConfig.loadedOptionOverrides[2] = null;
        _populatedChunks.clear();

        _server = server;
        Logger.log.finer("Server world changed to " + worldInfo.getWorldName());
        BiomeGenBase[] worldBaseDir = BiomeGenBase.biomeList;
        int saveFormat = worldBaseDir.length;

        for (int cfg = 0; cfg < saveFormat; ++cfg)
        {
            BiomeGenBase ex = worldBaseDir[cfg];

            if (ex != null && ex.theBiomeDecorator != null)
            {
                patchBiomeDecorator(ex.theBiomeDecorator);
            }
        }

        File var8 = null;
        ISaveFormat var9 = _server.getActiveAnvilConverter();

        if (var9 != null && var9 instanceof SaveFormatOld)
        {
            var8 = (File)PrivateAccess.getPrivateValue(SaveFormatOld.class, (SaveFormatOld)var9, 0);
        }

        var8 = new File(var8, _server.getFolderName());
        WorldConfig var10 = null;

        while (var10 == null)
        {
            try
            {
                var10 = new WorldConfig(worldInfo, var8);
                validateOptions(var10.getConfigOptions(), false);
                validateDistributions(var10.getOreDistributions(), false);

            }
            catch (Exception var7)
            {
                if (!onConfigError(var7))
                {
                    break;
                }
                var7.printStackTrace();
                var10 = null;
            }
        }
    }

  

    
}
