package net.minecraft.src.betterore.common.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.World;
import net.minecraft.src.WorldServer;

public class BOTickRegistry {

	private static List<IBOEventHandler> handlers = new ArrayList<IBOEventHandler>();
	
	public static void registerHandler(IBOEventHandler handler) {
		handlers.add(handler);
	}
	
	public static void clientTickEnd(boolean paused) {
		for (IBOEventHandler handler : handlers) {
			handler.onClientTickEnd(paused);
		}
	}

	public static void clientTickStart(boolean paused) {
		for (IBOEventHandler handler : handlers) {
			handler.onClientTickStart(paused);
		}
	}

	public static void serverWillStart(MinecraftServer server) {
		for (IBOEventHandler handler : handlers) {
			handler.onServerWillStart(server);
		}
	}
	
	public static void serverDidStart(MinecraftServer server) {
		for (IBOEventHandler handler : handlers) {
			handler.onServerDidStart(server);
		}
	}
	
	public static void serverTickStart() {
		for (IBOEventHandler handler : handlers) {
			handler.onServerTickStart();
		}
	}
	
	public static void serverTickEnd() {
		for (IBOEventHandler handler : handlers) {
			handler.onServerTickEnd();
		}
	}

	public static void worldsDidLoad() {
		for (IBOEventHandler handler : handlers) {
			handler.onWorldsDidLoad();
		}
	}
	
	public static void postProcessChunk(World world, Random rand, int x, int z) {
		for (IBOEventHandler handler : handlers) {
			handler.onPostProcessChunk(world, rand, x, z);
		}
	}
	

}
