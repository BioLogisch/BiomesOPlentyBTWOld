package net.minecraft.src.biomesoplenty.worldgen;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenPalmTree3 extends WorldGenerator
{
	@Override
	public boolean generate(World var1, Random var2, int var3, int var4, int var5)
	{
		while (var1.isAirBlock(var3, var4, var5) && var4 > 2)
		{
			--var4;
		}

		int var6 = var1.getBlockId(var3, var4, var5);

		if (var6 != Block.grass.blockID)
			return false;
		else
		{
			for (int var7 = -2; var7 <= 2; ++var7)
			{
				for (int var8 = -2; var8 <= 2; ++var8)
				{
					if (var1.isAirBlock(var3 + var7, var4 - 1, var5 + var8) && var1.isAirBlock(var3 + var7, var4 - 2, var5 + var8) && !var1.isAirBlock(var3 + var7, var4, var5 + var8))
						return false;
				}
			}

			int var99 = var2.nextInt(4);

			if (var99 == 0)
			{
				var1.setBlock(var3, var4, var5, Block.dirt.blockID);
				var1.setBlockAndMetadata(var3, var4 + 1, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 2, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 3, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 4, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 5, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 6, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 7, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5, Block.wood.blockID, 3);

				var1.setBlockAndMetadata(var3, var4 + 7, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 4, var4 + 7, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 7, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 7, var5 + 2, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 + 1, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 3, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5 + 1, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3, var4 + 8, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 4, var4 + 8, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 4, var4 + 8, var5 + 2, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 + 1, var4 + 9, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 3, var4 + 9, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 9, var5 + 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 3, var4 + 9, var5 + 1, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 + 2, var4 + 9, var5, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3, var4 + 10, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 4, var4 + 10, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 10, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 10, var5 + 2, Block.leaves.blockID, 3);
			}

			if (var99 == 1)
			{
				var1.setBlock(var3, var4, var5, Block.dirt.blockID);
				var1.setBlockAndMetadata(var3, var4 + 1, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 2, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 3, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 4, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 5, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 6, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 7, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5, Block.wood.blockID, 3);

				var1.setBlockAndMetadata(var3 - 4, var4 + 7, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 7, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 7, var5 + 2, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 3, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5 + 1, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 4, var4 + 8, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 4, var4 + 8, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 + 2, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 3, var4 + 9, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 9, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 3, var4 + 9, var5 + 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 9, var5 + 1, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 9, var5, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 4, var4 + 10, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 10, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 10, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 10, var5 + 2, Block.leaves.blockID, 3);
			}

			if (var99 == 2)
			{
				var1.setBlock(var3, var4, var5, Block.dirt.blockID);
				var1.setBlockAndMetadata(var3, var4 + 1, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 2, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 3, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 4, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 5, var5 + 1, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 6, var5 + 1, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5 + 2, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 + 2, Block.wood.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 7, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 7, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5 + 4, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 1, var4 + 8, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 8, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 + 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 + 3, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5 + 4, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5 + 4, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 1, var4 + 9, var5 + 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 9, var5 + 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 9, var5 + 3, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 9, var5 + 3, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3, var4 + 9, var5 + 2, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 10, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 10, var5 + 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 10, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 10, var5 + 4, Block.leaves.blockID, 3);
			}

			if (var99 == 3)
			{
				var1.setBlock(var3, var4, var5, Block.dirt.blockID);
				var1.setBlockAndMetadata(var3, var4 + 1, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 2, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 3, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 4, var5, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 5, var5 - 1, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 6, var5 - 1, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5 - 2, Block.wood.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 - 2, Block.wood.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 7, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 7, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5 - 4, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 7, var5, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 1, var4 + 8, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 8, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 - 3, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 8, var5 - 1, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5 - 4, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5 - 4, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 2, var4 + 8, var5, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 8, var5, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 1, var4 + 9, var5 - 3, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 9, var5 - 3, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 - 1, var4 + 9, var5 - 1, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 1, var4 + 9, var5 - 1, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3, var4 + 9, var5 - 2, Block.leaves.blockID, 3);

				var1.setBlockAndMetadata(var3 - 2, var4 + 10, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3 + 2, var4 + 10, var5 - 2, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 10, var5 - 4, Block.leaves.blockID, 3);
				var1.setBlockAndMetadata(var3, var4 + 10, var5, Block.leaves.blockID, 3);
			}

			return true;
		}
	}
}
