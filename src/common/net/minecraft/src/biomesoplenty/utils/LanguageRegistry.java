package net.minecraft.src.biomesoplenty.utils;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class LanguageRegistry 
{	
	public static ArrayList<Localization> localizations = new ArrayList();
	
    public static void addStringLocalization(String key, String lang, String value)
    {
        localizations.add(new Localization(key, lang, value));
    }
    
    public static void addStringLocalization(String key, String value)
    {
        localizations.add(new Localization(key, value));
    }
    
    public static void addNameForObject(Object objectToName, String lang, String name)
    {
        String objectName;
        
        if (objectToName instanceof Item) 
        {
            objectName=((Item)objectToName).getUnlocalizedName();
        } 
        else if (objectToName instanceof Block) {
        	
            objectName=((Block)objectToName).getUnlocalizedName();
        } 
        else if (objectToName instanceof ItemStack) 
        {
            objectName=((ItemStack)objectToName).getItem().getUnlocalizedName((ItemStack)objectToName);
        } 
        else 
        {
            throw new IllegalArgumentException(String.format("Illegal object for naming %s",objectToName));
        }
        
        objectName+=".name";
        addStringLocalization(objectName, lang, name);
    }

    public static void addName(Object objectToName, String name)
    {
    	addNameForObject(objectToName, "en_US", name);
    }
}
