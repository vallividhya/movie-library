package edu.sjsu.videolibrary.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.util.Utils;

public class StoredProcUserDAO extends BaseUserDAO {

	@Override
	public String signUpUser(String userId, String password, String memType,
			String firstName, String lastName, String address, String city,
			String state, String zipCode, String ccNumber) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String signUpAdmin(String userId, String password, String firstName,
			String lastName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User signInUser(String userId, String password)
			throws SQLException {
		User user = new User();
		String encryptedPasswrd = Utils.encryptPassword(password);
		String sql = " call videolibrary.signInUser('"+userId+"','"+encryptedPasswrd+"');";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			user.setMembershipId(rs.getInt("membershipId"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setAddress(rs.getString("address"));
			user.setCity(rs.getString("city"));
			user.setCreditCardNumber(rs.getString("creditCardNumber"));
			user.setMembershipType(rs.getString("membershipType"));
			user.setState(rs.getString("state"));
			user.setZip(rs.getString("zip"));
			Date startDate = rs.getDate("startDate");
			if(startDate !=null){
				user.setStartDate(startDate.toString());
			}
			else{
				user.setStartDate(null);
			}
			Date latestPaymentDate = rs.getDate("latestPaymentDate");
			if(latestPaymentDate !=null){
				user.setStartDate(latestPaymentDate.toString());
			}
			else{
				user.setLatestPaymentDate(null);
			}			
			user.setUserId(rs.getString("userId"));
			user.setPassword(rs.getString("password"));
		} 
		else{
			user = null;
		}
		return user;
	}

	@Override
	public String signInAdmin(String userId, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Transaction> viewAccountTransactions(int membershipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeMonthlyPayment(int membershipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserInfo(int membershipId, String userId,
			String firstName, String lastName, String address, String city,
			String state, String zipCode, String membershipType,
			String creditCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updatePassword(int membershipId, String oldPassword,
			String newPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<StatementInfo> viewStatement(int membershipId, int month,
			int year) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
