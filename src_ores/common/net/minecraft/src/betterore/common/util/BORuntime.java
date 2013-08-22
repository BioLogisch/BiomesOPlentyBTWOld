package net.minecraft.src.betterore.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BORuntime {

	public static boolean isClient() {
		return (getMincraftClass() != null);
	}
	
	public static boolean isServer() {
		return (getMincraftClass() == null);
	}
	
	public static Class getMincraftClass() {
		Class<?> cls = null;
		try {
			cls = Class.forName("net.minectaft.client.Minecraft");
		} catch (Exception e) {
			
		}
		return cls;
	}
	
	public static String getString(Class<?> cls, String staticMethodName) {
		String result = null;
		try {
			Method  m = cls.getMethod(staticMethodName);
			result = (String)m.invoke(null, (Object)null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
