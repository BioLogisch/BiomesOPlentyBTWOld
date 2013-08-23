package net.minecraft.src.betterore.util;

import java.awt.Frame;

import net.minecraft.src.betterore.common.util.BOLogger;
import net.minecraft.src.betterore.config.ui.ConfigErrorDialog;

public class BOErrorHandler {

	public static boolean onConfigError(Throwable error)
	{
		BOLogger.log.throwing("CustomOreGen.ServerState", "loadWorldConfig", error);
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

}
