package edu.sjsu.videolibrary.util;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.validator.routines.EmailValidator;

//import sun.security.jca.GetInstance;

public class Utils {

	
	static public String encryptPassword(String pwd){
		String securePwd = null;
		if(isValidInput(pwd)){		
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = pwd.getBytes("UTF-8");

			MessageDigest md;

			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			securePwd = (new BigInteger(1,thedigest)).toString(16);
		} 

		catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());

		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
		}
		else{
			securePwd = null;
		}
		return securePwd;
	}

	static public boolean isValidInput(String in){
		return in != null && !in.isEmpty() && !in.trim().isEmpty();
	}


	static public boolean isValidEMailAddress(String emailStr){
		if(isValidInput(emailStr)){
			EmailValidator email = EmailValidator.getInstance();
			return email.isValid(emailStr);
		}
		else{
			return false;
		}
	}
}

