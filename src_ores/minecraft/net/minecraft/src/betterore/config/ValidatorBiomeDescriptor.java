package net.minecraft.src.betterore.config;

import org.w3c.dom.Node;

import net.minecraft.src.betterore.util.BiomeDescriptor.Climate;

public class ValidatorBiomeDescriptor extends ValidatorNode
{
    public String biome = null;
    public float weight = 1.0F;
    public Climate climate = new Climate(); 

    protected ValidatorBiomeDescriptor(ValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws ParserException
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
    
    public static class Factory implements IValidatorFactory<ValidatorBiomeDescriptor>
    {
        public ValidatorBiomeDescriptor createValidator(ValidatorNode parent, Node node)
        {
            return new ValidatorBiomeDescriptor(parent, node);
        }
    }

}
