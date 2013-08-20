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
import net.minecraft.src.betterore.config.ui.WorldConfig;
import net.minecraft.src.betterore.util.ConfigDir;
import net.minecraft.src.betterore.util.Logger;

public class BOOreGen {

	public static BOOreGen instance;

	public static void init()
	{

		instance = new BOOreGen();
		File configPath = ConfigDir.getConfigDir();

		unpackResourceFile("net/minecraft/src/betterore/resources/CustomOreGen_Config.xml", new File(configPath, "CustomOreGen_Config.xml"));
		unpackResourceFile("net/minecraft/src/betterore/resources/MinecraftOres.xml", new File(configPath, "modules/MinecraftOres.xml"));

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
			this.onServerTick();
		}


	}

	public void generateOres(int chunkX, int chunkZ, World world)
	{
		ServerState.checkIfServerChanged(MinecraftServer.getServer(), world.getWorldInfo());
		ServerState.onPopulateChunk(world, chunkX, chunkZ);
	}

	public void onServerStarted()
	{
		Logger.log.fine("onServerStarted");

		ServerState.checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}

	public void onServerTick()
	{
		ServerState.checkIfServerChanged(MinecraftServer.getServer(), (WorldInfo)null);
	}



	public static boolean unpackResourceFile(String resourceName, File destination)
	{
		if (destination.exists())
		{
			return false;
		}
		else
		{
			try
			{
				Logger.log.fine("Unpacking \'" + BOOreGen.class.getClassLoader().getResource(resourceName) + "\' ...");
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				InputStream ex = classLoader.getResourceAsStream(resourceName);
				if (ex == null) {
					throw new RuntimeException("Failed to unpack resource \'" + resourceName + "\'");
				}
				BufferedOutputStream streamOut = new BufferedOutputStream(new FileOutputStream(destination));
				byte[] buffer = new byte[1024];
				boolean len = false;
				int len1;

				while ((len1 = ex.read(buffer)) >= 0)
				{
					streamOut.write(buffer, 0, len1);
				}

				ex.close();
				streamOut.close();
				return true;
			}
			catch (Exception var6)
			{
				throw new RuntimeException("Failed to unpack resource \'" + resourceName + "\'", var6);
			}
		}
	}
}
