package edu.sjsu.videolibrary.util;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testEncryptPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidInput() {
		assertTrue("Valid for correct input", Utils.isValidInput("asdfg"));
		assertEquals("",true, Utils.isValidInput("asdfg") );
	}
	
	@Test
	public void testIsValidEMailAddress() {
		fail("Not yet implemented");
	}

}
