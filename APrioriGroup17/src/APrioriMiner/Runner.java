package APrioriMiner;

public class Runner {

	public static void main(String[] args) {
	
		String transactionSet = "test.txt";
		
		// parameters for APrioriAlgorithm are: (support, confidence, filepath)
		
		Timer timer = new Timer();
		timer.startTimer();
		@SuppressWarnings("unused")
		APrioriAlgorithm generator = new APrioriAlgorithm(0.5, 0.75, transactionSet);
		timer.stopTimer();
		System.out.println("Elapsed Time: " + timer.getTotal() + " msec");

	}

}