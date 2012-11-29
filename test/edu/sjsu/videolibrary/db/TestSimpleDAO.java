package edu.sjsu.videolibrary.db;

import static org.mockito.Mockito.*;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

public class TestSimpleDAO extends TestCase {

	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		con = mock(Connection.class);
		stmt = mock(Statement.class);
		rs = mock(ResultSet.class);
		stub(con.createStatement()).toReturn(stmt);
		stub(con.prepareStatement(anyString())).toReturn(pstmt);
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

	private void setupConnection(VideoLibraryDAO dao) {
		try {
			Method m = VideoLibraryDAO.class.getDeclaredMethod("setConnection", java.sql.Connection.class);
			m.setAccessible(true);
			m.invoke(dao, con);
		} catch( Exception e ) {
			fail(e.getMessage());
		}
	}
}
