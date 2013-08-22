package net.minecraft.src.betterore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.WorldInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.GuiCreateWorld;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.betterore.common.util.BOLogger;
import net.minecraft.src.betterore.config.ui.WorldConfig;
import net.minecraft.src.betterore.util.ConfigDir;

public class BOOreGen {

	public static BOOreGen instance;

	public static void init()
	{

		instance = new BOOreGen();
		File configPath = ConfigDir.getConfigDir();

		

		WorldConfig var8 = null;

		while (var8 == null)
		{
			try
			{
				var8 = new WorldConfig();
			}
			catch (Exception var7)
			{
				if (!ServerState.onConfigError(var7))
				{
					break;
				}

				var8 = null;
			}
		}
	}



	public void onClientTick()
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.isSingleplayer())
		{
			this.onServerTicks();
		}


	}

	public void generateOres(int chunkX, int chunkZ, World world)
	{
		ServerState.checkIfServerChanged(MinecraftServer.getServer(), world.getWorldInfo());
		ServerState.onPopulateChunk(world, chunkX, chunkZ);
	}

	public void onServerStarteds()
	{
		BOLogger.log.fine("onServerStarted");

		ServerState.checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}

	public void onServerTicks()
	{
		ServerState.checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}



	
}
