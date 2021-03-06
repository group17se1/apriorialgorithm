package edu.uwec.cs.cs355.group17.APrioriMiner;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;



public class DAOTests {


	@Test
	public void testDAOConstructor(){
		DataAccessObject dao = new DataAccessObject();
		assertNotNull(dao);
	}
	
	@Test
	public void testDAOGettersAndSetters(){
		//testing getters and setters
		DataAccessObject dao = new DataAccessObject();
        
        dao.setSupportLevel(0.4);
		assertNotNull(dao.getSupportLevel());
		assertNotEquals(dao.getSupportLevel(), 0.6);
		
		
		dao.setConfidenceLevel(0.7);
        assertNotNull(dao.getConfidenceLevel());
        assertNotEquals(dao.getConfidenceLevel(), 0.8);
		
	}
	
	@Test
	public void testDAOconnect(){
		
		DataAccessObject dao = new DataAccessObject();
		Connection connection = (Connection) dao.connect();
		assertNotNull(connection);
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
