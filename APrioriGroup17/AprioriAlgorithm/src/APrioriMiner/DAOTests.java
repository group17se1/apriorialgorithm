package APrioriMiner;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;



public class DAOTests {


	@Test
	public void testDAOConstructor(){
		DataAccessObject dao = new DataAccessObject();
		assertNotNull(dao);
		assertNotNull(dao.getDate());
	}
	
	@Test
	public void testDAOGettersAndSetters(){
		//testing getters and setters
		DataAccessObject dao = new DataAccessObject();
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("mmddyyyy").parse("04152015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date date = new java.sql.Date(date1.getTime());
        
		dao.setDate(date);
        assertEquals(dao.getDate(), date);
        
        dao.setSupportLevel(0.4);
		assertNotNull(dao.getSupportLevel());
		assertNotEquals(dao.getSupportLevel(), 0.6);
		
		
		dao.setConfidenceLevel(0.7);
        assertNotNull(dao.getConfidenceLevel());
        assertNotEquals(dao.getConfidenceLevel(), 0.8);
		
	}
	
	@Test
	public void testDAOconnect(){
		
		
		fail("not implemented yet");
		
	}
	
	@Test
	public void testDAOupdateSettings(){
		fail("not implemented yet");
//		dao.uploadSettings(supportLevel, confidenceLevel);
//		dao.updateSettings(supportLevel + 0.1, confidenceLevel + 0.1);
//		assertNotNull(dao.getConfidenceLevel());
//		assertNotNull(dao.getSupportLevel());
//		System.out.println("******************" + dao.getConfidenceLevel());
//		assertEquals(confidenceLevel + 0.1 , dao.getConfidenceLevel(), 0);
		
	}
	
	@Test
	public void testDAOuploadTransaction(){
//		dao.uploadTransaction("PaulMart", parser.getStartDate(), parser.getEndDate(), generator.transactions);
	}
	
	@Test
	public void testDAOuploadAssociations(){
		fail("not implemented yet");
		/*
		DataAccessObject dao = new DataAccessObject();
		double supportLevel = 2;
		double minSL = 0.4;
		double confidenceLevel = 0.7;
		String filepath = "src/testcase4";
		APrioriAlgorithm generator = new APrioriAlgorithm(minSL, confidenceLevel, filepath);
		Parser parser = new Parser(filepath);
*/
//		dao.uploadAssociations(parser.getStartDate(), generator.associationRules);
	}
	
	@Test
	public void testDAOreadEntry(){
		fail("not implemented yet");
		
	}
}
