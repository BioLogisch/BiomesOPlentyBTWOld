package net.minecraft.src.betterore.common.config.parser.validation;

import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;

public class BOValidatorBlockDescriptor extends BOValidatorNode
{
    public String blocks = null;
    public float weight = 1.0F;

    protected BOValidatorBlockDescriptor(BOValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.blocks = (String)this.validateRequiredAttribute(String.class, "Block", true);
        this.weight = ((Float)this.validateNamedAttribute(Float.class, "Weight", Float.valueOf(this.weight), true)).floatValue();
        return true;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorBlockDescriptor>
    {
        public BOValidatorBlockDescriptor createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorBlockDescriptor(parent, node);
        }
    }

}
