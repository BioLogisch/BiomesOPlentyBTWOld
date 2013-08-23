package net.minecraft.src.betterore.common.config.parser.validation;

import java.util.Collection;

import org.w3c.dom.Node;
import org.w3c.dom.UserDataHandler;

import net.minecraft.src.betterore.common.config.parser.BOParserException;
import net.minecraft.src.betterore.common.util.BOBiomeDescriptor;

public class BOValidatorBiomeSet extends BOValidatorNode {
	
	public BOBiomeDescriptor biomeSet;
	public float weight = 1.0F;
	
	protected BOValidatorBiomeSet(BOValidatorNode parent, Node node) {
		super(parent, node);
	}

	protected boolean validateChildren() throws BOParserException
	{
		super.validateChildren();
		
		this.biomeSet = new BOBiomeDescriptor();
		String name = this.validateNamedAttribute(String.class, "name", null, true);
		if (name != null) {
			this.biomeSet.setName(name);
			this.getParser().target.getBiomeSets().add(this.biomeSet);
		}
		
		this.weight = this.validateNamedAttribute(Float.class, "Weight", this.weight, true);
		
		String inherits = this.validateNamedAttribute(String.class, "inherits", null, true);
		if (inherits != null)	
		{
			Collection<BOBiomeDescriptor> sets = this.getParser().target.getBiomeSets(inherits);

			if (sets.isEmpty())
			{
				throw new BOParserException("Cannot inherit biomes (\'" + inherits + "\' is not a loaded biome set).", this.getNode());
			}

			if (sets.size() > 1)
			{
	            throw new BOParserException("Cannot inherit biomes (\'" + inherits + "\' is ambiguous; matching " + sets.size() + " loaded biome sets).", this.getNode());
			}

			try
			{
				this.biomeSet.addAll(sets.iterator().next(), this.weight);
			}
			catch (IllegalArgumentException e)
			{
				throw new BOParserException("Cannot inherit biomes (" + e.getMessage() + ").", this.getNode(), e);
			}
		}
	        
		validateBiomes();
		
		this.getNode().setUserData("validated", true, null);
		
		return true;
	}
	    
	public void validateBiomes() throws BOParserException {
		for (BOValidatorBiomeDescriptor biome : validateNamedChildren(2, "Biome", new BOValidatorBiomeDescriptor.Factory())) {
			this.biomeSet.add(biome.biome, biome.weight * this.weight, biome.climate, false);
        }
        for (BOValidatorBiomeDescriptor biomeType : validateNamedChildren(2, "BiomeType", new BOValidatorBiomeDescriptor.Factory())) {
        	this.biomeSet.add(biomeType.biome, biomeType.weight * this.weight, biomeType.climate, true);
        }
        for (BOValidatorBiomeSet biomeSet : validateNamedChildren(2, "BiomeSet", new BOValidatorBiomeSet.Factory())) {
        	this.biomeSet.addAll(biomeSet.biomeSet, this.weight);
        }
	}
	
	public static class Factory implements IValidatorFactory<BOValidatorBiomeSet>
	{
		public BOValidatorBiomeSet createValidator(BOValidatorNode parent, Node node)
		{
			return new BOValidatorBiomeSet(parent, node);
		}
	}

}
