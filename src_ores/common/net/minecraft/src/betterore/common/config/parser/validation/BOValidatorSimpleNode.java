package net.minecraft.src.betterore.common.config.parser.validation;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

import net.minecraft.src.betterore.common.config.parser.BOConfigParser;
import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.config.parser.BOExpressionEvaluator;
import net.minecraft.src.betterore.common.config.parser.BOExpressionEvaluator.EvaluatorException;

public class BOValidatorSimpleNode extends BOValidatorNode
{
    public Object content = null;
    public BOExpressionEvaluator evaluator;
    private final Class _targetClass;

    protected BOValidatorSimpleNode(BOValidatorNode parent, Node node, Class targetClass, BOExpressionEvaluator evaluator)
    {
        super(parent, node);
        this._targetClass = targetClass;
        this.evaluator = (BOExpressionEvaluator)(evaluator == null ? this.getParser().defaultEvaluator : evaluator);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        StringBuilder buffer = new StringBuilder();

        for (Node input = this.getNode().getFirstChild(); input != null; input = input.getNextSibling())
        {
            if (input.getNodeType() == 3)
            {
                input.setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
                buffer.append(input.getNodeValue());
            }
        }

        String input1 = buffer.toString().trim();

        try
        {
            if (input1.startsWith(":="))
            {
                Object ex = this.evaluator.evaluate(input1.substring(2));

                if (ex == null)
                {
                    this.content = null;
                }
                else if (this._targetClass.isInstance(ex))
                {
                    this.content = ex;
                }
                else if (ex instanceof Number && Number.class.isAssignableFrom(this._targetClass))
                {
                    this.getParser();
                    this.content = BOConfigParser.convertNumber(this._targetClass, (Number)ex);
                }
                else
                {
                    this.content = BOConfigParser.parseString(this._targetClass, ex.toString());
                }
            }
            else
            {
                this.content = BOConfigParser.parseString(this._targetClass, input1);
            }

            return true;
        }
        catch (IllegalArgumentException var4)
        {
            throw new BOParserException(var4.getMessage(), this.getNode(), var4);
        }
        catch (EvaluatorException var5)
        {
            throw new BOParserException(var5.getMessage(), this.getNode(), var5);
        }
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorSimpleNode>
    {
        private final Class _targetClass;
        private final BOExpressionEvaluator _evaluator;

        public Factory(Class targetClass, BOExpressionEvaluator evaluator)
        {
            this._targetClass = targetClass;
            this._evaluator = evaluator;
        }

        public Factory(Class targetClass)
        {
            this._targetClass = targetClass;
            this._evaluator = null;
        }

        public BOValidatorSimpleNode createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorSimpleNode(parent, node, this._targetClass, this._evaluator);
        }
    }

}
