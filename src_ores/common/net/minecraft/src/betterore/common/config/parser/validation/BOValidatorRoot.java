package net.minecraft.src.betterore.common.config.parser.validation;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.src.betterore.common.config.parser.BOParserException;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

public class BOValidatorRoot extends BOValidatorNode
{
    private final Collection<String> _topLevelNodes;

    protected BOValidatorRoot(BOValidatorNode parent, Node node, Collection<String> topLevelNodes)
    {
        super(parent, node);
        this._topLevelNodes = topLevelNodes;
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        Node parent = this.getNode().getParentNode();

        if (parent != null && parent.getNodeType() == 9)
        {
            this.getNode().setUserData("validated", true, (UserDataHandler)null);

            if (this._topLevelNodes != null)
            {
            	for (String nodeName : this._topLevelNodes) {
            		this.validateNamedChildren(2, nodeName, (IValidatorFactory)null);
            	}
            }
        }

        return true;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorRoot>
    {
        private final Collection<String> _topLevelNodes;

        public Factory(Collection<String> topLevelNodes)
        {
            this._topLevelNodes = topLevelNodes;
        }

        public BOValidatorRoot createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorRoot(parent, node, this._topLevelNodes);
        }
    }

}
