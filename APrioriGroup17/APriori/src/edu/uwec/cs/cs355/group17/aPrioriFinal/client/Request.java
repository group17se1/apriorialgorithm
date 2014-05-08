package edu.uwec.cs.cs355.group17.aPrioriFinal.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import edu.uwec.cs.cs355.group17.aPrioriFinal.server.AssociationRule;
import edu.uwec.cs.cs355.group17.aPrioriFinal.server.ItemSet;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	/***************
	 * VARIABLES *
	 ***************/
	String filepath;
	public double time;
	public double minSupportLevel;
	public double minConfidenceLevel;
	public HashSet<ItemSet> itemSets;
	public HashSet<ItemSet> itemSubsets;
	public HashSet<ItemSet> transactions;
	public HashSet<AssociationRule> associationRules;
	public ArrayList<String> badSets = new ArrayList<String>();
	
	/******************
	 * CONSTRUCTORS *
	 ******************/
	public Request(){
		// default constructor
	}
	
	public Request(Request aRequest){
		this.minConfidenceLevel = aRequest.getMinConfidenceLevel();
		this.minSupportLevel = aRequest.getMinSupportLevel();
		this.associationRules = aRequest.getAssociationRules();
		this.filepath = aRequest.getFilepath();
		this.badSets = aRequest.getBadSets();
		this.time = aRequest.getTime();
		this.transactions = aRequest.getTransactions();
		this.itemSets = aRequest.getItemSets();
		this.itemSubsets = aRequest.getItemSubsets();
	}
	
	public Request(double mcl, double msl, String filepath){
		this.filepath = filepath;
		this.minConfidenceLevel = mcl;
		this.minSupportLevel = msl;
		this.transactions = new HashSet<ItemSet>();
		this.itemSets = new HashSet<ItemSet>();
		this.itemSubsets = new HashSet<ItemSet>();
		this.associationRules = new HashSet<AssociationRule>();
	}
	
	public Request(HashSet<AssociationRule> associationRules, ArrayList<String> badSets, long time){
		this.associationRules = associationRules;
		this.badSets = badSets;
		this.time = time;
		this.transactions = new HashSet<ItemSet>();
		this.itemSets = new HashSet<ItemSet>();
		this.itemSubsets = new HashSet<ItemSet>();
	}
	
	/*************
	 * METHODS *
	 *************/
	public HashSet<ItemSet> getTransactions() {
		return transactions;
	}
	
	public double getMinConfidenceLevel() {
		return minConfidenceLevel;
	}
	
	public double getMinSupportLevel() {
		return minSupportLevel;
	}

	public HashSet<ItemSet> getItemSets() {
		return itemSets;
	}

	public HashSet<ItemSet> getItemSubsets(){
		return itemSubsets;
	}
	
	public String getFilepath() {
		return filepath;
	}

	public HashSet<AssociationRule> getAssociationRules() {
		return associationRules;
	}

	public ArrayList<String> getBadSets(){
		return badSets;
	}

	public double getTime(){
		return time;
	}

	
} // end of Request
