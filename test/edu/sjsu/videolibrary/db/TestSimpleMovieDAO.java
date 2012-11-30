package edu.sjsu.videolibrary.db;


import static org.mockito.Mockito.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.test.BaseTestCase;

public class TestSimpleMovieDAO extends BaseTestCase {

	@Before
	public  void setUp() throws Exception {
		super.setUp();
	}

	@After
	public  void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testCreateNewMovie() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);

		stub(stmt.executeUpdate(anyString())).toReturn(0);
		stub(stmt.getGeneratedKeys()).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			assertEquals("",dao.createNewMovie("aa","bb","cc",1,1));
		}catch(Exception e){
			fail(e.getMessage());
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

	 */
}
