package net.minecraft.src.betterore.common.config.parser.validation;

import org.w3c.dom.Node;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.generator.IBOOreDistribution;
import net.minecraft.src.betterore.common.generator.tools.BOPDist;
import net.minecraft.src.betterore.common.generator.tools.BOPDist.Type;

public class BOValidatorPDist extends BOValidatorNode
{
    private final IBOOreDistribution _parentDist;
    public String name = null;
    public BOPDist pdist = null;

    protected BOValidatorPDist(BOValidatorNode parent, Node node, IBOOreDistribution parentDistribution)
    {
        super(parent, node);
        this._parentDist = parentDistribution;
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        this.name = (String)this.validateRequiredAttribute(String.class, "Name", true);

        if (this._parentDist == null)
        {
            this.pdist = new BOPDist();
        }
        else
        {
            try
            {
                this.pdist = (BOPDist)this._parentDist.getDistributionSetting(this.name);
            }
            catch (ClassCastException var4)
            {
                throw new BOParserException("Setting \'" + this.name + "\' is not supported by this distribution.", this.getNode(), var4);
            }

            if (this.pdist == null)
            {
                throw new BOParserException("Setting \'" + this.name + "\' is not supported by this distribution.", this.getNode());
            }
        }

        this.pdist.mean = ((Float)this.validateNamedAttribute(Float.class, "Avg", Float.valueOf(this.pdist.mean), true)).floatValue();
        this.pdist.range = ((Float)this.validateNamedAttribute(Float.class, "Range", Float.valueOf(this.pdist.range), true)).floatValue();
        this.pdist.type = (Type)this.validateNamedAttribute(Type.class, "Type", this.pdist.type, true);

        if (this._parentDist != null)
        {
            try
            {
                this._parentDist.setDistributionSetting(this.name, this.pdist);
            }
            catch (IllegalAccessException var2)
            {
                throw new BOParserException("Setting \'" + this.name + "\' is not configurable.", this.getNode(), var2);
            }
            catch (IllegalArgumentException var3)
            {
                throw new BOParserException("Setting \'" + this.name + "\' is not supported by this distribution.", this.getNode(), var3);
            }
        }

        return true;
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorPDist>
    {
        private final IBOOreDistribution _parentDist;

        public Factory(IBOOreDistribution parentDistribution)
        {
            this._parentDist = parentDistribution;
        }

        public BOValidatorPDist createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorPDist(parent, node, this._parentDist);
        }
    }

}
