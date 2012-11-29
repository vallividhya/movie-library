package edu.sjsu.videolibrary.util;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.sjsu.videolibrary.util.Utils;

import junit.framework.TestCase;

public class Util extends TestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testEncryptPassword1() {
		assertEquals("Correct MD5 Returned",Utils.encryptPassword("abc"),"900150983cd24fb0d6963f7d28e17f72");
	}

	@Test
	public void testIsValidInput1() {
		assertTrue("Valid Input", Utils.isValidInput("abcd"));
	}

	@Test
	public void testIsValidEMailAddress1() {
		assertTrue("Valid email address", Utils.isValidEMailAddress("abc@gmail.com"));
	}

	@Test
	public void testIsValidInput2() {
		assertFalse("Invalid Input", Utils.isValidInput(" "));
	}

	@Test
	public void testIsValidEMailAddress2() {
		assertFalse("Inalid email address", Utils.isValidEMailAddress("asfd.cm"));
	}


	@Test
	public void testIsValidInput3() {
		assertFalse("Invalid Input", Utils.isValidInput(null));
	}

	@Test
	public void testIsValidEMailAddress3() {
		assertFalse("Inalid email address", Utils.isValidEMailAddress(null));
	}


}
