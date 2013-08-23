package net.minecraft.src.betterore.util;

import net.minecraft.src.betterore.common.util.BOLogger;

public class BOErrorHandler {

	public static boolean onConfigError(Throwable error)
	{
		BOLogger.log.throwing("CustomOreGen.ServerState", "loadWorldConfig", error);
		error.printStackTrace();
		return false;
	}

}
