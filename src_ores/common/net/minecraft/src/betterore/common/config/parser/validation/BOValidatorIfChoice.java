package net.minecraft.src.betterore.common.config.parser.validation;

import net.minecraft.src.betterore.common.config.BOChoiceOption;
import net.minecraft.src.betterore.common.config.BOConfigOption;
import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;

public class BOValidatorIfChoice extends BOValidatorCondition
{
    protected BOValidatorIfChoice(BOValidatorNode parent, Node node, boolean invert)
    {
        super(parent, node, invert);
    }

    protected boolean evaluateCondition() throws BOParserException
    {
        String optionName = this.validateRequiredAttribute(String.class, "name", true);
        String strValue = this.validateNamedAttribute(String.class, "value", null, true);
        BOConfigOption option = this.getParser().target.getConfigOption(optionName);
        boolean isOptionValid = option != null && option instanceof BOChoiceOption;

        if (strValue == null)
        {
            return isOptionValid;
        }
        else if (isOptionValid)
        {
            return strValue.equalsIgnoreCase((String)option.getValue());
        }
        else
        {
            throw new BOParserException("Option \'" + optionName + "\' is not a recognized Choice option.", this.getNode());
        }
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorIfChoice>
    {
        private final boolean _invert;

        public Factory(boolean invert)
        {
            this._invert = invert;
        }

        public BOValidatorIfChoice createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorIfChoice(parent, node, this._invert);
        }
    }

}
