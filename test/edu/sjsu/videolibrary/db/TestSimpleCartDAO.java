package edu.sjsu.videolibrary.db;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import junit.framework.TestCase;

public class TestSimpleCartDAO extends BaseTestCase {
	
	String transactionId = null;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		 transactionId = TransactionManager.INSTANCE.startTransaction();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		TransactionManager.INSTANCE.rollbackTransaction(transactionId);
	}


	@Test
	public void testAddToCart() throws Exception {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		stub(rs.isBeforeFirst()).toReturn(false);
		stub(stmt.executeUpdate(anyString())).toReturn(0);


		try {
			assertEquals("true",dao.addToCart(1,1));
			
			verify(stmt).executeQuery(eq("SELECT movieId, membershipId FROM moviecart WHERE movieId = 1 AND membershipId = 1"));
			verify(stmt).executeUpdate(eq("INSERT INTO videolibrary.moviecart (movieId, membershipId) VALUES (1, 1)"));
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
}
