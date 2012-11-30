/**
 * 
 */
package edu.sjsu.videolibrary.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.sjsu.videolibrary.db.SimpleAdminDAO;
import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;


public class SimpleAdminDAOTest 
{
	SimpleAdminDAO DAO ;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		this.DAO = new SimpleAdminDAO();
		if(DAO == null)
			System.out.println("is null");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void testdisplayUserInformation() 
	{
		User u = DAO.displayUserInformation(1);
		assertNotNull(u);
	}

	@Test
	public void tesdisplayMovieInformationt() 
	{
		Movie m = DAO.displayMovieInformation(1);
		assertNotNull(m);
	}
	@Test
	public void testgetRentAmountforMovie() 
	{
		double d = DAO.getRentAmountforMovie();
		assertFalse(d == -1);
	}
	@Test
	public void testgetMonthlyFeesForPremiumMember() 
	{
		double d = DAO.getMonthlyFeesForPremiumMember();
		assertFalse(d == -1);
	}
	@Test
	public void testupdateMovieInfo() 
	{
		int movieId = 1;
		String movieName = "Batman";
		String movieBanner = "Batman";
		String releaseDate = "12-2-2002";
		int availableCopies = 1;
		int categoryId = 1;
		
		String s = DAO.updateMovieInfo(movieId,movieName,movieBanner,releaseDate,availableCopies,categoryId);
		assertTrue(s == null || s.equals("false"));
	}
	@Test
	public void testgenerateMonthlyStatement() throws SQLException 
	{
		int membershipId = 1;
		int month = 5;
		int year = 2012;
		String s = null;

		try
		{
		s = DAO.generateMonthlyStatement(membershipId,month,year);
		}
		catch(Exception e)
		{
			
		}
		assertFalse(s != null);
	}
	@Test
	public void testdeleteUser() 
	{
		String s = null;
		try{
		
		 s = DAO.deleteUser("1");
		}
		catch(Exception e)
		{}
		assertTrue(s == null);
	}
	@Test
	public void testgenerateMonthlyBillForPremiumMember() 
	{
		int membershipId = 1;
		int month = 5;
		int year = 2012;
	
		PaymentForPremiumMemInfo p = DAO.generateMonthlyBillForPremiumMember(membershipId,month,year);
		assertFalse(p != null);
	}
	@Test
	public void testdeleteAdmin() 
	{
		 String s = null;
		 try{
		 DAO.deleteAdmin("1");
		 }
		 catch(Exception e)
		 {}
		 assertTrue(s == null);
	}
	@Test
	public void testlistMembers() 
	{
		List <User> l = new ArrayList<User>();
		try
		{
			l =  DAO.listMembers("Simple");
		}
		catch(Exception e) {}
		assertTrue(l.size() > 0 );
	}
	
	@Test
	public void testupdateUserPassword() 
	{
		String s = DAO.updateUserPassword(1,"guest");
		assertFalse( s.equals("true"));
	}
	@Test
	public void testsignInAdminObject() 
	{
		Admin ad = null;
		try
		{
			ad = DAO.signInAdminObject ("admin", "admin");
		}
		catch(Exception e){}
		assertTrue(ad == null);
	}
	@Test
	public void testlistAdmins() 
	{
		List <Admin> l = new ArrayList<Admin>();
		try
		{
			l =  DAO.listAdmins();
		}
		catch(Exception e) {}
		assertTrue(l.size() > 0 );
	}


}
