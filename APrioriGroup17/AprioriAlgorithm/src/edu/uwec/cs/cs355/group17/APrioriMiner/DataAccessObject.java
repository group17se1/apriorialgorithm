package edu.uwec.cs.cs355.group17.APrioriMiner;

import java.util.HashSet;
import java.util.Iterator;
import java.sql.*;

public class DataAccessObject {

	/*************
	 * VARIABLES *
	 *************/
	private double supportLevel;
	private double confidenceLevel;

	/****************
	 * CONSTRUCTORS *
	 ****************/
	public DataAccessObject() {
		//default constructor
	}

	/***********************
	 * GETTERS AND SETTERS *
	 ***********************/
	public double getSupportLevel() {
		return supportLevel;
	}

	public void setSupportLevel(double supportLevel) {
		this.supportLevel = supportLevel;
	}
	
	public void setConfidenceLevel(double confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public double getConfidenceLevel() {
		return confidenceLevel;
	}

	/***********
	 * METHODS *
	 ***********/
	public Connection connect(){

		// initializing our database information
		String username = "CS355GROUP17";
		String password = "K564781$";

		// getting driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("driver loaded");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: Unable to retrieve driver");
			System.out.println(e.getMessage());
			System.exit(1);
		}

		// connecting to database
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://dario.cs.uwec.edu/" + username, username, password);
			//System.out.println("connection established");
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to connect to database");
			System.out.println(e.getMessage());
			System.exit(1);
		}		

		return connection;

	} // end of connect

	private void disconnect(Connection connection, Statement statement){

		// closing connection
		try {
			if(statement != null){
				statement.close();
				//System.out.println("statement closed");
			}
			if(connection != null){
				connection.close();
				//System.out.println("connection closed");

			}
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to close connection");
			System.out.println(e.getMessage());
			System.exit(1);
		}

	} // end of disconnect

	public void uploadTransaction(String store, String startDate, String endDate, HashSet<ItemSet> transaction){

		// establish database connection
		Connection connection = connect();

		// initialize variables for query
		PreparedStatement stmt = null;

		// attempt to execute query for transaction info
		try {
			stmt = connection.prepareStatement("INSERT INTO transaction_info(transaction_store, transaction_start_date, transaction_end_date) VALUES(?,?,?)");
			stmt.setString(1, store);
			stmt.setString(2, startDate);
			stmt.setString(3, endDate);
			stmt.execute();
		} catch (SQLException e){
			System.out.println("ERROR: Unable to upload transaction information");
			System.out.println(e.getMessage());
			System.exit(1);
		}

		// retrieving database ID for transaction info
		Statement queryStatement = null;
		ResultSet results = null;
		int transaction_set_ID = 0;
		try {
			queryStatement = connection.createStatement();
			results = queryStatement.executeQuery("SELECT transaction_info_ID FROM transaction_info WHERE transaction_start_date = "+ startDate + " AND transaction_end_date = " + endDate);
			while (results.next()){
				transaction_set_ID = results.getInt("transaction_set_ID");
			}
			queryStatement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to retrieve transaction_info_ID");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		// attempt to execute query for transaction items
		Iterator<ItemSet> transactionIterator = transaction.iterator();
		while (transactionIterator.hasNext()){
			PreparedStatement transactionStatement = null;
			ItemSet next = transactionIterator.next();
			try {
				transactionStatement = connection.prepareStatement("INSERT INTO transactions(transaction_info_ID, transaction_count, transaction_items) VALUES(?,?,?)");
				transactionStatement.setInt(1, transaction_set_ID);
				transactionStatement.setDouble(2, next.count);
				transactionStatement.setString(3, next.items.toString());
				transactionStatement.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Unable to upload transaction items");
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}
		// clean up and disconnect
		disconnect(connection, stmt);

	} // end of uploadTransaction

	public void uploadAssociations(String startDate, HashSet<AssociationRule> associationSet){

		// convert association rule hashset into array
		Object[] associationArray = associationSet.toArray();

		// establish database connection
		Connection connection = connect();

		// initialize variables for query
		PreparedStatement stmt = null;

		for (int i = 0; i < associationArray.length; i++){
			// re-establishing variable types
			AssociationRule association = (AssociationRule) associationArray[i];
			ItemSet consequent = association.consequent;
			ItemSet antecedent = association.antecedent;
			double supportLevel = association.supportLevel;
			double confidenceLevel = association.confidenceLevel;

			// attempt to execute query
			try {
				stmt = connection.prepareStatement("INSERT INTO associations(support_level, confidence_level, consequent, antecedent, transaction_info_ID) VALUES(?,?,?,?,(SELECT transaction_info_ID FROM transaction_info WHERE transaction_start_date = ? LIMIT 1));");
				stmt.setFloat(1, (float) supportLevel);
				stmt.setFloat(2, (float) confidenceLevel);
				stmt.setString(3, consequent.toString());
				stmt.setString(4, antecedent.toString());
				stmt.setString(5, startDate);
				stmt.execute();
			} catch (SQLException e) {
				System.out.println("ERROR: Unable to upload associations");
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}

		// clean up and disconnect
		disconnect(connection, stmt);

	} // end of uploadAssociations

} // end of DataAccessObject class