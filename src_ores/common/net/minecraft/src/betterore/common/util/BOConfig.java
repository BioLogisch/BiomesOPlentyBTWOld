package net.minecraft.src.betterore.common.util;

import net.minecraft.src.betterore.common.config.BOWorldConfig;
import net.minecraft.src.betterore.util.BOErrorHandler;

public class BOConfig {

	
	public static void init() {
		unpackConfigStandardFiles();
	}
	
	private static void unpackConfigStandardFiles() {
		BOFileUtils.unpackResourceFile("net/minecraft/src/betterore/common/resources/CustomOreGen_Config.xml", BOFileUtils.getConfigFile("CustomOreGen_Config.xml"));
		BOFileUtils.unpackResourceFile("net/minecraft/src/betterore/common/resources/MinecraftOres.xml",BOFileUtils.getConfigFile("modules/MinecraftOres.xml"));
	}
	
	private static void validateConfigs() {
		BOWorldConfig config = null;

		while (config == null)
		{
			try
			{
				config = new BOWorldConfig();
			}
			catch (Exception exception)
			{
				if (!BOErrorHandler.onConfigError(exception))
				{
					break;
				}

				config = null;
			}
		}
	}
	
}
