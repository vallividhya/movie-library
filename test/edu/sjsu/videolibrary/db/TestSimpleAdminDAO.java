package edu.sjsu.videolibrary.db;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class TestSimpleAdminDAO extends BaseTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}
//
	@Test
	public void testDisplayUserInformationWrongInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			User u = dao.displayUserInformation(19);
			assertNull(u);
		}catch(Exception e){
			e.getMessage();
		}
	}	

	
	@Test
	public void testDisplayUserInformationSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		try{
			User u = dao.displayUserInformation(1231243);
			assertNull(u);
		}catch(Exception e){
			e.getMessage();
		}
	}

	
	@Test
	public final void testDisplayMovieInformationWrongInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			Movie m=dao.displayMovieInformation(123);
			assertNull(m);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testDisplayMovieInformationSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
				
		try{
			Movie m=dao.displayMovieInformation(123);
			assertNull(m);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

//	@Test
//	public void testGetRentAmountforMovieValidOutput() throws Exception{
//		SimpleAdminDAO dao = new SimpleAdminDAO();
//		setupConnection(dao); 
//		
//		stub(stmt.executeQuery(anyString())).toReturn(rs);
//		stub(rs.next()).toReturn(true);
//		
//		try{
//			Double rentAmount = dao.getRentAmountforMovie();
//			assertEquals(1.5,rentAmount);
//		}catch(Exception e){
//			fail(e.getMessage());
//		}
//	}
	
	@Test
	public void testGetRentAmountforMovieInValidOutput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			Double rentAmount = dao.getRentAmountforMovie();
			assertEquals(0.0,rentAmount);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetRentAmountforMovieSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		
		try{
			Double rentAmount = dao.getRentAmountforMovie();
			assertEquals(-1.0,rentAmount);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}


	
	@Test
	public void testGetMonthlyFeesForPremiumMemberInValidOutput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		try{
			Double monthlyFees = dao.getMonthlyFeesForPremiumMember();
			assertEquals(0.0,monthlyFees);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetMonthlyFeesForPremiumMemberSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		try{
			Double monthlyFees = dao.getMonthlyFeesForPremiumMember();
			assertEquals(-1.0,monthlyFees);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testUpdateMovieInfoCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			String result = dao.updateMovieInfo(1,"movieName","movieBanner","releaseDate",10,3);
			assertEquals("true",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testGenerateMonthlyBillForPremiumMemberSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		
		try{
			PaymentForPremiumMemInfo pymnt = dao.generateMonthlyBillForPremiumMember(0, 0, 0);
			assertNull(pymnt);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}

}

