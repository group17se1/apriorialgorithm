package APrioriMiner;

import java.util.*;

public class APrioriAlgorithm {

	private double minSupportLevel;
	private double minConfidenceLevel;
	private String tSetFile;
	private HashSet<ItemSet> itemSets;
	private HashSet<ItemSet> itemSubsets;
	private HashSet<ItemSet> transactions;
	HashSet<AssociationRule> associationRules;

	// Constructor for hard coding in support and confidence levels)
	public APrioriAlgorithm(double minSupportLevel, double minConfidenceLevel, String tSetFile) {

		// Initialize variables
		this.minConfidenceLevel = minConfidenceLevel;
		Parser parser = new Parser(tSetFile);
		this.transactions = parser.getTransactionSet();
		this.minSupportLevel = transactions.size() * minSupportLevel;
		this.itemSubsets = new HashSet<ItemSet>();
		itemSets = parser.getUniqueItems();
		generateRules();
	} // end of hard code constructor

	public void generateRules() {
		
		// Remove item sets that initially fall below the support level,
		// Then add what's left into a subset hashset.
		purgeNonSupported(itemSets);
		itemSubsets.addAll(itemSets);

		// Processes item sets and adds all possible ones to hashset.
		HashSet<ItemSet> uniqueItemSets = new HashSet<ItemSet>();
		generateItemSubsets(itemSets, uniqueItemSets);
		itemSubsets.addAll(uniqueItemSets);

		// Process data for association rules
		HashSet<AssociationRule> generatedRules = processData();
		System.out.println("--Item set size: " + itemSubsets.size());
		System.out.println(itemSubsets);

		// Set the association rules for this run, and print them out.
		this.associationRules = generatedRules;
		System.out.println("--Association Rules Found: " + generatedRules.size());
		System.out.println(generatedRules);

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
		if (!(set.size() == 0) || !(set.size() == 1)){
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
	
	public HashSet<ItemSet> countItemCandidates(HashSet<ItemSet> itemSet) {
		for (ItemSet currentItemSet : itemSet) {
			for (ItemSet currentTransactionSet : transactions) {
				if (currentTransactionSet.contains(currentItemSet)) {
					currentItemSet.increment(currentTransactionSet.getCount());
				}
			}
		}
		return itemSet;
	} // end of countItemCandidates
	
	public HashSet<ItemSet> joinLists(HashSet<ItemSet> setOne) {
		HashMap<ItemSet, Integer> newUniqueSubset = new HashMap<ItemSet, Integer>();
		ArrayList<ItemSet> listOne = new ArrayList<ItemSet>(setOne);
		for (int firstItemSetIndex = 0; firstItemSetIndex < listOne.size() - 1; firstItemSetIndex++) {
			for (int SecondItemSetIndex = firstItemSetIndex + 1; SecondItemSetIndex < listOne.size(); SecondItemSetIndex++) {
				// Grab the first and second ItemSet to clear up space.
				ItemSet ItemSetOne = listOne.get(firstItemSetIndex);
				ItemSet ItemSetTwo = listOne.get(SecondItemSetIndex);
				// check the number of same items
				int duplicateItems = ItemSetOne.duplicateItems(ItemSetTwo);
				if (duplicateItems == listOne.get(firstItemSetIndex).getItems().size() - 1) {
					ItemSet newItemSet = new ItemSet(ItemSetOne, ItemSetTwo);
					// No reason to check if it contains it because if it does,
					// it will not add newItemSet
					newUniqueSubset.put(newItemSet, newItemSet.hashCode());
				}
			}
		}
		return new HashSet<ItemSet>(newUniqueSubset.keySet());
	} // end of joinLists

	public HashSet<AssociationRule> processData() {
		HashMap<AssociationRule, Integer> allRules = new HashMap<AssociationRule, Integer>();
		for (ItemSet currentItemSet : itemSubsets) {
			for (ItemSet comparisonItemSet : itemSubsets) {
				double confidenceLevel = (currentItemSet.getCount() / comparisonItemSet.getCount());
				if (confidenceLevel >= minConfidenceLevel
						&& !(currentItemSet.equals(comparisonItemSet)) && currentItemSet.getItems().size() > comparisonItemSet.getItems().size() && currentItemSet.contains(comparisonItemSet)) {
					AssociationRule rule = new AssociationRule(currentItemSet, comparisonItemSet, confidenceLevel);
					allRules.put(rule, rule.hashCode());
				}
			}

		}
		return new HashSet<AssociationRule>(allRules.keySet());
	} // end of processData
	
} // end of class
