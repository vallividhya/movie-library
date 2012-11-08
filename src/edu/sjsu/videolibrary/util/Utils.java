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

	static public boolean validateLogin( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		String user =(String) request.getSession().getAttribute("user");
		if( user == null ) {
			response.sendRedirect("SignIn");
			return false;
		}
		return true;
	}

	static public String encryptPassword(String pwd){
		String securePwd = null;
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
		return securePwd;
	}

	static public boolean isValidInput(String in){
		return in != null && !in.isEmpty() && !in.trim().isEmpty();
	}

	static public boolean isValidCardNum(String ccNum){
		if(isValidInput(ccNum) && ccNum.length()==16){
			for(char temp :ccNum.toCharArray()){
				if(temp < '0'|| temp>'9'){
					return false;
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

	static public void generateHeader( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		out.write("<table border=1><tr>");
		out.write("<td><a href=\"UserMain\">View Products</a></td>");
		out.write("<td><a href=\"ViewAccount\">View My Account</a></td>");
		out.write("	<td><a href=\"Cart\">Shopping Cart</a></td>");
		out.write("	<td><a href=\"SellItem\">Sell an item</a></td>");
		out.write("	<td>Last Logged In:"+((Date)session.getAttribute("lastLoggedInTime")).toString()+"</td>");
		out.write("	<td><a href=\"SignOutServlet\">Sign-off</a></td>");
		out.write("</tr>");
		out.write("</table>");
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

