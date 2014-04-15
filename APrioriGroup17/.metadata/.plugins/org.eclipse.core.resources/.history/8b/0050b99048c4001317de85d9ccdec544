package APrioriMiner;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTests {

	@Test
	public void testItemConstructor(){
//		testing constructor
		Item item1 = new Item("beer");
		assertNotNull(item1);
		assertNotNull(item1.getName());
		
//		testing constructor with empty item
		Item item2 = new Item("");
		assertNotNull(item2);
		assertSame(item2.getName(), "");
	}

	@Test
	public void testItemGettersAndSetters(){
//		testing getters and setters
		Item item1 = new Item("beer");
		Item item2 = new Item("dipper");
		assertEquals(item1.getName(), "beer");
		assertEquals(item2.getName(), "dipper");
		
		item1.setName("glasses");
		assertNotEquals(item1.getName(), "beer");
		assertEquals(item1.getName(), "glasses");
		assertFalse(item1.equals(item2));
		assertTrue(item1.equals(new Item("glasses")));
		
		item1.setName("underwear");
		item2.setName("underwear");
		assertTrue(item1.equals(item2));
	}
}
