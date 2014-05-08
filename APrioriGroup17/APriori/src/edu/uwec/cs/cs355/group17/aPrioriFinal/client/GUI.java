package edu.uwec.cs.cs355.group17.aPrioriFinal.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.restlet.resource.ClientResource;

import edu.uwec.cs.cs355.group17.aPrioriFinal.server.AssociationRule;
import edu.uwec.cs.cs355.group17.aPrioriFinal.server.RequestResource;


@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener{
	JTextField support;
	JTextField confidence;
	JTextField inputFile;
	JTextField outputFile;
	JTextArea ruleArea;
	JScrollPane scrollPane;
	final JFileChooser fileChooser = new JFileChooser();

	public GUI() {
		
		//set background
		this.getContentPane().setBackground(Color.decode("#00CCFF"));
		
		// add title jLabel
		JLabel title = new JLabel("Welcome to PaulMart");
		title.setBounds(40, 10, 600, 60);
		title.setFont(new Font("Tahoma", Font.BOLD, 35));
		title.setForeground(Color.WHITE);
		this.add(title);
		
		//MSL & MCL labels
		JLabel supportLabel = new JLabel("Minimum Support Level:");
		supportLabel.setForeground(Color.WHITE);
		supportLabel.setBounds(75,55,140,50);
		//can't figure out how to set alignment
		supportLabel.setAlignmentX(RIGHT_ALIGNMENT);

		JLabel confidenceLabel = new JLabel("Minimum Confidence Level:");
		confidenceLabel.setAlignmentX(RIGHT_ALIGNMENT);
		confidenceLabel.setForeground(Color.WHITE);
		confidenceLabel.setBounds(75,85,140,50);
		
		this.add(supportLabel);
		this.add(confidenceLabel);
		
		//MSL & MCL text fields
		support = new JTextField(15);
		support.setBounds(225,70,150,20);
		confidence = new JTextField(15);
		confidence.setBounds(225,100,150,20);
		
		this.add(support);
		this.add(confidence);
		
		//Transaction Set label and text fields
		//label
		JLabel inputLabel = new JLabel("Input file path:");
		inputLabel.setAlignmentX(RIGHT_ALIGNMENT);
		inputLabel.setForeground(Color.WHITE);
		inputLabel.setBounds(75,115,140,50);
		this.add(inputLabel);
		
		//Selecting file with transaction data
		JButton chooseInput = new JButton("Browse");
		chooseInput.setActionCommand("chooseInput");
		chooseInput.addActionListener(this);
		chooseInput.setBounds(376,130,70,20);
		this.add(chooseInput);
		
		//Input File Text Field
		inputFile = new JTextField(15);
		inputFile.setEditable(false);
		inputFile.setBounds(225,130,150,20);
		this.add(inputFile);
		
		//Output path label and text field
		JLabel outputLabel = new JLabel("Output file path:");
		outputLabel.setAlignmentX(RIGHT_ALIGNMENT);
		outputLabel.setForeground(Color.WHITE);
		outputLabel.setBounds(75,145,140,50);
		this.add(outputLabel);
		
		//Output file selector
		JButton chooseOutput = new JButton("Browse");
		chooseOutput.setActionCommand("chooseOutput");
		chooseOutput.addActionListener(this);
		chooseOutput.setBounds(376,160,70,20);
		this.add(chooseOutput);
		
		//text field
		outputFile = new JTextField(15);
		outputFile.setEditable(false);
		outputFile.setBounds(225,160,150,20);
		
		this.add(outputFile);
		
		//submit button
		JButton submit = new JButton("Generate Rules");
		submit.setActionCommand("submit");
		submit.addActionListener(this);
		this.add(submit);
		submit.setBounds(75,205,350,30);


		
		// unnecessary fun
		JLabel jjl = new JLabel("powered by");
		jjl.setBounds(425, 240, 50, 10);
		jjl.setFont(new Font("Serif", Font.PLAIN, 7));
		this.add(jjl);
		
		JLabel jjl2 = new JLabel("JJL Technologies©");
		jjl2.setBounds(425, 250, 75, 10);
		jjl2.setFont(new Font("Serif", Font.PLAIN, 7));
		this.add(jjl2);		
		
		//rule area
		ruleArea = new JTextArea();
		ruleArea.setEditable(false);
		scrollPane = new JScrollPane(ruleArea);
		scrollPane.setBounds(515, 25, 450, 200);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
		
		//rule area clear button
		JButton clear = new JButton("Clear");
		clear.setActionCommand("clear");
		clear.addActionListener(this);
		this.add(clear);
		clear.setBounds(890,230,75,25);
			
		//finish setting up general layout
		this.setLayout(null);
		this.setTitle("PaulMart");
		this.setSize(1000, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
	} // end of GUI constructor
	
	public void display(ArrayList<String> badSets, HashSet<AssociationRule> associationRules, double time){
		
		// this method prints out the improperly formatted transactions,
		// large item sets, and association rules to both the client, and to 
		// the output text file.
		PrintWriter out;
		try {
			out = new PrintWriter(outputFile.getText() + ".txt");
			
			if (badSets.size() > 0){
				ruleArea.append("Improper Formatting on:\n");
				out.println("Improper Formatting on:");
				for (int i = 0; i < badSets.size(); i++){
					ruleArea.append("\t" + badSets.get(i) + "\n");
					out.println("\t" + badSets.get(i));
				}
			}
			
			if (!associationRules.isEmpty()){
				ruleArea.append("Association Rules:\n");
				out.println("Association Rules:");
				Iterator<AssociationRule> ruleIterator = associationRules.iterator();
				while(ruleIterator.hasNext()) {
					String rule = ruleIterator.next().toString();
					ruleArea.append("\t" + rule + "\n");
					out.println("\t" + rule);
				}
			} else {
				ruleArea.append("No Association Rules Found.\n");
				out.println("No Association Rules Found.");
			}
			
			ruleArea.append("Time: " + time + " msec\n\n");	
			out.println("Time: " + time + " msec");
			out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Output path not valid!", "Invalid filepath", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	} // end of display
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "chooseInput"){
			fileChooser.setCurrentDirectory(new File("."));
			int inputVal = fileChooser.showOpenDialog(this);
			if (inputVal == JFileChooser.APPROVE_OPTION) {
	            File inFile = fileChooser.getSelectedFile();
	            inputFile.setText(inFile.toString());
	        }
		}
		if (e.getActionCommand() == "chooseOutput"){
			fileChooser.setCurrentDirectory(new File("."));
			int outputVal = fileChooser.showOpenDialog(this);
			if (outputVal == JFileChooser.APPROVE_OPTION) {
	            File outFile = fileChooser.getSelectedFile();
	            outputFile.setText(outFile.toString());
	        }
		}
		if(e.getActionCommand() == "submit") {//mcl = 0.75 msl = 0.5 file path = H:/CS355/Java/newApriori/src/test.txt
			boolean file = true;
			boolean output = true;
			boolean minS = true;
			boolean minC = true;
			
			//-----------------------------------------------testing inputs-------------------------------------------
			//use this file path:   H:/CS355/Java/newApriori/src/test.txt
			@SuppressWarnings("unused")
			String transactionSet = "";
			try {
			if(!(inputFile.getText().equals(""))) {
				transactionSet = inputFile.getText();
			} else {
				file = false;
			}
			
			//make sure input is filled
			if(outputFile.getText().equals("")) {
				output = false;
			}
			
			//make sure input is filled
			double mcl = 0.0;
			if(!(confidence.getText().equals(""))) {
				mcl = (Double.parseDouble(confidence.getText()));
			} else {
				minC = false;
			}
			
			//make sure input is filled
			double msl = 0.0;
			if(!(support.getText().equals(""))) {
				msl = (Double.parseDouble(support.getText()));
			} else {
				minS = false;
			}
			
			//make sure inputs are within range
			if(msl > 1.0 || msl < 0) {
				minS = false;
			}
			if(mcl > 1.0 || mcl < 0) {
				minC = false;
			}
			
			if(minS && minC && file && output) {
				// requesting data processing from RESTlet
				request();
			} else if(!minS) {
				JOptionPane.showMessageDialog(null, "Please enter a valid support level [0 - 1.0]", "Invalid", JOptionPane.ERROR_MESSAGE);
			} else if(!minC) {
				JOptionPane.showMessageDialog(null, "Please enter a valid confidence level [0 - 1.0]", "Invalid", JOptionPane.ERROR_MESSAGE);
			} else if(!file) {
				JOptionPane.showMessageDialog(null, "Please enter a valid filepath", "Invalid filepath", JOptionPane.ERROR_MESSAGE);
			}
			
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Please enter a valid filepath", "Invalid filepath", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
		} else if(e.getActionCommand() == "clear") {
			ruleArea.setText("");
		}
	} // end of actionPerformed
	
    public void request(){
    	
		// Creating Request
		Request request = new Request(Double.parseDouble(confidence.getText()), Double.parseDouble(support.getText()), inputFile.getText());
		
		// Storing and Retrieving
		ClientResource clientResource = new ClientResource("http://localhost:8111/");
		RequestResource proxy =	clientResource.wrap(RequestResource.class);
		Request retrieved = null;
		proxy.store(request);
		
		// created retrieved request
		retrieved = proxy.retrieve();
		
		// displaying bad sets and rules
		ArrayList<String> badSets = new ArrayList<String>();
		badSets = retrieved.getBadSets();
		HashSet<AssociationRule> associationRules = new HashSet<AssociationRule>();
		associationRules = retrieved.getAssociationRules();
		display(badSets, associationRules, retrieved.getTime());
		
    } // end of request

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unused")
		GUI gui = new GUI();
	} // end of main
	
} // end of GUI class
