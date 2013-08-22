package net.minecraft.src.betterore.common.util;

import java.io.File;

public class BOConfig {

	
	public static void init() {
		unpackConfigStandardFiles();
	}
	
	private static void unpackConfigStandardFiles() {
		BOFileUtils.unpackResourceFile("net/minecraft/src/betterore/resources/CustomOreGen_Config.xml", BOFileUtils.getConfigFile("CustomOreGen_Config.xml"));
		BOFileUtils.unpackResourceFile("net/minecraft/src/betterore/resources/MinecraftOres.xml",BOFileUtils.getConfigFile("modules/MinecraftOres.xml"));
	}
	
	
	
}
