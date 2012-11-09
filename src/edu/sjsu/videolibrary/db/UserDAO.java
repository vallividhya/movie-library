package edu.sjsu.videolibrary.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.util.Utils;

public class UserDAO extends VideoLibraryDAO 
{
	public UserDAO()
	{ }
	 
	public String signUpUser (String userId, String password, String memType, java.sql.Date startDate,
			String firstName, String lastName, String address, String city, 
			String state, String zipCode,String ccNumber, java.sql.Date latestPaymentDate) throws SQLException 
	{
		
		String sql = "INSERT INTO user (userId,password,membershipType,startDate,firstName,lastName," +
					 "address,city,state,zip,creditCardNumber,latestPaymentDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
		String sql = "INSERT INTO admin (userId,password,firstName,lastName) VALUES (?,?,?,?)";
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
		String sql = "SELECT userId, password FROM users WHERE userId = '" + userId + "'" + " AND password = '" + Utils.encryptPassword(password) + "'";
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
		String sql = "SELECT userId, password FROM admin where userId = '" + userId + "'" + " AND password = '" + Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "d";     
	}
	
	
	public String deleteUser (String userId) {
		try {
			String sql = "DELETE from user WHERE userId = ?";
			//String sql = "DELETE from user WHERE membershipId  = ?";
			
			PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, userId); 
			pst.execute();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
			{
				Integer memID = rs.getInt(1);
				return memID.toString();
			}    
		} catch (SQLException e) { e.printStackTrace(); } 
		return "";		
	}
	
	public String deleteAdmin (String userId) {
		
		//SuperAdmin should not be removed from the Database
		if (Integer.parseInt(userId) != 1) {	
			try {
				String sql = "DELETE FROM admin WHERE userId = ?";
				
				PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, userId); 
				pst.execute();
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next())
				{
					Integer memID = rs.getInt(1);
					return memID.toString();
				}    
			} catch (SQLException e) { e.printStackTrace(); } 
		} else { 
			
		}
		return "";		
	}
	
	public List <User> listMembers (String type){		
		PreparedStatement preparedStmt = null;
		List <User> members = new ArrayList<User>();
		String query = ""; 
		
		if (type.equals("all")) {
			query = "SELECT user.membershipId, user.userId, user.firstName, user.lastName FROM user";
		} else { 
			query = "SELECT user.membershipId, user.userId, user.firstName, user.lastName FROM user WHERE user.membershipType = " + type; 
		}
		
		try {
			preparedStmt = con.prepareStatement(query);
			System.out.println(preparedStmt.toString());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				User member = new User();
				member.setMembershipId(rs.getInt("membershipId"));
				member.setUserId(rs.getString("userId"));
				member.setFirstName(rs.getString("firstName"));
				member.setLastName(rs.getString("lastName"));
				members.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return members;
	}

	
}

