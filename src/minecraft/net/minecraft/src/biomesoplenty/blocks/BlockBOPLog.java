package net.minecraft.src.biomesoplenty.blocks;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Explosion;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FCIBlock;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;
import net.minecraft.src.biomesoplenty.api.BOPAPIBlocks;
import net.minecraft.src.biomesoplenty.api.BOPAPIItems;

public class BlockBOPLog extends Block implements FCIBlock
{
	public static enum LogCategory
	{
		CAT1, CAT2, CAT3, CAT4;
	}

	private static final String[] types = new String[] {"acacia", "cherry", "dark", "fir", "holy", "magic", "mangrove", "palm", "redwood", "willow", "dead", "bigflowerstem", "pine", "hellbark", "jacaranda"};
	private Icon[] textures;
	private Icon[] logHearts;

	private final LogCategory category;

	public BlockBOPLog(int blockID, LogCategory cat)
	{
		super(blockID, FCBetterThanWolves.fcMaterialLog);
		category = cat;
		setHardness(1.25F);
		setStepSound(Block.soundWoodFootstep);
		this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
	}
	
	@Override
    public void OnBlockDestroyedWithImproperTool(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6)
    {
		if (getPlankMetaFromLogMeta(var6) != -2)
		{
			if (getPlankMetaFromLogMeta(var6) != -1)
			{
				this.dropBlockAsItem_do(var1, var3, var4, var5, new ItemStack(BOPAPIBlocks.planks, 1, getPlankMetaFromLogMeta(var6)));
				this.dropBlockAsItem_do(var1, var3, var4, var5, new ItemStack(BOPAPIItems.bark, 1, getPlankMetaFromLogMeta(var6)));
			}

			for (int var7 = 0; var7 < 3; ++var7)
			{
				ItemStack var8 = new ItemStack(FCBetterThanWolves.fcSawDust);
				this.dropBlockAsItem_do(var1, var3, var4, var5, var8);
			}
		}
    }
	
	@Override
    public boolean canDropFromExplosion(Explosion var1)
    {
        return false;
    }

	@Override
    public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4, Explosion var5)
    {
        float var6 = 1.0F;

        if (var5 != null)
        {
            var6 = 1.0F / var5.explosionSize;
        }

        for (int var7 = 0; var7 < 4; ++var7)
        {
            if (var1.rand.nextFloat() <= var6)
            {
                ItemStack var8 = new ItemStack(FCBetterThanWolves.fcSawDust);
                this.dropBlockAsItem_do(var1, var2, var3, var4, var8);
            }
        }
    }

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		textures = new Icon[types.length];
		logHearts = new Icon[types.length];

		for (int i = 0; i < types.length; ++i)
		{
			if (i != 11)
			{
				textures[i] = iconRegister.registerIcon("biomesoplenty:log_"+types[i]+"_side");
				logHearts[i] = iconRegister.registerIcon("biomesoplenty:log_"+types[i]+"_heart");
			}
		}

		textures[11] = iconRegister.registerIcon("biomesoplenty:bigflowerstem_side");
		logHearts[11] = iconRegister.registerIcon("biomesoplenty:bigflowerstem_heart");
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		int pos = meta & 12;
		if (pos == 0 && (side == 1 || side == 0) || pos == 4 && (side == 5 || side == 4) || pos == 8 && (side == 2 || side == 3))
			return logHearts[(getTypeFromMeta(meta) + category.ordinal() * 4)];
		else
			return textures[(getTypeFromMeta(meta) + category.ordinal() * 4)];
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
		if (category != LogCategory.CAT4)
		{
			for (int i = 0; i < 4; ++i) {
				list.add(new ItemStack(this, 1, i));
			}
		}
		else
		{
			for (int i = 0; i < 3; ++i) {
				list.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		byte radius = 4;
		int bounds = radius + 1;

		if (world.checkChunksExist(x - bounds, y - bounds, z - bounds, x + bounds, y + bounds, z + bounds)) {
			for (int i = -radius; i <= radius; ++i) {
				for (int j = -radius; j <= radius; ++j) {
					for (int k = -radius; k <= radius; ++k)
					{
						int blockID = world.getBlockId(x + i, y + j, z + k);

						if (Block.blocksList[blockID] != null) {
							Block.blocksList[blockID].beginLeavesDecay(world, x + i, y + j, z + k);
						}
					}
				}
			}
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
	{
		int type = getTypeFromMeta(meta);
		byte orientation = 0;

		switch (side)
		{
		case 0:
		case 1:
			orientation = 0;
			break;

		case 2:
		case 3:
			orientation = 8;
			break;

		case 4:
		case 5:
			orientation = 4;
		}

		return type | orientation;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata)
	{
		if (category == LogCategory.CAT4 && metadata == 1)
			return 0;
		else
		{
			super.setBurnProperties(blockID, 5, 5);
			return blockFlammability[blockID];
		}
	}

	@Override
	public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata)
	{
		if (category == LogCategory.CAT4 && metadata == 1)
			return 0;
		else
			return blockFireSpreadSpeed[blockID];
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata)
	{
		if (category == LogCategory.CAT4 && metadata == 1)
			return false;
		else
			return getFlammability(world, x, y, z, metadata) > 0;
	}

	@Override
	public int damageDropped(int meta)
	{
		return getTypeFromMeta(meta);
	}

	@Override
	protected ItemStack createStackedBlock(int meta)
	{
		return new ItemStack(blockID, 1, getTypeFromMeta(meta));
	}

	@Override
	public int getRenderType()
	{
		return 31;
	}

	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean isWood(World world, int x, int y, int z)
	{
		return true;
	}
	
	public int getPlankMetaFromLogMeta(int meta)
	{
		int typeIndex = getTypeFromMeta(meta) + category.ordinal() * 4;
		
		if (typeIndex >= 12) return typeIndex -= 1;
		if (typeIndex == 10) return -1;
		if (typeIndex == 11) return -2;

		return typeIndex;
	}

	public String getWoodType(int meta)
	{
		return types[getTypeFromMeta(meta) + category.ordinal() * 4];
	}

	private static int getTypeFromMeta(int meta)
	{
		return meta & 3;
	}

	@Override
	public int GetFacing(IBlockAccess block, int x, int y, int z) 
	{
		return block.getBlockMetadata(x, y, z) & 12;
	}

	@Override
	public void SetFacing(World world, int x, int y, int z, int orientation) 
	{
	}

	@Override
	public int GetFacingFromMetadata(int meta) 
	{
		return 0;
	}

	@Override
	public int SetFacingInMetadata(int var1, int var2) 
	{
		return 0;
	}

	@Override
	public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4) 
	{
		return true;
	}

	@Override
	public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4) 
	{
		return true;
	}

	@Override
	public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4) 
	{
		return true;
	}

	@Override
    public void RotateAroundJAxis(World world, int x, int y, int z, boolean var5)
    {
        int meta = world.getBlockMetadata(x, y, z);
        int var6 = meta & 12;

        if (var6 != 0)
        {
            if (var6 == 4)
            {
                var6 = 8;
            }
            else if (var6 == 8)
            {
                var6 = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, meta & -13 | var6);
        }

        world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
    }

	@Override
    public int RotateMetadataAroundJAxis(int meta, boolean redstonepowered)
    {
		return 0;
    }

	@Override
	public boolean ToggleFacing(World world, int x, int y, int z, boolean redstonepowered) 
	{
		return false;
	}
}
