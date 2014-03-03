package APrioriMiner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Parser {
	private HashSet<ItemSet> transactionSet;
	private HashSet<ItemSet> uniqueItems;
	boolean validInput;
	String failedLine;

	public HashSet<ItemSet> getTransactionSet() {
		return transactionSet;
	}

	public void setTransactionSet(HashSet<ItemSet> transactionSet) {
		this.transactionSet = transactionSet;
	}

	public HashSet<ItemSet> getUniqueItems() {
		return uniqueItems;
	}

	public void setUniqueItems(HashSet<ItemSet> uniqueItems) {
		this.uniqueItems = uniqueItems;
	}

	public boolean isValidInput() {
		return validInput;
	}

	public void setValidInput(boolean validInput) {
		this.validInput = validInput;
	}

	public String getFailedLine() {
		return failedLine;
	}

	public void setFailedLine(String failedLine) {
		this.failedLine = failedLine;
	}

	/**
	 * Parses the text file into the item list and also generates and counts the
	 * unique item list.
	 * 
	 * @param path
	 *            : the path of the text file to be read.
	 * @author Andrew Paffel & Alexander Hurd
	 */
	public Parser(String path) {
		validInput = true;
		// Create the regex pattern to check each line against
		Pattern p = Pattern
				.compile("^\\{\\p{Blank}*\\p{Alnum}+[\\p{Alnum}\\p{Blank},]*\\p{Blank}*\\}$");
		BufferedReader br = null;

		// Create HashMaps so that we can just try to put the transactions and
		// items in there
		// and if they come back with one, just increment it and put it back in
		// (fast)
		HashMap<ItemSet, ItemSet> uniqueItemsFound = new HashMap<ItemSet, ItemSet>();
		HashMap<ItemSet, ItemSet> transactions = new HashMap<ItemSet, ItemSet>();

		// Instantiate our Buffered Reader
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Read the first line of the file
		String sCurrentLine = null;
		try {
			sCurrentLine = br.readLine().trim();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (sCurrentLine != null) {
			// Check the line for proper formatting
			if (!p.matcher(sCurrentLine).matches()) {
				failedLine = sCurrentLine;
				validInput = false;
				break;
			}
			// Remove braces and the spaces.
			sCurrentLine = sCurrentLine.replace("}", "").replace("{", "")
					.replace(" ", "");

			// Split the comma delimited list into the items.
			String[] dataArray = sCurrentLine.split(",");
			HashSet<Item> itemSet = new HashSet<Item>();

			// Create the transaction and also count the number of times the
			// individual items appear
			for (String item : dataArray) {
				Item currentItem = new Item(item.trim());
				ItemSet singleItemSet = new ItemSet(currentItem);

				ItemSet oldItemSet = uniqueItemsFound.put(singleItemSet,
						singleItemSet);
				if (oldItemSet != null) {
					oldItemSet.increment(1);
					uniqueItemsFound.put(oldItemSet, oldItemSet);
				}
				itemSet.add(currentItem);
			}
			// Check the current transaction, and if it appears in the hash map,
			// just increment the amount of times that that transaction appears.
			// This allows us to just use a count instead of searching duplicate
			// transactions.
			ItemSet currentTransaction = new ItemSet(itemSet);
			ItemSet oldTransaction = transactions.put(currentTransaction,
					currentTransaction);
			if (oldTransaction != null) {
				oldTransaction.increment(1);
				transactions.put(oldTransaction, oldTransaction);
			}

			try {
				sCurrentLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Convert our HashMaps into HashSets.
		this.setTransactionSet(new HashSet<ItemSet>(transactions.keySet()));
		this.setUniqueItems(new HashSet<ItemSet>(uniqueItemsFound.keySet()));
	}
}
