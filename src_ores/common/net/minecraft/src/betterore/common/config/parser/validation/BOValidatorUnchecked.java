package net.minecraft.src.betterore.common.config.parser.validation;

import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;

public class BOValidatorUnchecked extends BOValidatorNode
{
    protected BOValidatorUnchecked(BOValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        return false;
    }
    

    public static class Factory implements IValidatorFactory<BOValidatorUnchecked>
    {
    	public BOValidatorUnchecked createValidator(BOValidatorNode parent, Node node)
    	{
    		return new BOValidatorUnchecked(parent, node);
    	}
    }

}
