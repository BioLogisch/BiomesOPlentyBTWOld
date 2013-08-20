package net.minecraft.src.betterore.config;

import org.w3c.dom.Node;

import net.minecraft.src.betterore.config.ValidatorNode.IValidatorFactory;

public class ValidatorIfCondition extends ValidatorCondition
{
    protected ValidatorIfCondition(ValidatorNode parent, Node node)
    {
        super(parent, node, false);
    }

    protected boolean evaluateCondition() throws ParserException
    {
        return ((Boolean)this.validateRequiredAttribute(Boolean.class, "Condition", true)).booleanValue();
    }
    
    public static class Factory implements IValidatorFactory<ValidatorIfCondition>
    {
        public ValidatorIfCondition createValidator(ValidatorNode parent, Node node)
        {
            return new ValidatorIfCondition(parent, node);
        }
    }

}
