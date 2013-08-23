package net.minecraft.src.betterore.common.generator;

import java.util.Random;

import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.betterore.common.config.BOWorldConfig;


public class BOWorldGenEmpty extends WorldGenerator
{
    public static final boolean cacheState = false;
    private final WorldGenerator delegateGenerator;
    private World _lastWorld = null;
    private boolean _lastEnabled = false;

    public BOWorldGenEmpty(WorldGenerator delegate)
    {
        this.delegateGenerator = delegate;
    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        if (world == this._lastWorld)
        {
            ;
        }

        this._lastEnabled = BOWorldConfig.getWorldConfig(world).vanillaOreGen;
        this._lastWorld = world;
        return this._lastEnabled ? this.delegateGenerator.generate(world, rand, x, y, z) : false;
    }
}
