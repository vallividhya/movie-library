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
	public void testCreateNewMovieWrongInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		stub( stmt.getGeneratedKeys()).toReturn(rs);
		
		if(rs.next()){			 
			result = anyString();
			}		
		try{
			String result = dao.createNewMovie("aa", "bb", "2012", 1, 1);
			assertEquals("", result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
@Test
public void testCreateNewMovieNullValueInput() throws Exception {
	SimpleMovieDAO dao = new SimpleMovieDAO();
	setupConnection(dao);
	stub(stmt.executeUpdate(anyString())).toReturn(0);
	stub( stmt.getGeneratedKeys()).toReturn(rs);
	if(rs.next()){
		 
		result = anyString();}
	
	try{
		String result = dao.createNewMovie(null, null, "2012",5, 1);
		assertEquals("", result);
	}catch(Exception e){
		fail(e.getMessage());
	}
}

public void testCreateNewMovieCorrectInput() throws Exception {
	SimpleMovieDAO dao = new SimpleMovieDAO();
	setupConnection(dao);
	
	stub(stmt.executeUpdate(anyString())).toReturn(1);
	stub( stmt.getGeneratedKeys()).toReturn(rs);
	if(rs.next()){
		 
		result = anyString();}
	
	try{
		String result = dao.createNewMovie("aa", "bb", "2012",5,1);
		assertEquals(anyString(), result);
	}catch(Exception e){
		fail(e.getMessage());
	}
}

	@Test
	public void testCreateNewMovieSQLException() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		try{
		String result =dao.createNewMovie("aa", "bb", "2012",5,1);
		assertEquals(null,result);
		}catch(Exception e){fail(e.getMessage());};
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
	public  void testDeleteMovieNullValueInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		try{
			result = dao.deleteMovie(null);
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
	public  void testListMoviesByCategoryWrongInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		
		try{
			Movie[] m = dao.listMoviesByCategory("Drama",10,10);
			assertNull(m);
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testListMoviesByCategoryCorrectInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		
		try{
			Movie[] m = dao.listMoviesByCategory("Drama",10,10);
			assertNotNull(m);
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testListMoviesByCategoryNullValueInput() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		
		try{
			Movie[] m = dao.listMoviesByCategory(null,10,10);
			assertNull(m);
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testListMoviesByCategorySQLException() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());	
		
		
		try{
			Movie[] m = dao.listMoviesByCategory("xyz",10,10);
			assertNull(m);
			
		}catch(Exception e){
			fail(e.getMessage());
		}
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
	public  void testUpdateCopiesCountWrongInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String sstr = dao.updateCopiesCount(1, 10);
			assertEquals("false",sstr);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testUpdateCopiesCountNullValueInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String sstr = dao.updateCopiesCount(1,(Integer) null);
			assertEquals("false",sstr);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testUpdateCopiesCountCorrectInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			String sstr = dao.updateCopiesCount(1, 10);
			assertEquals("true",sstr);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public  void testUpdateCopiesCountSQLException() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		
		try{
			String sstr = dao.updateCopiesCount(1, 10);
			assertEquals(null,sstr);
		}catch(Exception e){
			fail(e.getMessage());
		}
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
	
	@Test
	public  void testGetAvailableCopiesCorrectInput() throws Exception{
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		
		try{
			int i = dao.getAvailableCopies(1);
			assertEquals(12,i);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testGetAvailableCopiesSQLExceptions() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		try{
			int i = dao.getAvailableCopies(1);
			assertEquals(0,i);
		}catch(Exception e){
			fail(e.getMessage());
		};
	}

//	@Test
//	public  void testSearchMovie() {
//		fail("Not yet implemented"); // TODO
//	}
//
}
