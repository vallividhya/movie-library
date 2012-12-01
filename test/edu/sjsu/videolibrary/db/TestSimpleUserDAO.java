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

public class TestSimpleUserDAO extends BaseTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testSignInUserWrongInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);


		try {
			User u = dao.signInUser("userId", "password");
			assertNull(u);
		//	verify(stmt).executeQuery(eq("SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = 'userId' AND password = '5f4dcc3b5aa765d61d8327deb882cf99'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignInUserCorrectInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		stub(rs.getInt(anyString())).toReturn(1);
		stub(rs.getString(anyString())).toReturn("fname").toReturn("lname").toReturn("address").toReturn("city").toReturn("ccNum").toReturn("memType").toReturn("state").toReturn("zip").toReturn("uId").toReturn("pwd");
		java.sql.Date date = new java.sql.Date(0);
		stub(rs.getDate(anyString())).toReturn(date).toReturn(date);

		try {
			User u = dao.signInUser("userId", "password");
			assertNotNull(u);
			assertEquals(1, u.getMembershipId());
			assertEquals("fname", u.getFirstName());
			assertEquals("lname", u.getLastName());
			assertEquals("address",u.getAddress());
			assertEquals("city", u.getCity());
			assertEquals("state", u.getState());
			assertEquals("ccNum",u.getCreditCardNumber());
			assertEquals("memType",u.getMembershipType());
			assertEquals("zip", u.getZip());
			assertEquals("uId", u.getUserId());
			assertEquals("pwd", u.getPassword());
			assertEquals(date.toString(), u.getStartDate());
			assertEquals(null, u.getLatestPaymentDate());
			//verify(stmt).executeQuery(eq("SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = 'userId' AND password = '5f4dcc3b5aa765d61d8327deb882cf99'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testSignInUserEmptyStringInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);


		try {
			User u = dao.signInUser("","");
			assertNull(u);
		//	verify(stmt).executeQuery(eq( "SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = '' AND password = 'null'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignInUserNullInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);


		try {
			User u = dao.signInUser(null,null);
			assertNull(u);
			verify(stmt).executeQuery(eq( "SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = 'null' AND password = 'null'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignInUserThrowSQLException() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toThrow(new SQLException(""));

		try {
			dao.signInUser("userId", "password");
			fail("Exception not thrown");
		} catch(SQLException e) {
		}
	}

	@Test

	public void testSignUpUserNullInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toReturn(0);

		try {
			String result = dao.signUpUser(null, null,"memType", "firstName", "lastName", "address", "city", "st", "zipCo", "ccNumber");
			assertEquals("false", result);
			verify(stmt).executeUpdate(eq("INSERT INTO user (userId,password,membershipType,startDate,firstName,lastName,address,city,state,zip,creditCardNumber,latestPaymentDate) VALUES ('null','null','memType',NOW(),'firstName','lastName','address','city','st','zipCo','ccNumber',null)"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testSignUpUserCorrectInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toReturn(1);

		try {
			String result = dao.signUpUser("ab@ab.com", "ab","memType", "firstName", "lastName", "address", "city", "st", "zipCo", "ccNumber");
			assertEquals("true", result);
			verify(stmt).executeUpdate("INSERT INTO user (userId,password,membershipType,startDate,firstName,lastName,address,city,state,zip,creditCardNumber,latestPaymentDate) VALUES ('ab@ab.com','187ef4436122d1cc2f40dc2b92f0eba0','memType',NOW(),'firstName','lastName','address','city','st','zipCo','ccNumber',null)");

		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignUpUserThrowSQLException() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException(""));

		try {
			dao.signUpUser("userId", "password", "memType", "firstName", "lastName", "address", "city", "state", "zipCode", "ccNumber");
			fail("Exception not thrown");
		} catch(SQLException e) {
		}
	}
	@Test 
	public void testUpdatePasswordWrongInput() throws Exception{
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toReturn(0);

		try {
			assertEquals("invalidOldPassword",dao.updatePassword(1, "oldPassword", "newPassword"));
		} catch(Exception e) {
			fail("Exception Thrown");
		}
	}

	@Test 
	public void testUpdatePasswordNullInput() throws Exception{
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toReturn(0);

		try {
			assertEquals("invalidOldPassword",dao.updatePassword(1, "oldPassword", "newPassword"));
		} catch(Exception e) {
			fail("Exception Thrown");
		}
	}

	@Test 
	public void testUpdatePasswordEmptyStringInput() throws Exception{
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toReturn(0);

		try {
			assertEquals("invalidOldPassword",dao.updatePassword(1, "oldPassword", "newPassword"));
		} catch(Exception e) {
			fail("Exception Thrown");
		}
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

	@Test
	public void testViewAccountThrowSQLException() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toThrow(new SQLException(""));

		try {
			dao.viewAccountTransactions(1);
			fail("Exception not thrown");
		} catch(SQLException e) {
		}
	}
	
	
	@Test

	public void testMViewStatementTransactionCorrectInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true).toReturn(true).toReturn(false);
		stub(rs.getDouble(anyString())).toReturn(0.0);
		stub(rs.getString(anyString())).toReturn("movieName");
		java.sql.Date date = new java.sql.Date(0);
		stub(rs.getDate(anyString())).toReturn(date).toReturn(date);

		try {
			LinkedList<StatementInfo> s = dao.viewStatement(1, 1, 1);
			assertNotNull(s);	
			assertEquals(1, s.size());
			assertEquals("movieName", s.get(0).getMovieName());
			assertEquals(0.0,s.get(0).getTotalDueAmount());
			assertEquals(date.toString(), s.get(0).getRentDate());
			assertEquals(date.toString(), s.get(0).getReturnDate());
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testViewStatementThrowSQLException() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toThrow(new SQLException(""));

		try {
			dao.viewStatement(1, 1, 2012);
			fail("Exception not thrown");
		} catch(SQLException e) {
		}
	}

}
