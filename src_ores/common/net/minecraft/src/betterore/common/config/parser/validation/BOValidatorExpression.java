package net.minecraft.src.betterore.common.config.parser.validation;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.config.parser.BOExpressionEvaluator;
import net.minecraft.src.betterore.common.config.parser.BOExpressionEvaluator.EvaluatorException;

public class BOValidatorExpression extends BOValidatorSimpleNode
{
    protected BOValidatorExpression(BOValidatorNode parent, Node node, BOExpressionEvaluator evaluator)
    {
        super(parent, node, String.class, evaluator);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.getNode().setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
        this.checkChildrenValid();
        Object value = null;

        try
        {
            value = this.evaluator.evaluate((String)this.content);
        }
        catch (EvaluatorException var3)
        {
            throw new BOParserException(var3.getMessage(), this.getNode(), var3);
        }

        this.replaceWithNode(new Node[] {value == null ? null : this.getNode().getOwnerDocument().createTextNode(value.toString())});
        return false;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorExpression>
    {
        private final BOExpressionEvaluator _evaluator;

        public Factory(BOExpressionEvaluator evaluator)
        {
            this._evaluator = evaluator;
        }

        public BOValidatorExpression createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorExpression(parent, node, this._evaluator);
        }
    }

}
