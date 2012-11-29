package edu.sjsu.videolibrary.db;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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
import java.util.UUID;

import junit.framework.TestCase;

public class TestSimpleCartDAO extends TestCase {
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
	public void testAddToCart() throws Exception {
		String transactionId = UUID.randomUUID().toString();;
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);


		try {
			assertEquals(true,dao.addToCart(1,1));
			
		//	verify(stmt).executeQuery(eq("SELECT membershipId,firstName,lastName,address,city,ccNumber,membershipType,state,zipCode,startDate,latestPaymentDate,userId, password FROM user WHERE userId = 'userId' AND password = '5f4dcc3b5aa765d61d8327deb882cf99'"));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
/*
	@Test
	public void testDeleteFromCart() {
		fail("Not yet implemented");
	}

	@Test
	public void testListCartItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecordPaymentTransaction() {
		fail("Not yet implemented");
	}

	@Test
	public void testRecordMovieTransaction() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateReturnDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCart() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimpleCartDAO() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMovieById() {
		fail("Not yet implemented");
	}
*/

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
