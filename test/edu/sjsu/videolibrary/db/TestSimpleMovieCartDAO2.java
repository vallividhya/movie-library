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

public class TestSimpleMovieCartDAO2 extends BaseTestCase {

	SimpleCartDAO dao = null;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		dao = new SimpleCartDAO();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testListCartItemsThrowException() throws SQLException {
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
//	public void testRecordPaymentTransactionThrowException() throws SQLException,InternalServerException {
//		setupConnection(dao);
//		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
//		stub(rs.next()).toReturn(true).toReturn(false);
//
//		try {
//			 dao.recordPaymentTransaction(-1.0, 1);
//			 fail("Exception  not thrown");
//		
//		}
//		catch(InternalServerException e){
//			
//		}
//		catch(Exception e) {
//			
//		}
//	}
//	
	@Test
	public void testRecordMovieTransactionThrowException() throws SQLException {
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		stub(rs.next()).toReturn(true);
		
		try {
			 dao.recordMovieTransaction(1, 1, 0);
			fail("Exception not thrown");
		} catch(Exception e) {
			
		}		
	}
	
	@Test
	public void testRecordMovieTransactionCorrectInput() throws SQLException {
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try {
			 dao.recordMovieTransaction(1, 1, 0);			
		} catch(Exception e) {
			
		}		
	}

	@Test
	public void testUpdateReturnDateCorrectInput() throws SQLException {	
			setupConnection(dao);
			stub(stmt.executeUpdate(anyString())).toReturn(1);			
			try {
				 dao.updateReturnDate(1, 1);	
			} catch(Exception e) {
				
			}
	}
	
	@Test
	public void testUpdateReturnDateThrowsException() throws SQLException {
	
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
		
		setupConnection(dao);
		stub(stmt.executeUpdate(anyString())).toReturn(1);			
		try {
			 dao.deleteCart(1);
		} catch(Exception e) {
			
		}
		
	}
	
	@Test
	public void testDeleteCartThrowException() throws SQLException {
		
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
