package edu.sjsu.videolibrary.db;


import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataAccess extends VideoLibraryDAO 
{
	public DataAccess()
	{ }
	
	public String signUp (String userId, String password, String firstName, String lastName, String address, String city, String state, String zipCode, boolean membership) throws SQLException 
	{
		// last parameter membership is not in DB
		//
		String sql = "insert into person (UserId,Password,StartDate,FirstName,LastName,Address,City,State,Zip) values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setString(1, userId); pst.setString(2, password);
		pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
		pst.setString(4, firstName);pst.setString(5, lastName);
		pst.setString(6, address);pst.setString(7, city);
		pst.setString(8, state);pst.setString(9, zipCode);
		pst.execute();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next())
		{
			Integer memID = rs.getInt(1);
			return memID.toString();
		}     
		return "";
	}
	public boolean signUp (String userId, String password, String firstName, String lastName) throws SQLException 
	{
		// mismatch API
		//
		signUp(userId,password,firstName,lastName,"", "", "", "", false);
		return false;
	}
	public String signIn (String UserId, String password) throws SQLException
	{
		// Changed membership id  to user id.
		//
		String sql = "select UserId, Password from  person where UserID = '" + UserId + "'" + " and Password = '" + password + "'";
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "";
	}
}


