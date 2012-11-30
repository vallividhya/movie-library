package edu.sjsu.videolibrary.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSimpleMovieDAO {
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int rc ;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		super.setUp();
		con = mock(Connection.class);
		stmt = mock(Statement.class);
		rs = mock(ResultSet.class);
		stub(con.createStatement()).toReturn(stmt);
		stub(con.prepareStatement(anyString())).toReturn(pstmt);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		super.tearDown();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestCreateNewMovie() {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(rc);
		stub(rc>0).toReturn(false);
		try{
			assertEquals(true,dao.createNewMovie(aa,bb,cc,dd,ff));
		}catch(Exception e){
			fail(e.getMessage);
		}
	}
	
	/*@Test
	public void nullValueTestCreateNewMovie() {
		fail("Not yet implemented");
	}
	
	@Test
	public void correctValueTestCreateNewMovie() {
		fail("Not yet implemented");
	}
	
	@Test
	public void sqlExceptionTestCreateNewMovie() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteMovie() {
		fail("Not yet implemented");
	}

	@Test
	public void testReturnMovie() {
		fail("Not yet implemented");
	}

	@Test
	public void testListCategories() {
		fail("Not yet implemented");
	}

	@Test
	public void testListMoviesByCategoryStringIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testListAllMovies() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCopiesCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAvailableCopies() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchMovie() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimpleMovieDAO() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimpleMovieDAOString() {
		fail("Not yet implemented");
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
*/

}
