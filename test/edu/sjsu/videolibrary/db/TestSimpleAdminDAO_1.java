package edu.sjsu.videolibrary.db;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;

import junit.framework.TestCase;

public class TestSimpleAdminDAO_1 extends BaseTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public final void testUpdateMovieInfoWrongInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String result = dao.updateMovieInfo(1,"movieName","movieBanner","releaseDate",10,10);
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}		
	}
	
	@Test
	public final void testUpdateMovieInfoNullValueInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String result = dao.updateMovieInfo(1,null,null,"releaseDate",10,10);
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}		
	}
	

	@Test
	public void testGenerateMonthlyBillForPremiumMemberIncorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			PaymentForPremiumMemInfo pymnt = dao.generateMonthlyBillForPremiumMember(100, 1, 12);
			assertNull(pymnt);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testGenerateMonthlyBillForPremiumMemberCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			PaymentForPremiumMemInfo pymnt = dao.generateMonthlyBillForPremiumMember(12, 12, 12);
			assertNotNull(pymnt);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}	
	
	@Test
	public void testListMembersSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		List <User> members = new ArrayList<User>();
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
				
		try{
			members = dao.listMembers("all",0,100);
			assertNull(members);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testListAdminsSQLException() throws Exception{
		List <Admin> admins = new ArrayList<Admin>();
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
				
		try{
			admins = dao.listAdmins();
			assertNull(admins);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	
}
	





