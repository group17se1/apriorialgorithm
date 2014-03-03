package APrioriMiner;

import java.io.Serializable;
import java.util.HashSet;

public class AssociationRule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// variables
	public ItemSet antecedent;
	public ItemSet consequent;
	public double supportLevel;
	public double confidenceLevel;

	// getters and setters
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

	public AssociationRule(ItemSet set1, ItemSet consequentIn,double confidenceLevel) {
		this.antecedent = consequentIn;
		this.consequent = new ItemSet(new HashSet<Item>());
		this.confidenceLevel = confidenceLevel;
		for (Item setOneItem : set1.getItems()) {
			if (!consequentIn.contains(setOneItem)) {
				consequent.add(setOneItem);
			}
		}
	}

	public String toString() {
		return "IF "+antecedent.getItems().toString() + "=>Then" + consequent.getItems().toString()+" (confidence:" + confidenceLevel + ")\n";
	}
	
	public String lineInTextFile(){
		String retval = "IF ";
		for(Item currentItem : antecedent.getItems()){
			retval += currentItem.toString() + " AND ";
		}
		retval = retval.substring(0, retval.length() - 5);
		retval += " THEN ";
		for(Item currentItem : consequent.getItems()){
			retval += currentItem.toString() + " AND ";
		}
		retval = retval.substring(0, retval.length() - 5);
		return retval;
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
	}
	
	@Override
	public boolean equals(Object o){
		return ((AssociationRule)o).hashCode() == this.hashCode();
	}
}