package edu.sjsu.videolibrary.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.sjsu.videolibrary.util.Utils;

public class UserDAO extends VideoLibraryDAO 
{
	public UserDAO()
	{ }
	 
	public String signUpUser (String userId, String password, String memType, java.sql.Date startDate,
			String firstName, String lastName, String address, String city, 
			String state, String zipCode,String ccNumber, java.sql.Date latestPaymentDate) throws SQLException 
	{
		
		String sql = "insert into user (UserId,password,membershipType,startDate,firstName,lastName," +
				"address,city,state,zip,creditCardNumber,latestPaymentDate) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setString(1, userId); pst.setString(2, Utils.encryptPassword(password));
		pst.setString(3,memType); pst.setDate(4, startDate);
		pst.setString(5, firstName);pst.setString(6, lastName);
		pst.setString(7, address);pst.setString(8, city);
		pst.setString(9, state);pst.setString(10, zipCode);
		pst.setString(11, ccNumber);pst.setDate(12, latestPaymentDate);
		pst.execute();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next())
		{
			Integer memID = rs.getInt(1);
			return memID.toString();
		}     
		return "";
	}
	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException 
	{
		String sql = "insert into admin (UserId,password,firstName,lastName) values (?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setString(1, userId); pst.setString(2, Utils.encryptPassword(password));
		pst.setString(3, firstName);pst.setString(4, lastName);
		pst.execute();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next())
		{
			Integer memID = rs.getInt(1);
			return memID.toString();
		}     
		return "";
	}
	public String signInUser(String userId, String password) throws SQLException
	{
		String sql = "select userId, password from  users where userId = '" + userId + "'" + " and password = '" + Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "";
	}
	public String signInAdmin(String userId, String password) throws SQLException
	{
		String sql = "select userId, password from admin where userId = '" + userId + "'" + " and password = '" + Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "";
	}
}

