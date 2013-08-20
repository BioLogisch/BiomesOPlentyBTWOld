package net.minecraft.src.betterore.util;

import net.minecraft.src.FCAddOnHandler;

public class Logger {

	public static Logger log = new Logger();
	
	
	
	public void warning(String message) {
        FCAddOnHandler.LogMessage("[BetterOre] WARN:" + message);

	}
	
	public void fine(String message) {
        FCAddOnHandler.LogMessage("[BetterOre] FINE:" + message);

	}
	
	public void finer(String message) {
        FCAddOnHandler.LogMessage("[BetterOre] FINER:" + message);

	}
	
	public void throwing(String message,String omessage, Throwable e) {
        FCAddOnHandler.LogMessage("[BetterOre] FATAL:" + message + " " + omessage + " " + e.getMessage());

	}
	
}
