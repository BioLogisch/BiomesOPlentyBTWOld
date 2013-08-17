package net.minecraft.src.biomesoplenty.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockLeavesBase;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;
import net.minecraft.src.biomesoplenty.utils.IShearable;

public class BlockBOPLeaves extends BlockLeavesBase implements IShearable
{
        public static enum LeafCategory
        {
        	CAT1, CAT2;
        }

        //Autumn - Orange = Leaves 1, Origin - White = Leaves 2
        private static final String[] leaves = new String[] {"yellowautumn", "bamboo", "magic", "dark", "dead", "fir", "holy", "orangeautumn", "origin", "pinkcherry", "maple", "whitecherry", "hellbark", "jacaranda"};

        private static final float[] fallingLeavesChance = new float[] {0.1F, 0.008F, 0.016F, 0.008F, 0.0F, 0.008F, 0.016F, 0.1F, 0.008F, 0.1F, 0.008F, 0.1F, 0.008F, 0.008F};

        private Icon[][] textures;
        private final LeafCategory category;
        int[] adjacentTreeBlocks;

        public BlockBOPLeaves(int blockID, LeafCategory cat)
        {
        	super(blockID, Material.leaves, false);
        	category = cat;
        	this.setTickRandomly(true);
        	setHardness(0.2F);
        	setLightOpacity(1);
        	setStepSound(Block.soundGrassFootstep);
        	this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
        }
        
        public float GetMovementModifier(World var1, int var2, int var3, int var4)
        {
            return 0.5F;
        }

        public boolean CanSnowOnBlock(World var1, int var2, int var3, int var4)
        {
            return true;
        }
        
        public Icon getIconBetterLeaves(int metadata, float randomIndex)
        {
                return textures[2][getTypeFromMeta(metadata) + (category.ordinal() * 8)];
        }

        public Icon getIconFallingLeaves(int metadata)
        {
            return textures[1][getTypeFromMeta(metadata) + (category.ordinal() * 8)];
        }

        public float getSpawnChanceFallingLeaves(int metadata)
        {
            return fallingLeavesChance[getTypeFromMeta(metadata) + (category.ordinal() * 8)];
        }

        @Override
        public boolean isOpaqueCube()
        {
                return Block.leaves.isOpaqueCube();
        }

        @Override
        public void breakBlock(World world, int x, int y, int z, int par5, int par6)
        {
                byte radius = 1;
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
        public void updateTick(World world, int x, int y, int z, Random random)
        {
                if (world.isRemote)
                        return;

                int meta = world.getBlockMetadata(x, y, z);

                if ((meta & 8) != 0/* && (meta & 4) == 0*/)
                {
                        byte b0 = 4;
                        int i1 = b0 + 1;
                        byte b1 = 32;
                        int j1 = b1 * b1;
                        int k1 = b1 / 2;

                        if (adjacentTreeBlocks == null)
                        {
                                adjacentTreeBlocks = new int[b1 * b1 * b1];
                        }

                        int l1;

                        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1))
                        {
                                int i2;
                                int j2;
                                int k2;

                                for (l1 = -b0; l1 <= b0; ++l1)
                                {
                                        for (i2 = -b0; i2 <= b0; ++i2)
                                        {
                                                for (j2 = -b0; j2 <= b0; ++j2)
                                                {
                                                        k2 = world.getBlockId(x + l1, y + i2, z + j2);

                                                        Block block = Block.blocksList[k2];

                                                        if (block != null && block.canSustainLeaves(world, x + l1, y + i2, z + j2))
                                                        {
                                                                adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                                                        }
                                                        else if (block != null && block.isLeaves(world, x + l1, y + i2, z + j2))
                                                        {
                                                                adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                                        }
                                                        else
                                                        {
                                                                adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                                        }
                                                }
                                        }
                                }

                                for (l1 = 1; l1 <= 4; ++l1)
                                {
                                        for (i2 = -b0; i2 <= b0; ++i2)
                                        {
                                                for (j2 = -b0; j2 <= b0; ++j2)
                                                {
                                                        for (k2 = -b0; k2 <= b0; ++k2)
                                                        {
                                                                if (adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1)
                                                                {
                                                                        if (adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2)
                                                                        {
                                                                                adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                                                        }

                                                                        if (adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2)
                                                                        {
                                                                                adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                                                        }

                                                                        if (adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2)
                                                                        {
                                                                                adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
                                                                        }

                                                                        if (adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2)
                                                                        {
                                                                                adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
                                                                        }

                                                                        if (adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2)
                                                                        {
                                                                                adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;
                                                                        }

                                                                        if (adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2)
                                                                        {
                                                                                adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }

                        l1 = adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];

                        if (l1 >= 0)
                        {
                                world.setBlockMetadata(x, y, z, meta & -9, 4);
                        }
                        else
                        {
                                this.removeLeaves(world, x, y, z);
                        }
                }
        }

        private void removeLeaves(World world, int x, int y, int z)
        {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
        }

        @Override
        public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata)
        {
                if (category == LeafCategory.CAT2 && metadata == 4)
                        return 0;
                else
                {
                        super.setBurnProperties(blockID, 30, 60);
                        return blockFlammability[blockID];
                }
        }

        @Override
        public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata)
        {
                if (category == LeafCategory.CAT2 && metadata == 4)
                        return 0;
                else
                        return blockFireSpreadSpeed[blockID];
        }

        @Override
        public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata)
        {
                if (category == LeafCategory.CAT2 && metadata == 4)
                        return false;
                else
                        return getFlammability(world, x, y, z, metadata) > 0;
        }


       /* @Override
        public int idDropped(int par1, Random par2Random, int par3)
        {
                return Blocks.saplings.get().blockID;
        }*/

        @Override
        public int damageDropped(int meta)
        {
                return (getTypeFromMeta(meta) + category.ordinal() * 8) + 1;
        }

        @Override
        public int getDamageValue(World par1World, int par2, int par3, int par4)
        {
                return getTypeFromMeta(par1World.getBlockMetadata(par2, par3, par4));
        }

        @Override
        public int quantityDropped(Random random)
        {
                return random.nextInt(20) == 0 ? 1 : 0;
        }

        @Override
        public boolean isShearable(ItemStack item, World world, int x, int y, int z)
        {
                return true;
        }

        @Override
        public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune)
        {
                ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
                ret.add(new ItemStack(this, 1, getTypeFromMeta(world.getBlockMetadata(x, y, z))));
                return ret;
        }

        public String getLeafType(int meta)
        {
                return leaves[getTypeFromMeta(meta) + category.ordinal() * 8];
        }

        private static int getTypeFromMeta(int meta)
        {
                meta = meta & 7;
                if (meta < 0 || meta >= leaves.length) {
                        meta = 0;
                }
                return meta;
        }

        //Client only
        public void setGraphicsLevel(boolean par1)
        {
                graphicsLevel = par1;
        }

        @Override
        public void beginLeavesDecay(World world, int x, int y, int z)
        {
                world.setBlockMetadata(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
        }

        @Override
        public boolean isLeaves(World world, int x, int y, int z)
        {
                return true;
        }
}