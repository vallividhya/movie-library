package edu.sjsu.videolibrary.test;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;

import edu.sjsu.videolibrary.db.VideoLibraryDAO;

import junit.framework.TestCase;


public abstract class BaseTestCase extends TestCase {
	
	protected Connection con = null;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;
	
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
	
	protected void setupConnection(VideoLibraryDAO dao) {
		try {
			Method m = VideoLibraryDAO.class.getDeclaredMethod("setConnection", java.sql.Connection.class);
			m.setAccessible(true);
			m.invoke(dao, con);
			Method m2 = VideoLibraryDAO.class.getDeclaredMethod("setStatement", java.sql.Statement.class);
			m2.setAccessible(true);
			m2.invoke(dao, stmt);
		} catch( Exception e ) {
			fail(e.getMessage());
		}
	}

}
