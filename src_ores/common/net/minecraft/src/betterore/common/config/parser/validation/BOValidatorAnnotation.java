package net.minecraft.src.betterore.common.config.parser.validation;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.config.parser.BOExpressionEvaluator;
import net.minecraft.src.betterore.common.config.parser.validation.BOValidatorNode.IValidatorFactory;

public class BOValidatorAnnotation extends BOValidatorSimpleNode
{
    protected BOValidatorAnnotation(BOValidatorNode parent, Node node)
    {
        super(parent, node, String.class, (BOExpressionEvaluator)null);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.getNode().setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
        return false;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorAnnotation>
    {
        public BOValidatorAnnotation createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorAnnotation(parent, node);
        }
    }

}
