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

public class TestSimpleUserDAO3 extends BaseTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
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