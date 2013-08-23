package net.minecraft.src.betterore.common.config.parser.validation;

import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class BOValidatorSection extends BOValidatorNode
{
    protected BOValidatorSection(BOValidatorNode parent, Node node)
    {
        super(parent, node);
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.getNode().setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);
        this.replaceWithNodeContents(new Node[] {this.getNode()});
        return false;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorSection>
    {
        public BOValidatorSection createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorSection(parent, node);
        }
    }

}
