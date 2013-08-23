package net.minecraft.src.biomesoplenty.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockSapling;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.biomesoplenty.BiomesOPlenty;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenAutumn;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenCherry1;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenCherry2;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenMystic2;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenOminous1;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenOminous2;
import net.minecraft.src.biomesoplenty.worldgen.WorldGenOriginTree;

public class BlockBOPSapling extends BlockSapling
{
	private static final String[] saplings = new String[] {"apple", "yellowautumn", "bamboo", "magic", "dark", "dead", "fir", "holy", "orangeautumn", "origin", "pinkcherry", "maple", "whitecherry", "hellbark", "jacaranda"};
	private Icon[] textures;
	private static final int TYPES = 15;

	public BlockBOPSapling(int par1)
	{
		super(par1);
		setHardness(0.0F);
		setStepSound(Block.soundGrassFootstep);
		this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		textures = new Icon[saplings.length];

		for (int i = 0; i < saplings.length; ++i) {
			textures[i] = iconRegister.registerIcon("biomesoplenty:sapling_" + saplings[i]);
		}

	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		if (meta < 0 || meta >= saplings.length) {
			meta = 0;
		}

		return textures[meta];
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < saplings.length; ++i) {
			list.add(new ItemStack(blockID, 1, i));
		}
	}

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		this.growTree(world, x, y, z, world.rand);
        return false;
    }

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (world.isRemote)
			return;

		this.checkFlowerChange(world, x, y, z);

		if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0) {
			this.growTree(world, x, y, z, random);
		}
	}

	@Override
	public void growTree(World world, int x, int y, int z, Random random)
	{
		int meta = world.getBlockMetadata(x, y, z) & TYPES;
		Object obj = null;
		int rnd = random.nextInt(8);

		if (obj == null)
		{
			switch (meta)
			{
			/*case 0: // Apple Tree
			obj = new WorldGenApple(false);
			break;*/

			case 1: // Autumn Tree
				obj = new WorldGenAutumn(false); //DONE
				break;

			/*case 2: // Bamboo Tree
				rnd = random.nextInt(8);

				if (rnd == 0) {
					obj = new WorldGenBambooTree(false);
				} else {
					obj = new WorldGenBambooTree2(false);
				}
				break;*/

			case 3: // Magic Tree
				obj = new WorldGenMystic2(false); //DONE
				break;

			case 4: // Dark Tree
				rnd = random.nextInt(8);

				if (rnd == 0) {
					obj = new WorldGenOminous2(); //DONE
				} else {
					obj = new WorldGenOminous1(false); //DONE
				}
				break;

			/*case 5: // Dead Tree
				obj = new WorldGenDeadTree2(false);
				break;

			case 6: // Fir Tree
				obj = new WorldGenTaiga9(false);
				break;

			case 7: // Holy Tree
				obj = new WorldGenPromisedTree(false);
				break;

			case 8: // Autumn Tree
				obj = new WorldGenAutumn2(false);
				break;*/

			case 9: // Origin Tree
				obj = new WorldGenOriginTree(false);
				break;

			case 10: // Pink Cherry Tree
				obj = new WorldGenCherry1(false); //DONE
				break;

			/*case 11: // Maple Tree
				obj = new WorldGenMaple(false);
				break;*/

			case 12: // White Cherry Tree
				obj = new WorldGenCherry2(false); //DONE
				break;

			/*case 13: // Hellbark
				obj = new WorldGenNetherBush();
				break;

			case 14: // Jacaranda
				obj = new WorldGenJacaranda(false);
				break;*/
			}
		}

		if (obj != null)
		{
			world.setBlockToAir(x, y, z);

			if (!((WorldGenerator)obj).generate(world, random, x, y, z)) {
				world.setBlock(x, y, z, blockID, meta, 2);
			}
		}
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta & TYPES;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) & TYPES;
	}
}
