package net.minecraft.src.biomesoplenty.itemblock;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FCUtilsItem;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.api.BOPAPIItems;
import net.minecraft.src.biomesoplenty.blocks.BlockBOPLog;

public class ItemBlockLog extends ItemBlock
{
	public ItemBlockLog(int par1)
	{
		super(par1 - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public void OnUsedInCrafting(EntityPlayer var1, ItemStack var2)
	{
		if (!var1.worldObj.isRemote && var2.itemID == BOPAPIBlocks.planks.blockID)
		{
			FCUtilsItem.EjectStackWithRandomVelocity(var1.worldObj, var1.posX, var1.posY, var1.posZ, new ItemStack(FCBetterThanWolves.fcSawDust, 2, 0));
			FCUtilsItem.EjectStackWithRandomVelocity(var1.worldObj, var1.posX, var1.posY, var1.posZ, new ItemStack(BOPAPIItems.bark, 1, var2.getItemDamage()));
		}
	}
	
	@Override
    public int GetFurnaceBurnTime(int var1)
    {
		switch (var1)
		{
		case 0:
			return 1200;
			
		case 1:
			return 1600;
			
		case 2:
			return 1200;
				
		case 3:
			return 900;
			
		case 4:
			return 1600;
			
		case 5:
			return 1400;
			
		case 6:
			return 1200;
			
		case 7:
			return 2000;
			
		case 8:
			return 1800;
			
		case 9:
			return 1500;
			
		case 10:
			return 200;
			
		case 11:
			return 150;
			
		case 12:
			return 2600;
			
		case 13:
			return 1000;
		
		default:
			return 1600;
		}
    }

	@Override
	public int getMetadata(int meta)
	{
		return meta & 3;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		BlockBOPLog block = (BlockBOPLog)Block.blocksList[itemStack.itemID];
		return super.getUnlocalizedName() + "." + block.getWoodType(itemStack.getItemDamage()) + "Wood";
	}
}
