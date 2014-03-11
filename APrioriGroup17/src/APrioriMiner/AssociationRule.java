package APrioriMiner;

import java.util.HashSet;

public class AssociationRule {

	/***************
	 * VARIABLES *
	 ***************/
	public ItemSet antecedent;
	public ItemSet consequent;
	public double supportLevel;
	public double confidenceLevel;
	
	/******************
	 * CONSTRUCTORS *
	 ******************/
	public AssociationRule(ItemSet set1, ItemSet consequentIn,double confidenceLevel) {
		this.antecedent = consequentIn;
		this.consequent = new ItemSet(new HashSet<Item>());
		this.confidenceLevel = confidenceLevel;
		for (Item setOneItem : set1.getItems()) {
			if (!consequentIn.contains(setOneItem)) {
				consequent.add(setOneItem);
			}
		}
	} // end of constructor
	
	/*************
	 * METHODS *
	 *************/
	public ItemSet getAntecedent() {
		return antecedent;
	}

	public void setAntecedent(ItemSet antecedent) {
		this.antecedent = antecedent;
	}

	public ItemSet getConsequent() {
		return consequent;
	}

	public void setConsequent(ItemSet consequent) {
		this.consequent = consequent;
	}

	public double getSupportLevel() {
		return supportLevel;
	}

	public void setSupportLevel(double supportLevel) {
		this.supportLevel = supportLevel;
	}

	public double getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(double confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}
	
	public String toString() {
		return "IF " + antecedent.getItems().toString() + " THEN " + consequent.getItems().toString() +" (confidence: " + confidenceLevel + ")";
	}
	
	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder(100);
		for (Item item : antecedent.getItems()) {
			sb.append(item);
		}
		for(Item item : consequent.getItems()) {
			sb.append(item);
		}
		return sb.toString().hashCode();
	} // end of hashCode
	
	@Override
	public boolean equals(Object o){
		return ((AssociationRule)o).hashCode() == this.hashCode();
	}
}