package edu.uwec.cs.cs355.group17.APrioriMiner;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class APTests {

	@Test
	public void testGenerateRulesTransactions() {
		String filepath = "src/transactions1.txt";
		APrioriAlgorithm generator = new APrioriAlgorithm(0.25, 0.5, filepath);

		assertEquals(generator.itemSubsets.size(), 9);
		assertEquals(generator.associationRules.size(), 9);

		generator = new APrioriAlgorithm(0.5, 0.66, filepath);
		assertEquals(generator.itemSubsets.size(), 5);
		assertEquals(generator.associationRules.size(), 4);
		
		generator = new APrioriAlgorithm(0.75, 0.66, filepath);
		assertEquals(generator.itemSubsets.size(), 2);
		assertEquals(generator.associationRules.size(), 0);
		
		generator = new APrioriAlgorithm(0.5, 0.8, filepath);
		assertEquals(generator.itemSubsets.size(), 5);
		assertEquals(generator.associationRules.size(), 1);
		
		
		generator = new APrioriAlgorithm(0, 0, filepath);
		assertEquals(generator.itemSubsets.size(), 9);
		assertEquals(generator.associationRules.size(), 10);

		
		filepath = "src/transactions5.txt";
		generator = new APrioriAlgorithm(0.25, 0.5, filepath);
		assertEquals(generator.itemSubsets.size(), 76);
		assertEquals(generator.associationRules.size(), 43);
		
		filepath = "src/transactions6.txt";
		generator = new APrioriAlgorithm(0.014, 0.2, filepath);
		assertEquals(generator.itemSubsets.size(), 103);
		assertEquals(generator.associationRules.size(), 4);
		
		//This  transaction file only has 9744 transactions making the test fail for the association rules
		filepath = "src/transactions7.txt";
		generator = new APrioriAlgorithm(0.012, 0.6, filepath);
		assertEquals(generator.itemSubsets.size(), 80);
		assertEquals(generator.associationRules.size(), 10);
	}
	
	@Test
	public void testGenerateRulesTESCASE5() {
		double minSL = 0.4;
		double confidenceLevel = 0.7;
		String filepath = "src/testcase5";
		APrioriAlgorithm generator = new APrioriAlgorithm(minSL, confidenceLevel, filepath);
		
		assertEquals(generator.itemSubsets.size(), 12);
		assertEquals(generator.associationRules.size(), 2);
	}
	
	@Test
	public void testPurgeNonSupportted(){
		HashSet<ItemSet> set = new HashSet<ItemSet>();
		
		Item item1 = new Item("beer");
		Item item2 = new Item("steak");
		Item item3 = new Item("wine");
		Item item4 = new Item("soup");
		Item item5 = new Item("rope");
		ItemSet is = new ItemSet(item1);
		is.add(item2);
		is.add(item3);
		is.add(item4);
		is.add(item5);
		set.add(is);
		
		double minSL = 0.4;
		double confidenceLevel = 0.7;
		String filepath = "src/testcase6";
		APrioriAlgorithm generator = new APrioriAlgorithm(minSL, confidenceLevel, filepath);
		generator.minSupportLevel = 6;
		assertTrue(set.size() == 1);
		generator.purgeNonSupported(set);
		assertTrue(set.size() == 0);
		
	}
	
	@Test
	public void testGenerateItemSubsets(){
		double minSL = 0.4;
		double confidenceLevel = 0.7;
		String filepath = "src/testcase7";
		APrioriAlgorithm generator = new APrioriAlgorithm(minSL, confidenceLevel, filepath);
		Parser p = new Parser(filepath);
		HashSet<ItemSet> uniqueItemSets = new HashSet<ItemSet>();
		generator.generateItemSubsets(p.getUniqueItems(), uniqueItemSets);
		assertTrue(uniqueItemSets.size() > 0);
		
		filepath = "src/testcase8";
		p = new Parser(filepath);
		uniqueItemSets = new HashSet<ItemSet>();
		generator = new APrioriAlgorithm(0, 0, filepath);
		System.out.println(uniqueItemSets);
		generator.generateItemSubsets(p.getUniqueItems(), uniqueItemSets);
		assertTrue(uniqueItemSets.size() == 6);
	}
	
	@Test
	public void testCountItemCandidate(){
		double minSL = 0.4;
		double confidenceLevel = 0.7;
		String filepath = "src/testcase7";
		Parser p = new Parser(filepath);
		APrioriAlgorithm generator = new APrioriAlgorithm(minSL, confidenceLevel, filepath);
		HashSet<ItemSet> itemSet = p.getUniqueItems();
		generator.countItemCandidates(itemSet);
		for(ItemSet is : itemSet){
			assertTrue(is.getCount() == 2);
		}
	}
	
	

}