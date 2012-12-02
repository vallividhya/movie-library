package edu.sjsu.videolibrary.db;


import static org.mockito.Mockito.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class TestSimpleAdminDAO extends BaseTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDisplayUserInformationWrongInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			User u = dao.displayUserInformation(19);
			assertNull(u);
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	@Test
	public void testDisplayUserInformationCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			User u = dao.displayUserInformation(1);
			assertNotNull(u);
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	@Test
	public void testDisplayUserInformationSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		
		try{
			User u = dao.displayUserInformation(1231243);
			assertNull(u);
		}catch(Exception e){
			e.getMessage();
		}
	}

	@Test
	public final void testDisplayMovieInformationWrongInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			Movie m=dao.displayMovieInformation(123);
			assertNull(m);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testDisplayMovieInformationCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			Movie m=dao.displayMovieInformation(1);
			assertNotNull(m);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testDisplayMovieInformationSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
				
		try{
			Movie m=dao.displayMovieInformation(123);
			assertNull(m);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetRentAmountforMovieValidOutput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			Double rentAmount = dao.getRentAmountforMovie();
			assertEquals(1.5,rentAmount);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetRentAmountforMovieInValidOutput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			Double rentAmount = dao.getRentAmountforMovie();
			assertEquals(0.0,rentAmount);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetRentAmountforMovieSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		
		try{
			Double rentAmount = dao.getRentAmountforMovie();
			assertEquals(-1.0,rentAmount);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetMonthlyFeesForPremiumMemberValidOutput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		try{
			Double monthlyFees = dao.getMonthlyFeesForPremiumMember();
			assertEquals(25.0,monthlyFees);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetMonthlyFeesForPremiumMemberInValidOutput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		try{
			Double monthlyFees = dao.getMonthlyFeesForPremiumMember();
			assertEquals(0.0,monthlyFees);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetMonthlyFeesForPremiumMemberSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		try{
			Double monthlyFees = dao.getMonthlyFeesForPremiumMember();
			assertEquals(-1.0,monthlyFees);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testUpdateMovieInfoCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			String result = dao.updateMovieInfo(1,"movieName","movieBanner","releaseDate",10,3);
			assertEquals("true",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
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
	public final void testUpdateMovieInfoSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		
		try{
			String result = dao.updateMovieInfo(1,"movieName","movieBanner","releaseDate",10,10);
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}		
	}



//	@Test
//	public final void testGenerateMonthlyStatement() {
//		SimpleAdminDAO dao = new SimpleAdminDAO();
//		setupConnection(dao);
//	}

	@Test
	public final void testDeleteUserCorrectInput() throws Exception{
		String result = null;
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		stub(stmt.getGeneratedKeys()).toReturn(rs);
		if(rs.next()){
			result = anyString();
		}
		try{
			result = dao.deleteUser("21324");
			assertEquals("abc",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public final void testDeleteUserIncorrectInput() throws Exception{
		String result = null;
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		stub(stmt.getGeneratedKeys()).toReturn(rs);
		if(rs.next()){
			result = anyString();
		}
		else
			result=null;
		try{
			result = dao.deleteUser("21324");
			assertEquals(null,result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
		
		@Test
		public final void testDeleteUserNullValueInput() throws Exception{
			String result = null;
			SimpleAdminDAO dao = new SimpleAdminDAO();
			setupConnection(dao); 
			
			stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
			
			try{
				result = dao.deleteUser(null);
				assertEquals(null,result);
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
	public void testGenerateMonthlyBillForPremiumMemberSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
		
		
		try{
			PaymentForPremiumMemInfo pymnt = dao.generateMonthlyBillForPremiumMember(0, 0, 0);
			assertNull(pymnt);
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testDeleteAdminCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			String result = dao.deleteAdmin("aa");
			assertEquals("abc",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteAdminIncorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String result = dao.deleteAdmin("aa");
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteAdminSQLException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new SQLException());
		
		try{
			String result = dao.deleteAdmin("aa");
			assertEquals(null,result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testListMembersCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
		
		List <User> members = new ArrayList<User>();
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			members = dao.listMembers("all");
			assertNotNull(members);
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
			members = dao.listMembers("all");
			assertNull(members);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testListAdminsValidOutput() throws Exception{
		List <Admin> admins = new ArrayList<Admin>();
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			admins = dao.listAdmins();
			assertNotNull(admins);
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

//	@Test
//	public final void testSearchUserStringStringStringStringStringStringStringStringStringStringIntInt() {
//		SimpleAdminDAO dao = new SimpleAdminDAO();
//		setupConnection(dao); 
//	}

	@Test
	public void testDisplayAdminInformationCorrectInput() throws Exception{
		Admin admin = new Admin();
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			admin = dao.displayAdminInformation("aa");
			assertNotNull(admin);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDisplayAdminInformationIncorrectInput() throws Exception{
		Admin admin = new Admin();
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			admin = dao.displayAdminInformation("ab");
			assertNull(admin);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDisplayAdminInformationSQLException() throws Exception{
		Admin admin = new Admin();
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());		
		
		try{
			admin = dao.displayAdminInformation("abc");
			assertNull(admin);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}


	@Test
	public void testUpdateAdminInfoCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			String result = dao.updateAdminInfo("adminId"," firstName", "lastName"," password");
			assertEquals("true",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateAdminInfoIncorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String result = dao.updateAdminInfo("adminId1"," firstName1", "lastName1"," password1");
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateUserPasswordCorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(1);
		
		try{
			String result = dao.updateUserPassword(1, "newPassword");
			assertEquals("true",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateUserPasswordIncorrectInput() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toReturn(0);
		
		try{
			String result = dao.updateUserPassword(1, "newPassword");
			assertEquals("invalidID",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateUserPasswordException() throws Exception{
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeUpdate(anyString())).toThrow(new Exception());
		
		try{
			String result = dao.updateUserPassword(1, "newPassword");
			assertEquals("false",result);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testSignInAdminObjectCorrectInput() throws Exception{
		Admin bean = new Admin(); 
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(true);
		
		try{
			bean = dao.signInAdminObject("userID", "password");
			assertNotNull(bean);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSignInAdminObjectIncorrectInput() throws Exception{
		Admin bean = new Admin(); 
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toReturn(rs);
		stub(rs.next()).toReturn(false);
		
		try{
			bean = dao.signInAdminObject("userID1", "password1");
			assertNull(bean);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSignInAdminObjectSQLException() throws Exception{
		Admin bean = new Admin(); 
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
		
		stub(stmt.executeQuery(anyString())).toThrow(new SQLException());
				
		try{
			bean = dao.signInAdminObject("userID2", "password2");
			assertNull(bean);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
}

