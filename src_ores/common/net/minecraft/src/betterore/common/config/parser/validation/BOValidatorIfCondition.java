package net.minecraft.src.betterore.common.config.parser.validation;

import org.w3c.dom.Node;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.config.parser.validation.BOValidatorNode.IValidatorFactory;

public class BOValidatorIfCondition extends BOValidatorCondition
{
    protected BOValidatorIfCondition(BOValidatorNode parent, Node node)
    {
        super(parent, node, false);
    }

    protected boolean evaluateCondition() throws BOParserException
    {
        return ((Boolean)this.validateRequiredAttribute(Boolean.class, "Condition", true)).booleanValue();
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorIfCondition>
    {
        public BOValidatorIfCondition createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorIfCondition(parent, node);
        }
    }

}
