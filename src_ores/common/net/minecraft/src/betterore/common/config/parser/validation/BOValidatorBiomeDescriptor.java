package net.minecraft.src.betterore.common.config.parser.validation;

import org.w3c.dom.Node;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.util.BOBiomeDescriptor.Climate;

public class BOValidatorBiomeDescriptor extends BOValidatorNode
{
    public String biome = null;
    public float weight = 1.0F;
    public Climate climate = new Climate(); 

    protected BOValidatorBiomeDescriptor(BOValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.biome = this.validateRequiredAttribute(String.class, "Name", true);
        this.weight = this.validateNamedAttribute(Float.class, "Weight", this.weight, true);
        float minTemperature = this.validateNamedAttribute(Float.class, "MinTemperature", climate.minTemperature, true);
        float maxTemperature = this.validateNamedAttribute(Float.class, "MaxTemperature", climate.maxTemperature, true);
        float minRainfall = this.validateNamedAttribute(Float.class, "MinRainfall", climate.minRainfall, true);
        float maxRainfall = this.validateNamedAttribute(Float.class, "MaxRainfall", climate.maxRainfall, true);
        this.climate = new Climate(minTemperature, maxTemperature, minRainfall, maxRainfall);
        return true;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorBiomeDescriptor>
    {
        public BOValidatorBiomeDescriptor createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorBiomeDescriptor(parent, node);
        }
    }

}
