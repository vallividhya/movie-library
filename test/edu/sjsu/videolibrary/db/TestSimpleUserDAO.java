package edu.sjsu.videolibrary.db;

import static org.mockito.Mockito.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;
import java.sql.SQLException;

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
			verify(stmt).executeQuery(eq("SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = 'userId' AND password = '5f4dcc3b5aa765d61d8327deb882cf99'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	public void testSignInUserEmptyStringInput() throws Exception {
		SimpleUserDAO dao = new SimpleUserDAO();
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);


		try {
			User u = dao.signInUser("","");
			assertNull(u);
			verify(stmt).executeQuery(eq( "SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = '' AND password = 'null'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
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
}
