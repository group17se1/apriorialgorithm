package APrioriMiner;

import java.io.Serializable;
import java.util.HashSet;

public class ItemSet implements Serializable {

	private static final long serialVersionUID = 1L;

	public HashSet<Item> items;
	public int count;

	public ItemSet(HashSet<Item> items) {
		count = 1;
		this.items = items;
	}

	public ItemSet(Item item) {
		items = new HashSet<Item>();
		items.add(item);
		count = 1;
	}

	public ItemSet(ItemSet itemSetOne, ItemSet itemSetTwo) {
		items = (HashSet<Item>) itemSetOne.getItems().clone();
		items.addAll((HashSet<Item>) itemSetTwo.getItems().clone());
		count = 0;
	}

	public void increment(int value) {
		this.count += value;
	}

	public HashSet<Item> getItems() {
		return items;
	}

	public void setItems(HashSet<Item> items) {
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean contains(ItemSet otherItemSet) {
		return items.containsAll(otherItemSet.getItems());
	}

	public boolean contains(Item item) {
		return items.contains(item);
	}

	public void add(Item item) {
		this.items.add(item);
		this.count = 0;
	}

	public boolean equals(ItemSet otherItemSet) {
		return equals((Object) otherItemSet);
	}

	public int duplicateItems(ItemSet comparisonItemSet) {
		int duplicateItems = 0;
		for (Item comparisonItem : comparisonItemSet.getItems()) {
			if (items.contains(comparisonItem)) {
				duplicateItems++;
			}
		}
		return duplicateItems;
	}

	public String toString() {
		return count + ": " + items.toString();
	}

	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder(100);
		for (Item item : items) {
			sb.append(item);
		}
		return sb.toString().hashCode();
	}

	public String outputTransactions() {
		String output = "{";
		for (Item item : items) {
			output += item.toString() + ",";
		}
		output = output.substring(0, output.length() - 1) + "}";
		return output;
	}

	@Override
	public boolean equals(Object o) {
		return ((ItemSet) o).hashCode() == items.hashCode();
	}

}
