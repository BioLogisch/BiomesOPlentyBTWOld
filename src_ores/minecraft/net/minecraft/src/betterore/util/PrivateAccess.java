package net.minecraft.src.betterore.util;

import java.lang.reflect.Field;

public class PrivateAccess {

	private static Field field_modifiers = null;
	
	public static Object getPrivateValue(Class class1, Object obj, int i) {
		try
		{
			Field field = class1.getDeclaredFields()[i];
			field.setAccessible(true);
			return field.get(obj);
		}
		catch(Exception illegalaccessexception)
		{
			return null;
		}
	}

	public static void setPrivateValue(Class class1, Object obj, int i, Object obj1)
	       
	    {
	        try
	        {
	            Field field = class1.getDeclaredFields()[i];
	            field.setAccessible(true);
	            int j = field_modifiers.getInt(field);
	            if((j & 0x10) != 0)
	            {
	                field_modifiers.setInt(field, j & 0xffffffef);
	            }
	            field.set(obj, obj1);
	        }
	        catch(Exception illegalaccessexception)
	        {
	          
	        }
	    }
}
