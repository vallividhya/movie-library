package edu.sjsu.videolibrary.db;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.test.BaseTestCase;

public class TestSimpleMovieDAO extends BaseTestCase{
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
	public  void testDeleteMovieWrongInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		try{
			result = dao.deleteMovie("235436547");
			assertEquals("",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	

	@Test
	public  void testDeleteMovieCorrectInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		try{
			result = dao.deleteMovie("123456789");
			assertEquals("true",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testDeleteMovieSQLException() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		try{
			result = dao.deleteMovie("23");
			assertEquals(null,result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public  void testReturnMovieWrongInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			result = dao.returnMovie(1, "23");
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testReturnMovieNullValueInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			result = dao.returnMovie(1, null);
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testReturnMovieCorrectInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			result = dao.returnMovie(1, "254364678658");
			assertEquals("true",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testReturnMovieSQLException() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		
		try{
			dao.returnMovie(1, "254364678658");
			assertEquals(null,result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}	

	@Test
	public  void testListCategoriesSQLException()  throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		try{
			String[] s =dao.listCategories();
			assertEquals(null,s);
		}catch(Exception e){};
	}

		
	
	


	@Test
	public  void testListAllMoviesSQLExceptions() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		try{
			Movie[] m = dao.listAllMovies();
			assertEquals(null,m);
		}catch(Exception e){};
	}

	@Test
	public  void testGetAvailableCopiesWrongInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		
		try{
			int i = dao.getAvailableCopies(100);
			assertEquals(0,i);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	

}
