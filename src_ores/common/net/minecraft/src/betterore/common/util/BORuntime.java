package net.minecraft.src.betterore.common.util;

import java.io.File;
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
			cls = Class.forName("net.minecraft.client.Minecraft");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cls;
	}
	
	public static String getString(Class<?> cls, String staticMethodName) {
		String result = null;
		try {
			Method  m = cls.getMethod(staticMethodName);
			result = (String)m.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static File getFile(Class<?> cls, String staticMethodName) {
		File result = null;
		try {
			Method  m = cls.getMethod(staticMethodName);
			result = (File)m.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object getObject(Class<?> cls, String staticMethodName) {
		Object result = null;
		try {
			Method  m = cls.getMethod(staticMethodName);
			result = (Object)m.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Boolean getBoolean(Object object, String methodName) {
		Boolean result = null;
		try {
			Method  m = object.getClass().getMethod(methodName);
			result = (Boolean)m.invoke(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
