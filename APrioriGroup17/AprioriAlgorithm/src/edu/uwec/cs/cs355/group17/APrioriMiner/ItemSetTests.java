package edu.uwec.cs.cs355.group17.APrioriMiner;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class ItemSetTests {
	
	@Test
	public void testItemSetConstructor(){
		Item item = new Item("beer");
		Item item2 = new Item("dipper");
		Item item3 = new Item("");

		//		testing constructor with hashset
		HashSet<Item> hs = new HashSet<Item>();
		hs.add(item);
		hs.add(item2);
		ItemSet set1 = new ItemSet(hs);
		assertNotNull(set1);
		assertNotNull(set1.items);
		assertEquals(set1.getCount(), 1, 0);
		
//		testing constructor with item
		ItemSet set2 = new ItemSet(item);
		assertNotNull(set2);
		assertNotNull(set2.items);
		assertEquals(set2.getCount(), 1, 0);
		
//		testing constructor with empty item
		ItemSet set3 = new ItemSet(item3);
		assertNotNull(set3.getItems());		
	}
	
	@Test
	public void testItemSetGettersAndSetters(){
		
		Item item = new Item("beer");
		Item item2 = new Item("dipper");

		//		testing getters and setters
		ItemSet set = new ItemSet(item);
		assertNotNull(set.getCount());
		set.increment(5);
		assertEquals(set.getCount(), 6, 0);
		set.setCount(33);
		assertEquals(set.getCount(), 33, 0);
		assertFalse(set.contains(item2));
		set.add(item2);
		assertTrue(set.contains(item2));
	}
	
	@Test 
	public void testItemSetMethods(){
		Item item1 = new Item("beer");
		Item item2 = new Item("dipper");
		
		ItemSet set1 = new ItemSet(item1);
		ItemSet set2 = new ItemSet(item2);
		
		//equals()
		assertFalse(set1.equals(set2));
		
		set2.add(item1);
		set1.add(item2);
		assertTrue(set1.equals(set2));
		
		
		//duplicateItems(ItemSet set)
		for(int i = 0; i < 5; i++){
			set1.add(item1);
		}
		
		
		assertEquals(set1.duplicateItems(set2), 2);
	}
}

