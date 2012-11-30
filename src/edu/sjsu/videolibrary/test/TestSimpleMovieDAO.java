package edu.sjsu.videolibrary.test;


import java.lang.reflect.Method;

import static org.mockito.Matchers.anyString;

import edu.sjsu.videolibrary.db.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSimpleMovieDAO extends BaseTestCase {
	
	String result = null;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();		
	}
	
	@Test
	public final void testCreateNewMovieWrongInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		Integer movieID=null;
		stmt.executeUpdate(anyString());
		rs = stmt.getGeneratedKeys();
		if(rs.next())
			 movieID = rs.getInt(1);
			result = movieID.toString();
		
		try{
			String result = dao.createNewMovie("aa", "bb", "2012", 1, 1);
			assertEquals("", result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

//	@Test
//	public final void testDeleteMovie() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testReturnMovie() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testListCategories() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testListMoviesByCategoryStringIntInt() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testListAllMovies() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testUpdateCopiesCount() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testGetAvailableCopies() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testSearchMovie() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testSimpleMovieDAO() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testSimpleMovieDAOString() {
//		fail("Not yet implemented"); // TODO
//	}

}
