package APrioriMiner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Parser {

	/*************
	 * VARIABLES *
	 *************/
	private HashSet<ItemSet> transactionSet;
	private HashSet<ItemSet> uniqueItems;
	private ArrayList<String> badSets = new ArrayList<String>();
	private String store;
	private String startDate;
	private String endDate;

	/****************
	 * CONSTRUCTORS *
	 ****************/
	public Parser(String filepath) {

		// this regular expression will take in all items in a {item1,item2,itemN} format
		Pattern setRegex = Pattern
				.compile("^\\{,*\\p{Blank}*\\p{Alnum}+[-]*[\\p{Alnum}\\p{Blank}[-]*,]*\\p{Blank}*\\}$");

		// we're using hash maps to store our transaction sets because they work
		// very fast
		HashMap<ItemSet, ItemSet> transactionSet = new HashMap<ItemSet, ItemSet>();
		HashMap<ItemSet, ItemSet> uniqueItems = new HashMap<ItemSet, ItemSet>();

		// setting up the buffered reader
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Taking in the store name and then transaction set dates.
		// At this stage, this parser assumes correct formatting of the
		// first three lines of the transaction set file.
		try {
			this.store = reader.readLine().trim();
			this.startDate = reader.readLine().trim();
			this.endDate = reader.readLine().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// taking in first transaction
		String currentLine = null;
		try {
			currentLine = reader.readLine().trim();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		// loop to take in all lines of transaction set,
		// makes sure they match the correct formatting.
		// If not, they are thrown, and marked as erroneous.
		while (currentLine != null){
			
			// makes sure the current transaction line
			// has correct {item1,...,itemN} formatting.
			// If not, marks the set as erroneous and
			// stops reading.
			if (!setRegex.matcher(currentLine).matches()){
				System.out.println("Improper Formatting on: " + currentLine);
				badSets.add(currentLine);
				break;
			}
			
			// From this point on, we can assume the line
			// had correct formatting, so we can initialize
			// a hash set for the item sets.
			HashSet<Item> items = new HashSet<Item>();
			
			// Rather than throw out a transaction as invalid if it has superfluous commas,
			// at this point we can search the new string for repetitions and strip them out
			if (currentLine.contains("{,")){
				currentLine = currentLine.substring(0,1) + currentLine.substring(2, currentLine.length());
			}
			while (currentLine.contains(",,")){
				int index = currentLine.indexOf(",,");
				int length = currentLine.length();
				currentLine = currentLine.substring(0,index) + currentLine.substring(index+1, length);
			}
			
			// Removing brackets and white space
			currentLine = currentLine.replace("{", "").replace(" ", "").replace("}", "");
			
			// Casting transaction to all lower case in order to avoid duplicate items
			// with mismatching cases
			currentLine = currentLine.toLowerCase();
			
			// Splitting line into an array of items
			String[] itemList = currentLine.split(",");
			
			// processing the items as a transaction and counting
			// number of each item
			for (String item : itemList){
				
				// grabbing the next item in the array
				Item newItem = new Item(item.trim());
				ItemSet set = new ItemSet(newItem);
				
				// this initializes the item to have shown up
				// at least once.
				ItemSet lastSet = uniqueItems.put(set, set);
				if (lastSet != null) {
					lastSet.increment(1);
					uniqueItems.put(lastSet, lastSet);
				}
				
				// adding the item to the hash set
				items.add(newItem);
			}
			
			// Comparing transaction against what's already
			// in the hash set. If it's already present, we'll
			// increment the amount of times it shows up. In this
			// way, we don't have to spend time later searching for
			// equivalent transactions.
			ItemSet newTransaction = new ItemSet(items);
			ItemSet lastTransaction = transactionSet.put(newTransaction, newTransaction);
			if (lastTransaction != null){
				lastTransaction.increment(1);
				transactionSet.put(lastTransaction, lastTransaction);
			}
			
			// reading in the next transaction
			try {
				currentLine = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} // finished reading in transactions
		
		// Now that we've read in all transactions, we can finish up by converting
		// our hash maps to hash sets for later use.
		this.transactionSet = new HashSet<ItemSet>(transactionSet.keySet());
		this.uniqueItems = new HashSet<ItemSet>(uniqueItems.keySet());
		
	} // end of parser constructor

	/***********
	 * METHODS *
	 ***********/
	// Here we have the getters for the variables,
	// No need for setters, as nothing in this class
	// is set outside of this class.
	
	public HashSet<ItemSet> getTransactionSet() {
		return transactionSet;
	}
	
	public String getStore(){
		return store;
	}
	
	public String getStartDate(){
		return startDate;
	}
	
	public String getEndDate(){
		return endDate;
	}

	public HashSet<ItemSet> getUniqueItems() {
		return uniqueItems;
	}

	public ArrayList<String> getBadSets(){
		return badSets;
	}
	
} // end of Parser
