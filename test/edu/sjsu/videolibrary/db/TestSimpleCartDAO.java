package edu.sjsu.videolibrary.db;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
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
	public void testAddToCartCorrectInput() throws Exception {
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

	@Test
	public void testDeleteFromCartCorrectInput() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		stub(rs.isBeforeFirst()).toReturn(false);
		stub(stmt.executeUpdate(anyString())).toReturn(1);


		try {
			dao.deleteFromCart(1, 1);		

		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteFromCartThrowException() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		stub(rs.isBeforeFirst()).toReturn(false);
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		try {
			dao.deleteFromCart(1, 1);	
			fail("Exception not thrown");

		} catch(InternalServerException e) {

		}
		catch(Exception e){
			fail("Wrong exception catched.");
		}
	}

	@Test
	public void testListCartItemsCorrectInput() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true).toReturn(true).toReturn(false);
		stub(rs.getInt(anyString())).toReturn(1);
		stub(rs.getDouble(anyString())).toReturn(0.0);
		stub(rs.getString(anyString())).toReturn("movieName").toReturn("movieBanner");
		try {
			List<ItemOnCart> list = dao.listCartItems(1);
			assertEquals(2, list.size());
			assertEquals(1, list.get(0).getMovieId());
			assertEquals("movieName", list.get(0).getMovieName());
			assertEquals("movieBanner", list.get(0).getMovieBanner());
			assertEquals(0.0,list.get(0).getRentAmount());

		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testListCartItemsWrongInput() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);

		try {
			List<ItemOnCart> list = dao.listCartItems(1);
			assertEquals(0, list.size());
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testListCartItemsThrowException() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		stub(rs.next()).toReturn(false);

		try {
			List<ItemOnCart> list = dao.listCartItems(1);
			fail("Exception not thrown");
		} catch(Exception e) {

		}
	}

//	@Test
//	public void testRecordPaymentTransactionCorrectInput() throws SQLException {
//		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
//		setupConnection(dao);
//		stub(stmt.executeUpdate(anyString())).toReturn(1);
//		stub(rs.next()).toReturn(true).toReturn(true).toReturn(false);
//
//		try {
//			int i = dao.recordPaymentTransaction(1.0, 1);
//			assertNotNull(i);
//		} catch(Exception e) {
//			fail(e.getMessage());
//		}
//	}
	
	@Test
	public void testRecordPaymentTransactionThrowException() throws SQLException,InternalServerException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		stub(rs.next()).toReturn(true).toReturn(false);

		try {
			 dao.recordPaymentTransaction(-1.0, 1);
			 fail("Exception  not thrown");
		
		}
		catch(InternalServerException e){
			
		}
		catch(Exception e) {
			
		}
	}
	
	@Test
	public void testRecordMovieTransactionThrowException() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		stub(rs.next()).toReturn(true);
		
		try {
			 dao.recordMovieTransaction(1, 1);
			fail("Exception not thrown");
		} catch(Exception e) {
			
		}		
	}
	
	@Test
	public void testRecordMovieTransactionCorrectInput() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try {
			 dao.recordMovieTransaction(1, 1);			
		} catch(Exception e) {
			
		}		
	}

	@Test
	public void testUpdateReturnDateCorrectInput() throws SQLException {
	
			SimpleCartDAO dao = new SimpleCartDAO(transactionId);
			setupConnection(dao);
			stub(stmt.executeUpdate(anyString())).toReturn(1);			
			try {
				 dao.updateReturnDate(1, 1);	
			} catch(Exception e) {
				
			}
	}
	
	@Test
	public void testUpdateReturnDateThrowsException() throws SQLException {
	
			SimpleCartDAO dao = new SimpleCartDAO(transactionId);
			setupConnection(dao);
			stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());		
			try {
				 dao.updateReturnDate(1, 1);	
				 fail("Exception not thrown");
			} catch(Exception e) {
				
			}
	}
	
	@Test
	public void testDeleteCartCorrectInput() throws SQLException {
		
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toReturn(1);			
		try {
			 dao.deleteCart(1);
		} catch(Exception e) {
			
		}
		
	}
	
	@Test
	public void testDeleteCartThrowException() throws SQLException {
		
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());		
		try {
			 dao.deleteCart(1);
			 fail("Exception not thrown");
		} catch(Exception e) {
			
		}
		
	}	

	@Test
	public void testGetMovieByIdCorrectInput() throws SQLException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true).toReturn(false);
		stub(rs.isBeforeFirst()).toReturn(true);
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			ItemOnCart itm = dao.getMovieById(1, 1);	
			assertNotNull(itm);
		}
		catch(Exception e){
			fail(e.getMessage());
		}		
		
	}
	
	@Test
	public void testGetMovieByIdWrongInputThrowException() throws SQLException, InternalServerException {
		SimpleCartDAO dao = new SimpleCartDAO(transactionId);
		setupConnection(dao);

		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		stub(rs.isBeforeFirst()).toReturn(false);
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		
		try{
			dao.getMovieById(-1, 1);	
			fail("Exception not thrown");
		}
		catch(NoMovieFoundException e){
			
		}
		catch(Exception e){
			
		}
		
		
	}
	 
}
