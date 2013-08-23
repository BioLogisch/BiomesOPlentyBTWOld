package net.minecraft.src.betterore.common.config.parser.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.generator.IBOOreDistribution;
import net.minecraft.src.betterore.common.generator.IBOOreDistribution.IDistributionFactory;
import net.minecraft.src.betterore.common.generator.tools.BOPDist;
import net.minecraft.src.betterore.common.util.BOBiomeDescriptor;
import net.minecraft.src.betterore.common.util.BOBlockDescriptor;

public class BOValidatorDistribution extends BOValidatorNode
{
    private final IDistributionFactory _distributionFactory;
    public IBOOreDistribution distribution = null;

    protected BOValidatorDistribution(BOValidatorNode parent, Node node, IDistributionFactory distributionFactory)
    {
        super(parent, node);
        this._distributionFactory = distributionFactory;
    }

    protected boolean validateChildren() throws BOParserException
    {
        try
        {
            this.distribution = this._distributionFactory.createDistribution(this.getParser().target.getOreDistributions().size());
        }
        catch (Exception var7)
        {
            throw new BOParserException("Failed to create distribution using \'" + this._distributionFactory + "\'.", this.getNode(), var7);
        }

        this.getParser().target.getOreDistributions().add(this.distribution);
        this.getNode().setUserData("value", this.distribution, (UserDataHandler)null);
        super.validateChildren();
        String inherits = this.validateNamedAttribute(String.class, "Inherits", null, true);

        if (inherits != null)
        {
            Collection settings = this.getParser().target.getOreDistributions(inherits);

            if (settings.isEmpty())
            {
                throw new BOParserException("Cannot inherit settings (\'" + inherits + "\' is not a loaded distribution).", this.getNode());
            }

            if (settings.size() > 1)
            {
                throw new BOParserException("Cannot inherit settings (\'" + inherits + "\' is ambiguous; matching " + settings.size() + " loaded distributions).", this.getNode());
            }

            try
            {
                this.distribution.inheritFrom((IBOOreDistribution)settings.iterator().next());
            }
            catch (IllegalArgumentException var6)
            {
                throw new BOParserException("Cannot inherit settings (" + var6.getMessage() + ").", this.getNode(), var6);
            }
        }

        HashSet settings1 = new HashSet(this.distribution.getDistributionSettings().keySet());
        String nameKey = IBOOreDistribution.StandardSettings.Name.name();

        if (settings1.contains(nameKey))
        {
            String newName = this.validateNamedAttribute(String.class, nameKey, null, true);

            try
            {
                if (newName != null)
                {
                    this.distribution.setDistributionSetting(nameKey, newName);
                }
            }
            catch (IllegalAccessException var8)
            {
                throw new BOParserException("Attribute \'" + nameKey + "\' is not configurable.", this.getNode(), var8);
            }
            catch (IllegalArgumentException var9)
            {
                throw new BOParserException("Attribute \'" + nameKey + "\' cannot be set (" + var9.getMessage() + ").", this.getNode(), var9);
            }

            settings1.remove(nameKey);
        }

        this.validateDistributionSettings(settings1);
        return true;
    }

    public void validateDistributionSettings(Set<String> settings) throws BOParserException
    {
        String parentKey = IBOOreDistribution.StandardSettings.Parent.name();

        if (settings.contains(parentKey))
        {
            for (Node oreBlockKey = this.getNode().getParentNode(); oreBlockKey != null; oreBlockKey = oreBlockKey.getParentNode())
            {
                Object replacesKey = oreBlockKey.getUserData("value");

                if (replacesKey != null && replacesKey instanceof IBOOreDistribution)
                {
                    try
                    {
                        this.distribution.setDistributionSetting(parentKey, replacesKey);
                    }
                    catch (IllegalAccessException var18)
                    {
                        throw new BOParserException("Parent distribution is not configurable.", this.getNode(), var18);
                    }
                    catch (IllegalArgumentException var19)
                    {
                        throw new BOParserException("Invalid parent distribution.", this.getNode(), var19);
                    }

                    this.getNode().setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
                    break;
                }
            }

            settings.remove(parentKey);
        }

        String oreBlockKey1 = IBOOreDistribution.StandardSettings.OreBlock.name();
        String biomeKey;

        if (settings.contains(oreBlockKey1))
        {
            BOBlockDescriptor replacesKey1 = new BOBlockDescriptor();
            biomeKey = this.validateNamedAttribute(String.class, "Block", null, true);

            if (biomeKey != null)
            {
                replacesKey1.add(biomeKey);
            }

            for (BOValidatorBlockDescriptor settingName : validateNamedChildren(2, "OreBlock", new BOValidatorBlockDescriptor.Factory())) {
                replacesKey1.add(settingName.blocks, settingName.weight);	
            }
            
            if (!replacesKey1.getDescriptors().isEmpty())
            {
                try
                {
                    this.distribution.setDistributionSetting(oreBlockKey1, replacesKey1);
                }
                catch (IllegalAccessException var16)
                {
                    throw new BOParserException("Target ore blocks are not configurable.", this.getNode(), var16);
                }
                catch (IllegalArgumentException var17)
                {
                    throw new BOParserException("Target ore blocks are not supported by this distribution.", this.getNode(), var17);
                }
            }

            settings.remove(oreBlockKey1);
        }

        String replacesKey2 = IBOOreDistribution.StandardSettings.ReplaceableBlock.name();

        if (settings.contains(replacesKey2))
        {
            BOBlockDescriptor biomeKey1 = new BOBlockDescriptor();
            for (BOValidatorBlockDescriptor settingName : validateNamedChildren(2, "Replaces", new BOValidatorBlockDescriptor.Factory())) {
            	biomeKey1.add(settingName.blocks, settingName.weight);
            }
            
            if (!biomeKey1.getDescriptors().isEmpty())
            {
                try
                {
                    this.distribution.setDistributionSetting(replacesKey2, biomeKey1);
                }
                catch (IllegalAccessException var14)
                {
                    throw new BOParserException("Replaceable blocks are not configurable.", this.getNode(), var14);
                }
                catch (IllegalArgumentException var15)
                {
                    throw new BOParserException("Replaceable blocks are not supported by this distribution.", this.getNode(), var15);
                }
            }

            settings.remove(replacesKey2);
        }

        biomeKey = IBOOreDistribution.StandardSettings.TargetBiome.name();

        if (settings.contains(biomeKey))
        {
            BOBiomeDescriptor biomeDescriptor = new BOBiomeDescriptor();
            
            for (BOValidatorBiomeDescriptor biome : validateNamedChildren(2, "Biome", new BOValidatorBiomeDescriptor.Factory())) {
            	biomeDescriptor.add(biome.biome, biome.weight, biome.climate, false);
            }

            for (BOValidatorBiomeDescriptor biomeType : validateNamedChildren(2, "BiomeType", new BOValidatorBiomeDescriptor.Factory())) {
            	biomeDescriptor.add(biomeType.biome, biomeType.weight, biomeType.climate, true);
            }
            
            for (BOValidatorBiomeSet biomeSet : validateNamedChildren(2, "BiomeSet", new BOValidatorBiomeSet.Factory())) {
            	biomeDescriptor.addAll(biomeSet.biomeSet, 1.0F);
            }
            
            if (!biomeDescriptor.getDescriptors().isEmpty())
            {
                try
                {
                    this.distribution.setDistributionSetting(biomeKey, biomeDescriptor);
                }
                catch (IllegalAccessException e)
                {
                    throw new BOParserException("Biomes are not configurable.", this.getNode(), e);
                }
                catch (IllegalArgumentException e)
                {
                    throw new BOParserException("Biomes are not supported by this distribution.", this.getNode(), e);
                }
            }

            settings.remove(biomeKey);
        }

        validateNamedChildren(2, "Setting", new BOValidatorPDist.Factory(this.distribution));
        
        for (String settingName3 : settings) {
        	Object value1 = this.distribution.getDistributionSetting(settingName3);

            if (value1 != null)
            {
                if (value1 instanceof BOPDist)
                {
                    continue;
                }

                value1 = this.validateNamedAttribute((Class<Object>)value1.getClass(), settingName3, value1, true);
            }
            else
            {
                value1 = this.validateNamedAttribute(String.class, settingName3, null, true);
            }

            try
            {
                if (value1 != null)
                {
                    this.distribution.setDistributionSetting(settingName3, value1);
                }
            }
            catch (IllegalAccessException var10)
            {
                throw new BOParserException("Attribute \'" + settingName3 + "\' is not configurable.", this.getNode(), var10);
            }
            catch (IllegalArgumentException var11)
            {
                throw new BOParserException("Attribute \'" + settingName3 + "\' cannot be set (" + var11.getMessage() + ").", this.getNode(), var11);
            }
        }
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorDistribution>
    {
        private final IDistributionFactory _distributionFactory;

        public Factory(IDistributionFactory distributionFactory)
        {
            this._distributionFactory = distributionFactory;
        }

        public BOValidatorDistribution createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorDistribution(parent, node, this._distributionFactory);
        }
    }

}
