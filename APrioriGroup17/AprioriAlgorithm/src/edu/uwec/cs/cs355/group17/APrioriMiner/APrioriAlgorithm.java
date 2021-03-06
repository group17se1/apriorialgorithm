package edu.uwec.cs.cs355.group17.APrioriMiner;

import java.util.*;

public class APrioriAlgorithm {

	/***************
	 * VARIABLES *
	 ***************/
	public double minSupportLevel;
	public double minConfidenceLevel;
	public HashSet<ItemSet> itemSets;
	public HashSet<ItemSet> itemSubsets;
	public HashSet<ItemSet> transactions;
	HashSet<AssociationRule> associationRules;
	ArrayList<String> badSets = new ArrayList<String>();

	/******************
	 * CONSTRUCTORS *
	 ******************/
	public APrioriAlgorithm(double minSupportLevel, double minConfidenceLevel, String filepath) {
		// Initialize variables
		this.minConfidenceLevel = minConfidenceLevel;
		Parser parser = new Parser(filepath);
		this.transactions = parser.getTransactionSet();
		this.minSupportLevel = (double)transactions.size() * minSupportLevel;
		System.out.println("transaction size: " + (double) transactions.size());
		System.out.println("Support Level: " + this.minSupportLevel);
		this.itemSubsets = new HashSet<ItemSet>();
		this.itemSets = parser.getUniqueItems();
		this.badSets = parser.getBadSets();
		generateRules();
		
	} // end of hard code constructor

	/*************
	 * METHODS *
	 *************/
	public void generateRules() {
		// Remove item sets that initially fall below the support level,
		// Then add what's left into a subset hash set.
		purgeNonSupported(itemSets);
		itemSubsets.addAll(itemSets);

		// Processes item sets and adds all possible ones to hash set.
		HashSet<ItemSet> uniqueItemSets = new HashSet<ItemSet>();
		generateItemSubsets(itemSets, uniqueItemSets);
		itemSubsets.addAll(uniqueItemSets);

		// Process data for association rules
		HashSet<AssociationRule> generatedRules = processData();

		// Set the association rules for this run, and print them out.
		this.associationRules = generatedRules;
			
	} // end of generateRules
	
	public void purgeNonSupported(HashSet<ItemSet> itemSets) {
		// takes in a set of ItemSets and removes all ItemSets that fall below the
		// minimum support level necessary.
		Iterator<ItemSet> iterator = itemSets.iterator();
		while (iterator.hasNext()) {
			ItemSet currentItemSet = iterator.next();
			if (currentItemSet.getCount() < minSupportLevel) {
				iterator.remove();
			}
		}
	} // end of purgeNonSupported
	
	public void generateItemSubsets(HashSet<ItemSet> set, HashSet<ItemSet> uniqueItemSets){
		// we can ignore sets that are either empty or have one item... they can't produce
		// meaningful subsets.
		if (set.size() == 0){
			return;
		} else if (set.size() == 1){
			return;
		} else {
			// creating a hash set of possible subsets
			// this hash set will incorporate all possible
			// combinations, generated through the joinLists
			// method. Afterwards, it will review the candidates
			// and remove any set that does not meet the required
			// support level.
			HashSet<ItemSet> candidateItemSet = new HashSet<ItemSet>();
			candidateItemSet = joinLists(set);
			countItemCandidates(candidateItemSet);
			purgeNonSupported(candidateItemSet);
			if (!(candidateItemSet.size() == 0)) {
				uniqueItemSets.addAll(candidateItemSet);
			}
			generateItemSubsets(candidateItemSet, uniqueItemSets);
			
		}
	} // end of generateItemSubsets
	
	public void countItemCandidates(HashSet<ItemSet> itemSet) {
		// this method counts the number of each
		// particular transaction, incrementing counts
		// instead of adding duplicates.
		for (ItemSet currentItemSet : itemSet) {
			for (ItemSet currentTransactionSet : transactions) {
				if (currentTransactionSet.contains(currentItemSet)) {
					currentItemSet.increment(currentTransactionSet.getCount());
				}
			}
		}
//		return itemSet;
	} // end of countItemCandidates
	
	public HashSet<ItemSet> joinLists(HashSet<ItemSet> setOne) {
		// creates a hash map for new subsets 
		HashMap<ItemSet, Integer> newUniqueSubset = new HashMap<ItemSet, Integer>();
		ArrayList<ItemSet> listOne = new ArrayList<ItemSet>(setOne);
		// this loop takes an individual ItemSet in the array list and
		// compares it every other ItemSet in the array, creating a new
		// ItemSet that consists of all the unique items in either ItemSet.
		for (int firstItemSetIndex = 0; firstItemSetIndex < listOne.size() - 1; firstItemSetIndex++) {
			for (int SecondItemSetIndex = firstItemSetIndex + 1; SecondItemSetIndex < listOne.size(); SecondItemSetIndex++) {
				// Putting item sets into separate variables to
				// reduce command lengths
				ItemSet ItemSetOne = listOne.get(firstItemSetIndex);
				ItemSet ItemSetTwo = listOne.get(SecondItemSetIndex);
				// count the number of duplicate items
				int duplicateItems = ItemSetOne.duplicateItems(ItemSetTwo);
				if (duplicateItems == listOne.get(firstItemSetIndex).getItems().size() - 1) {
					ItemSet newItemSet = new ItemSet(ItemSetOne, ItemSetTwo);
					newUniqueSubset.put(newItemSet, newItemSet.hashCode());
				}
			}
		}
		return new HashSet<ItemSet>(newUniqueSubset.keySet());
	} // end of joinLists

	public HashSet<AssociationRule> processData() {
		// Creates a hash map for the association rules that will be generated
		HashMap<AssociationRule, Integer> allRules = new HashMap<AssociationRule, Integer>();
		// Takes one transaction and compares it to all the others, in order to generate all the
		// possible association rules
		for (ItemSet currentItemSet : itemSubsets) {
			for (ItemSet comparisonItemSet : itemSubsets) {
				// calculating confidence level, and then determining if the association rule meets
				// the criteria to be accepted:
				// 1. calculated confidence level exceeds or meets minimum confidence level
				// 2. The transactions that generated the rule are not the same
				// 3. the current transaction is larger in size than the comparison transaction
				// 4. the current transaction set contains the entirety of the comparison transaction
				double confidenceLevel = (currentItemSet.getCount() / comparisonItemSet.getCount());
				if (confidenceLevel >= minConfidenceLevel
						&& !(currentItemSet.equals(comparisonItemSet))
						&& currentItemSet.getItems().size() > comparisonItemSet.getItems().size() 
						&& currentItemSet.contains(comparisonItemSet)) {
					AssociationRule rule = new AssociationRule(currentItemSet, comparisonItemSet, confidenceLevel);
					allRules.put(rule, rule.hashCode());
				}
			}

		}
		return new HashSet<AssociationRule>(allRules.keySet());
	} // end of processData
	
} // end of class
