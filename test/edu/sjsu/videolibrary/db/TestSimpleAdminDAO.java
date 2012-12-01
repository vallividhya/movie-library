package edu.sjsu.videolibrary.db;


import static org.mockito.Mockito.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.test.BaseTestCase;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

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
	public final void testDisplayUserInformation() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testDisplayMovieInformation() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testGetRentAmountforMovie() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
	}

	@Test
	public final void testGetMonthlyFeesForPremiumMember() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
	}

	@Test
	public final void testUpdateMovieInfo() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testGenerateMonthlyStatement() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testDeleteUser() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
	}

	@Test
	public final void testGenerateMonthlyBillForPremiumMember() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testDeleteAdmin() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testListMembers() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao); 
	}

	@Test
	public final void testListAdmins() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

//	@Test
//	public final void testSearchUserStringStringStringStringStringStringStringStringStringStringIntInt() {
//		SimpleAdminDAO dao = new SimpleAdminDAO();
//		setupConnection(dao); 
//	}

	@Test
	public final void testDisplayAdminInformation() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testUpdateAdminInfo() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testUpdateUserPassword() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

	@Test
	public final void testSignInAdminObject() {
		SimpleAdminDAO dao = new SimpleAdminDAO();
		setupConnection(dao);
	}

}
