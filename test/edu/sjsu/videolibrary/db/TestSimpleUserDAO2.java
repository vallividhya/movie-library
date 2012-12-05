package edu.sjsu.videolibrary.db;

import static org.mockito.Mockito.*;

import org.apache.naming.java.javaURLContextFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.D2F;

import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

public class TestSimpleUserDAO2 extends BaseTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}
		@Test 
		public void testUpdatePasswordCorrectInput() throws Exception{
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toReturn(1);
	
			try {
				assertEquals("true",dao.updatePassword(1, "oldPassword", "newPassword"));
			} catch(Exception e) {
				fail("Exception Thrown");
			}
		}
	
		@Test
		public void testUpdatePasswordThrowSQLException() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toThrow(new SQLException(""));
	
			try {
				dao.updatePassword(1, "oldPassword", "newPassword");
				fail("Exception not thrown");
			} catch(SQLException e) {
			}
		}

		@Test
	
		public void testUpdateUserInfoWrongInput() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toReturn(0);
	
			try {
				String result = dao.updateUserInfo(1, "userId", "firstName", "lastName", "address", "city", "state", "zipCode", "membershipType", "creditCardNumber");
				assertEquals("false", result);			
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
	
		@Test	
		public void testUpdateUserInfoNullInput() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toReturn(0);
	
			try {
				String result = dao.updateUserInfo(1, "userId", "null", "null", "address", "city", "state", "zipCode", "membershipType", "creditCardNumber");
				assertEquals("false", result);			
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
		
		@Test
	
		public void testUpdateUserInfoEmptyStringInput() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toReturn(0);
	
			try {
				String result = dao.updateUserInfo(1, "", "", "", "address", "city", "state", "zipCode", "membershipType", "creditCardNumber");
				assertEquals("false", result);			
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
		
		@Test
	
		public void testUpdateUserInfoCorrectInput() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toReturn(1);
	
			try {
				String result = dao.updateUserInfo(1, "userId", "firstName", "lastName", "address", "city", "state", "zipCode", "membershipType", "creditCardNumber");
				assertEquals("true", result);			
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
		@Test
		public void testUpdateUserInfoThrowSQLException() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toThrow(new SQLException(""));
	
			try {
				dao.updateUserInfo(1, "userId", "firstName", "lastName", "address", "city", "state", "zipCode", "membershipType", "creditCardNumber");
				fail("Exception not thrown");
			} catch(SQLException e) {
			}
		}
		
		@Test
	
		public void testMakeMonthlyPaymentCorrectInput() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toReturn(1);
	
			try {
				String result = dao.makeMonthlyPayment(1);
				assertEquals("true", result);			
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
	
		@Test
		public void testMakeMonthlyPaymentThrowSQLException() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeUpdate(anyString())).toThrow(new SQLException(""));
	
			try {
				dao.makeMonthlyPayment(1);
				fail("Exception not thrown");
			} catch(SQLException e) {
			}
		}
		
		
		@Test
	
		public void testMViewAccountTransactionCorrectInput() throws Exception {
			SimpleUserDAO dao = new SimpleUserDAO();
			setupConnection(dao);
	
			stub(stmt.executeQuery(anyString())).toReturn(rs);
			stub(rs.next()).toReturn(true).toReturn(true).toReturn(false);
			stub(rs.getDouble(anyString())).toReturn(0.0);
			stub(rs.getString(anyString())).toReturn("movieName");
			java.sql.Date date = new java.sql.Date(0);
			stub(rs.getDate(anyString())).toReturn(date).toReturn(date);
	
			try {
				LinkedList<Transaction> trans = dao.viewAccountTransactions(1);
				assertNotNull(trans);	
				assertEquals(2, trans.size());
				assertEquals("movieName", trans.get(0).getMovieName());
				assertEquals(0.0,trans.get(0).getPerMovieAmount());
				assertEquals(date.toString(), trans.get(0).getPurchaseDate());
				assertEquals(date.toString(), trans.get(0).getReturnDate());
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}		  
	


	}