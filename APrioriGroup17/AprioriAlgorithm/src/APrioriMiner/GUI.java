package APrioriMiner;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class GUI extends JFrame implements ActionListener{
	JTextField support;
	JTextField confidence;
	JTextField transFile;
	JFileChooser fc;

	public GUI() {
		
		//set background
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(
					"blue.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// add title jLabel
		JLabel title = new JLabel("Welcome to PaulMart");
		title.setBounds(40, 10, 600, 60);
		title.setFont(new Font("Tahoma", Font.BOLD, 35));
		title.setForeground(Color.BLACK);
		this.add(title);
		
		//MSL & MCL labels
		JLabel supportLabel = new JLabel("Min Support Level:");
		supportLabel.setForeground(Color.BLACK);
		supportLabel.setBounds(75,55,140,50);
		//can't figure out how to set alignment
		supportLabel.setAlignmentX(RIGHT_ALIGNMENT);

		JLabel confidenceLabel = new JLabel("Min Confidence Level:");
		confidenceLabel.setAlignmentX(RIGHT_ALIGNMENT);
		confidenceLabel.setForeground(Color.BLACK);
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
		JLabel transLabel = new JLabel("Trasaction file path:");
		transLabel.setAlignmentX(RIGHT_ALIGNMENT);
		transLabel.setForeground(Color.BLACK);
		transLabel.setBounds(75,115,140,50);
		
		this.add(transLabel);
		
		//textfield
		transFile = new JTextField(15);
		transFile.setBounds(225,130,150,20);
		
		this.add(transFile);
		
		//submit button
		JButton submit = new JButton("Generate Rules");
		submit.setActionCommand("submit");
		submit.addActionListener(this);
		this.add(submit);
		submit.setBounds(75,175,350,30);
		
		/*-----------------------------------------------------
		//Selecting file with transaction data
		fc = new JFileChooser();//file browser
		
		JTextField inputFile = new JTextField(50);
		inputFile.setEditable(false);
		this.add(inputFile);
		
		JButton button = new JButton("Browse");
		button.addActionListener(this);
		this.add(button);
		--------------------------------------------------------*/
		
		
		
		
		//-----------just for fun----------------------------------
//		ImagePanel firework = new ImagePanel("firework.png");
//		firework.setBounds(425, 190, 50, 50);
//		this.add(firework);
		
		JLabel jjl = new JLabel("powered by");
		jjl.setBounds(425, 240, 50, 10);
		jjl.setFont(new Font("Serif", Font.PLAIN, 7));
		this.add(jjl);
		
		JLabel jjl2 = new JLabel("JJL Technologies�");
		jjl2.setBounds(425, 250, 75, 10);
		jjl2.setFont(new Font("Serif", Font.PLAIN, 7));
		this.add(jjl2);
		//---------------------------------------------------------
		
		
		//finish setting up general layout
		this.setLayout(null);
		this.setTitle("PaulMart");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		//this.setResizable(false); //we will want this for final implementation
		
	}
	
	public void display(APrioriAlgorithm apa, double time){
		JOptionPane.showMessageDialog(null, "Large Item Sets: " + apa.itemSubsets.size() + "\n" + apa.itemSubsets + "\n" + "Association Rules: " + apa.associationRules.size() + "\n" + apa.associationRules + "\nTime: " + time + " msec", "Result", JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
//		//This is some of the stuff for the file browser which I don't know how to implement
//		if (e.getSource() == openButton) {
//	          int returnVal = fc.showOpenDialog(FileChooserDemo.this);
//
//	          if (returnVal == JFileChooser.APPROVE_OPTION) {
//	              File file = fc.getSelectedFile();
//	              //This is where a real application would open the file.
//	              log.append("Opening: " + file.getName() + "." + newline);
//	          } else {
//	              log.append("Open command cancelled by user." + newline);
//	          }
//	          log.setCaretPosition(log.getDocument().getLength());
//		}
//		
		
		if(e.getActionCommand() == "submit") {//mcl = 0.75 msl = 0.5 file path = H:/CS355/Java/newApriori/src/test.txt
			boolean file = true;
			boolean minS = true;
			boolean minC = true;
			
			//-----------------------------------------------testing inputs-------------------------------------------
			//use this file path:   H:/CS355/Java/newApriori/src/test.txt
			String transactionSet = "";
			try {
			if(!(transFile.getText().equals(""))) {
				transactionSet = transFile.getText();
			} else {
				file = false;
			}
			
			double mcl = 0.0;
			if(!(confidence.getText().equals(""))) {
				mcl = (Double.parseDouble(confidence.getText()));
			} else {
				minC = false;
			}
			
			double msl = 0.0;
			if(!(support.getText().equals(""))) {
				msl = (Double.parseDouble(support.getText()));
			} else {
				minS = false;
			}
			
			if(msl > 1.0 || msl < 0) {
				minS = false;
			}
			if(mcl > 1.0 || mcl < 0) {
				minC = false;
			}
			//create and start timer
			double time;
			Timer timer = new Timer();
			timer.startTimer();
			if(minS && minC && file) {
				//----------------------------------------------------starting generator -------------------------------------------
				@SuppressWarnings("unused")
				APrioriAlgorithm generator = new APrioriAlgorithm(msl, mcl, transactionSet);
				timer.stopTimer();
				time = timer.getTotal();
				//stop timer and print out the elapsed time
				Parser parser = new Parser(transactionSet);
				DataAccessObject dao = new DataAccessObject();
				dao.uploadSettings(generator.minSupportLevel, generator.minConfidenceLevel);
				dao.uploadTransaction("PaulMart", parser.getStartDate(), parser.getEndDate(), generator.transactions);
				dao.uploadAssociations(parser.getStartDate(), generator.associationRules);
				dao.getSettings();
				System.out.println("Elapsed Time: " + time + " msec");
				this.display(generator, time);
			} else if(!minS) {
				JOptionPane.showMessageDialog(null, "Please enter a valid support level!", "Invalid", JOptionPane.ERROR_MESSAGE);
			} else if(!minC) {
				JOptionPane.showMessageDialog(null, "Please enter a valid confidence level!", "Invalid", JOptionPane.ERROR_MESSAGE);
			} else if(!file) {
				JOptionPane.showMessageDialog(null, "Please enter a valid filepath!", "Invalid filepath", JOptionPane.ERROR_MESSAGE);
			}
			
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Please enter a valid filepath!", "Invalid filepath", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
			
			
			
		
			
			
			
			
			
		}
	}

}
