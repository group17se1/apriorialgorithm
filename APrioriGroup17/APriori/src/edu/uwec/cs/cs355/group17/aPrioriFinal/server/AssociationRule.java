package edu.uwec.cs.cs355.group17.aPrioriFinal.server;

import java.io.Serializable;
import java.util.HashSet;

public class AssociationRule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/***************
	 * VARIABLES *
	 ***************/
	public ItemSet antecedent;
	public ItemSet consequent;
	public double supportLevel;
	public double confidenceLevel;
	
	/******************
	 *  CONSTRUCTORS  *
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
	 *  METHODS	 *
	 *************/
	// no getters and setters, as they are would not be used.
	
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