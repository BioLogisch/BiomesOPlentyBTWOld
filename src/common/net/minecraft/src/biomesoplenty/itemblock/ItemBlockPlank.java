package net.minecraft.src.biomesoplenty.itemblock;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemBlockPlank extends ItemBlock
{
	private static final String[] woodTypes = new String[] {"acaciaPlank", "cherryPlank", "darkPlank", "firPlank", "holyPlank", "magicPlank", "mangrovePlank", "palmPlank", "redwoodPlank", "willowPlank", "bambooThatching", "pinePlank", "hellBarkPlank", "jacarandaPlank"};

	public ItemBlockPlank(int par1)
	{
		super(par1 - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
    public int GetFurnaceBurnTime(int var1)
    {
		switch (var1)
		{
		case 0:
			return 300;
			
		case 1:
			return 400;
			
		case 2:
			return 300;
				
		case 3:
			return 225;
			
		case 4:
			return 400;
			
		case 5:
			return 350;
			
		case 6:
			return 300;
			
		case 7:
			return 500;
			
		case 8:
			return 450;
			
		case 9:
			return 375;
			
		case 10:
			return 250;
			
		case 11:
			return 300;
			
		case 12:
			return 650;
			
		case 13:
			return 275;
		
		default:
			return 400;
		}
    }

	@Override
	public int getMetadata(int meta)
	{
		return meta & 15;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= woodTypes.length) {
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + woodTypes[meta];
	}
}
