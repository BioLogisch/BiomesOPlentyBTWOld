package net.minecraft.src.betterore.common.config.parser.validation;

import java.util.Iterator;

import net.minecraft.src.betterore.common.config.BOChoiceOption;
import net.minecraft.src.betterore.common.config.BOConfigOption;
import net.minecraft.src.betterore.common.config.BONumericOption;
import net.minecraft.src.betterore.common.config.parser.BOConfigParser;
import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.util.BOLogger;

import org.w3c.dom.Node;


public class BOValidatorOption extends BOValidatorNode
{
    private final Class _type;

    protected BOValidatorOption(BOValidatorNode parent, Node node, Class type)
    {
        super(parent, node);
        this._type = type;
    }

    protected boolean validateChildren() throws BOParserException
    {
        super.validateChildren();
        String optionName = this.validateRequiredAttribute(String.class, "name", true);
        BOConfigOption option = null;
        Class valueType = null;

        if (this._type == BOChoiceOption.class)
        {
            valueType = String.class;
            BOChoiceOption groupName = new BOChoiceOption(optionName);
            option = groupName;
            Iterator<BOValidatorChoice> defValue = this.validateNamedChildren(2, "Choice", new BOValidatorChoice.Factory()).iterator();

            while (defValue.hasNext())
            {
                BOValidatorChoice loadedValueStr = defValue.next();
                groupName.addPossibleValue(loadedValueStr.value, loadedValueStr.displayValue, loadedValueStr.description);
            }

            if (groupName.getValue() == null)
            {
                throw new BOParserException("Choice option has no possible values.", this.getNode());
            }
        }
        else if (this._type == BONumericOption.class)
        {
            valueType = Double.class;
            BONumericOption groupName1 = new BONumericOption(optionName);
            option = groupName1;
            double defValue1 = this.validateNamedAttribute(valueType, "min", groupName1.getMin(), true);
            double err = this.validateNamedAttribute(valueType, "max", groupName1.getMax(), true);

            if (!groupName1.setLimits(defValue1, err))
            {
                throw new BOParserException("Numeric option value range [" + defValue1 + "," + err + "] is invalid.", this.getNode());
            }

            double dmin = this.validateNamedAttribute(valueType, "displayMin", groupName1.getMin(), true);
            double dmax = this.validateNamedAttribute(valueType, "displayMax", groupName1.getMax(), true);
            double dincr = this.validateNamedAttribute(valueType, "displayIncrement", (dmax - dmin) / 100.0D, true);

            if (!groupName1.setDisplayLimits(dmin, dmax, dincr))
            {
                throw new BOParserException("Numeric option display range/increment [" + dmin + "," + dmax + "]/" + dincr + " is invalid.", this.getNode());
            }
        }
        else if (this._type == BOConfigOption.DisplayGroup.class)
        {
            option = new BOConfigOption.DisplayGroup(optionName);
        }

        option.setDisplayState(this.validateNamedAttribute(BOConfigOption.DisplayState.class, "displayState", option.getDisplayState(), true));
        option.setDisplayName(this.validateNamedAttribute(String.class, "displayName", option.getDisplayName(), true));
        option.setDescription(this.validateNamedAttribute(String.class, "description", option.getDescription(), true));
        String groupName2 = this.validateNamedAttribute(String.class, "displayGroup", null, true);

        if (groupName2 != null)
        {
            BOConfigOption defValue3 = this.getParser().target.getConfigOption(groupName2);

            if (defValue3 == null || !(defValue3 instanceof BOConfigOption.DisplayGroup))
            {
                throw new BOParserException("Option \'" + groupName2 + "\' is not a recognized Display Group.", this.getNode());
            }

            option.setDisplayGroup((BOConfigOption.DisplayGroup)defValue3);
        }

        Object defValue2 = valueType == null ? null : this.validateNamedAttribute(valueType, "default", (Object)null, true);

        if (this.getParser().target.getConfigOption(option.getName()) != null)
        {
            throw new BOParserException("An Option named \'" + option.getName() + "\' already exists.", this.getNode());
        }
        else
        {
            this.getParser().target.getConfigOptions().add(option);
            String loadedValueStr1 = this.getParser().target.loadConfigOption(option.getName());

            if (loadedValueStr1 != null)
            {
                String err1 = null;

                try
                {
                    Object ex = BOConfigParser.parseString(valueType, loadedValueStr1);

                    if (!((BOConfigOption)option).setValue(ex))
                    {
                        err1 = "";
                    }
                }
                catch (IllegalArgumentException var15)
                {
                    err1 = " (" + var15.getMessage() + ")";
                }

                if (err1 == null)
                {
                    return true;
                }

                BOLogger.log.warning("The saved value \'" + loadedValueStr1 + "\' for Config Option \'" + ((BOConfigOption)option).getName() + "\' is invalid" + err1 + ".  " + "The default value \'" + (defValue2 == null ? option.getValue() : defValue2) + "\' will be used instead.");
            }

            if (defValue2 != null && !(option.setValue(defValue2)))
            {
                throw new BOParserException("Invalid default value \'" + defValue2 + "\' for option \'" + option.getName() + "\'.", this.getNode());
            }
            else
            {
                return true;
            }
        }
    }
    
    public static class Factory implements IValidatorFactory<BOValidatorOption>
    {
        private final Class _type;

        public Factory(Class type)
        {
            this._type = type;
        }

        public BOValidatorOption createValidator(BOValidatorNode parent, Node node)
        {
            return new BOValidatorOption(parent, node, this._type);
        }
    }

}
