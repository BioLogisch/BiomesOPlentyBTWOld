package net.minecraft.src.betterore.util;

import java.io.File;

import net.minecraft.client.Minecraft;

public class ConfigDir {

	private static String baseConfigDir = Minecraft.getMinecraftDir() + File.separator + "config";

	public static File getConfigDir() {
		File configdir = new File(baseConfigDir + File.separator + "betterores");
		if (!configdir.exists()) configdir.mkdirs();
		return configdir;
	}
	
}
