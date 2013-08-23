package net.minecraft.src.betterore.common.config.parser.validation;

import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;

public class BOValidatorChoice extends BOValidatorNode
{
    public String value = null;
    public String displayValue = null;
    public String description = null;

    protected BOValidatorChoice(BOValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.value = this.validateRequiredAttribute(String.class, "Value", true);
        this.displayValue = this.validateNamedAttribute(String.class, "DisplayValue", this.displayValue, true);
        this.description = this.validateNamedAttribute(String.class, "Description", null, true);
        return true;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorChoice>
    {
        public BOValidatorChoice createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorChoice(parent, node);
        }
    }

}
