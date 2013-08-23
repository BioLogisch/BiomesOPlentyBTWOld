package net.minecraft.src.betterore.common.config.parser.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import net.minecraft.src.betterore.common.config.parser.BOConfigParser;
import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.config.parser.BOConfigParser.ConfigExpressionEvaluator;

public class BOValidatorNode
{
    private BOConfigParser _parser = null;
    private Node _node = null;
    private Hashtable<List,IValidatorFactory> _validatorMap = null;
    private boolean _validatorMapShared = false;

    public BOValidatorNode(BOConfigParser parser, Node node)
    {
        this._parser = parser;
        this._node = node;
        node.setUserData("validator", this, (UserDataHandler)null);
    }

    protected BOValidatorNode(BOValidatorNode parent, Node node)
    {
        if (parent._validatorMap != null)
        {
            this._validatorMap = parent._validatorMap;
            this._validatorMapShared = parent._validatorMapShared = true;
        }

        this._parser = parent._parser;
        this._node = node;
        node.setUserData("validator", this, (UserDataHandler)null);
    }

    public final Node getNode()
    {
        return this._node;
    }

    public final BOConfigParser getParser()
    {
        return this._parser;
    }

    public final void addGlobalValidator(short nodeType, String nodeName, IValidatorFactory factory)
    {
        List key = Arrays.asList(new Object[] { nodeType, nodeName.toLowerCase()});

        if (this._validatorMap == null)
        {
            this._validatorMap = new Hashtable();
            this._validatorMapShared = false;
        }
        else if (this._validatorMapShared)
        {
            this._validatorMap = new Hashtable(this._validatorMap);
            this._validatorMapShared = false;
        }

        this._validatorMap.put(key, factory);
    }

    public final void validate() throws BOParserException
    {
        if (this.validateChildren())
        {
            this.checkChildrenValid();
        }
    }

    protected boolean validateChildren() throws BOParserException
    {
        if (this._validatorMap != null)
        {
            LinkedList<Node> childList = new LinkedList();
            NamedNodeMap attributes = this._node.getAttributes();

            for (int children = 0; attributes != null && children < attributes.getLength(); ++children)
            {
                childList.addLast(attributes.item(children));
            }

            NodeList elements = this._node.getChildNodes();

            for (int children = 0; elements != null && children < elements.getLength(); ++children)
            {
                childList.addLast(elements.item(children));
            }

            for (Node child : childList) {
            	List key = Arrays.asList(new Object[] { child.getNodeType(), child.getNodeName().toLowerCase()});
                IValidatorFactory factory = this._validatorMap.get(key);

                if (factory != null)
                {
                    BOValidatorNode validator = factory.createValidator(this, child);

                    if (validator != null)
                    {
                        validator.validate();
                    }
                }
            }
        }

        return true;
    }

    protected void checkChildrenValid() throws BOParserException
    {
        NamedNodeMap attributes = this._node.getAttributes();

        for (int children = 0; attributes != null && children < attributes.getLength(); ++children)
        {
            Node c = attributes.item(children);

            if (c.getUserData("validated") == null)
            {
                throw new BOParserException("Unexpected at this location.", c);
            }
        }

        NodeList var5 = this._node.getChildNodes();

        for (int var6 = 0; var5 != null && var6 < var5.getLength(); ++var6)
        {
            Node child = var5.item(var6);

            switch (child.getNodeType())
            {
                case Node.TEXT_NODE:
                    if (child.getNodeValue() == null || child.getNodeValue().trim().isEmpty())
                    {
                        break;
                    }

                case Node.COMMENT_NODE:
                case Node.DOCUMENT_NODE:
                    break;

                default:
                    if (child.getUserData("validated") == null)
                    {
                        throw new BOParserException("Unexpected at this location.", child);
                    }
            }
        }
    }

    protected final <T extends BOValidatorNode> LinkedList<T> validateNamedChildren(int nodeTypeMask, String nodeName, IValidatorFactory<T> factory) throws BOParserException
    {
        LinkedList<T> childList = new LinkedList();
        NamedNodeMap attributes = this._node.getAttributes();
        NodeList children = this._node.getChildNodes();
        int attrCount = attributes == null ? 0 : attributes.getLength();
        int childCount = children == null ? 0 : children.getLength();

        for (int i = -attrCount; i < childCount; ++i)
        {
            Node validator = i < 0 ? attributes.item(-i - 1) : children.item(i);

            if ((nodeTypeMask >>> validator.getNodeType()) % 2 != 0 && (nodeName == null || validator.getNodeName().equalsIgnoreCase(nodeName)))
            {
                validator.setUserData("validated", Boolean.valueOf(true), (UserDataHandler)null);

                if (factory != null)
                {
                    childList.addLast(factory.createValidator(this, validator));
                }
            }
        }

        for(BOValidatorNode child : childList) {
        	child.validate();
        }
        
        return childList;
    }

    protected final <T> T validateNamedAttribute(Class<T> attrType, String attrName, T defaultValue, boolean allowElements) throws BOParserException
    {
        ConfigExpressionEvaluator evaluator = defaultValue == null ? null :
        	this.getParser().new ConfigExpressionEvaluator(defaultValue);
        T value = null;
        int mask = 4;

        if (allowElements)
        {
            mask |= 2;
        }

        LinkedList<BOValidatorSimpleNode> children = this.validateNamedChildren(mask, attrName, new BOValidatorSimpleNode.Factory(attrType, evaluator));

        if (!children.isEmpty())
        {
            value = (T)(children.getLast()).content;
        }

        return value == null ? defaultValue : value;
    }

    protected final <T> T validateRequiredAttribute(Class<T> attrType, String attrName, boolean allowElements) throws BOParserException
    {
        T value = this.validateNamedAttribute(attrType, attrName, null, allowElements);

        if (value != null)
        {
            return value;
        }
        else
        {
            throw new BOParserException("Required attribute \'" + attrName + "\' not found.", this.getNode());
        }
    }

    protected final void replaceWithNode(Node ... newNodes) throws BOParserException
    {
        Node parent = this.getNode().getParentNode();

        if (parent != null)
        {
            if (newNodes != null && newNodes.length > 0)
            {
            	for (Node newNode : newNodes) 
                {
            		if (newNode != null)
                    {
                        Node n = newNode;

                        while (true)
                        {
                            Node attr = (Node)n.getUserData("hidden-parent");

                            if (attr == null)
                            {
                                n.setUserData("hidden-parent", this.getNode(), (UserDataHandler)null);
                                break;
                            }

                            if (attr == this.getNode())
                            {
                                break;
                            }

                            n = attr;
                        }

                        if (newNode.getNodeType() == 2)
                        {
                            if (parent.getNodeType() != 1)
                            {
                                throw new BOParserException("Attempting to merge attribute to non-element node.", newNode);
                            }

                            Attr var9 = (Attr)newNode;
                            var9.getOwnerElement().removeAttributeNode(var9);
                            ((Element)parent).setAttributeNode(var9);
                        }
                        else
                        {
                            parent.insertBefore(newNode, this.getNode());
                        }
                    }
                }
            }

            parent.removeChild(this.getNode());
        }
    }

    protected final void replaceWithNodeContents(Node ... containerNodes) throws BOParserException
    {
        Node parent = this.getNode().getParentNode();

        if (parent != null)
        {
            if (containerNodes != null && containerNodes.length > 0)
            {
                ArrayList content = new ArrayList();
                
                for (Node containerNode : containerNodes) {
                	if (containerNode != null)
                    {
                        Node child;
                        Node n;

                        if (containerNode != this.getNode())
                        {
                            child = containerNode;

                            while (true)
                            {
                                n = (Node)child.getUserData("hidden-parent");

                                if (n == null)
                                {
                                    child.setUserData("hidden-parent", this.getNode(), (UserDataHandler)null);
                                    break;
                                }

                                if (n == this.getNode())
                                {
                                    break;
                                }

                                child = n;
                            }
                        }

                        Node hiddenParent;

                        if (parent.getNodeType() == 1)
                        {
                            NamedNodeMap var13 = containerNode.getAttributes();

                            for (int var14 = 0; var14 < var13.getLength(); ++var14)
                            {
                                hiddenParent = var13.item(var14);
                                Node n1 = hiddenParent;

                                while (true)
                                {
                                    Node hiddenParent1 = (Node)n1.getUserData("hidden-parent");

                                    if (hiddenParent1 == null)
                                    {
                                        n1.setUserData("hidden-parent", containerNode, (UserDataHandler)null);
                                        break;
                                    }

                                    if (hiddenParent1 == containerNode)
                                    {
                                        break;
                                    }

                                    n1 = hiddenParent1;
                                }

                                content.add(var13.item(var14));
                            }
                        }

                        for (child = containerNode.getFirstChild(); child != null; child = child.getNextSibling())
                        {
                            n = child;

                            while (true)
                            {
                                hiddenParent = (Node)n.getUserData("hidden-parent");

                                if (hiddenParent == null)
                                {
                                    n.setUserData("hidden-parent", containerNode, (UserDataHandler)null);
                                    break;
                                }

                                if (hiddenParent == containerNode)
                                {
                                    break;
                                }

                                n = hiddenParent;
                            }

                            content.add(child);
                        }
                    }
                }

                this.replaceWithNode((Node[])content.toArray(new Node[content.size()]));
            }
            else
            {
                parent.removeChild(this.getNode());
            }
        }
    }
    
    public static interface IValidatorFactory<T extends BOValidatorNode>
    {
        T createValidator(BOValidatorNode parent, Node node);
    }


    public static class Factory implements IValidatorFactory
    {
    	public BOValidatorNode createValidator(BOValidatorNode parent, Node node)
    	{
    		return new BOValidatorNode(parent, node);
    	}
    }
}
