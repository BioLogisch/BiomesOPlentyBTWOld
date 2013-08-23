package net.minecraft.src.betterore.common.config.parser.validation;

import net.minecraft.src.betterore.common.config.BOConfigOption;
import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class BOValidatorRefOption extends BOValidatorNode
{
    public BOValidatorRefOption(BOValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        String optionName = (String)this.validateRequiredAttribute(String.class, "name", true);
        BOConfigOption option = this.getParser().target.getConfigOption(optionName);

        if (option == null)
        {
            throw new BOParserException("Option \'" + optionName + "\' is not a recognized option.", this.getNode());
        }
        else
        {
            this.getNode().setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
            this.checkChildrenValid();
            Object value = option.getValue();
            this.replaceWithNode(new Node[] {value == null ? null : this.getNode().getOwnerDocument().createTextNode(value.toString())});
            return false;
        }
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorRefOption>
    {
        public BOValidatorRefOption createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorRefOption(parent, node);
        }
    }

}
