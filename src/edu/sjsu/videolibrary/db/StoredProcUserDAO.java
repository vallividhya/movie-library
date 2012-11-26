package edu.sjsu.videolibrary.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;

public class StoredProcUserDAO extends BaseUserDAO {

	@Override
	public User signUpUser(String userId, String password, String memType,
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
	public String signInUser(String userId, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public User[] searchUser(String membershipId, String userId,
			String membershipType, String startDate, String firstName,
			String lastName, String address, String city, String state,
			String zipCode) throws NoUserFoundException {

		ArrayList<User> searchList = new ArrayList<User>();
		User[] userArray = null;

		String query = "call videolibrary.searchUser(";
		if( membershipId == null ) {
			query += "null,";
		} else {
			query += "'" + membershipId + "',";
		}
		if( userId == null ) {
			query += "null,";
		} else {
			query += "'" + userId + "',";
		}
		if( membershipType == null ) {
			query += "null,";
		} else {
			query += "'" + membershipType + "',";
		}
		if( startDate == null ) {
			query += "null,";
		} else {
			query += "'" + startDate + "',";
		}
		if( firstName == null ) {
			query += "null,";
		} else {
			query += "'" + firstName + "',";
		}
		if( lastName == null ) {
			query += "null,";
		} else {
			query += "'" + lastName + "',";
		}
		if( address == null ) {
			query += "null,";
		} else {
			query += "'" + address + "',";
		}
		if( city == null ) {
			query += "null,";
		} else {
			query += "'" + city + "',";
		}
		if( state == null ) {
			query += "null,";
		} else {
			query += "'" + state + "',";
		}
		if( zipCode == null ) {
			query += "null";
		} else {
			query += "'" + zipCode + "'";
		}
		query += ")";
		System.out.println(query);
		
		try{
			stmt.execute(query);
			ResultSet rs = stmt.getResultSet();
			if (!rs.isBeforeFirst()) {
				throw new NoUserFoundException(
						"No users found with the given search criteria");
			}
			while (rs.next()) {
				ResultSetMetaData rsm = rs.getMetaData();
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setMembershipType(rs.getString(4));
				user.setStartDate(rs.getDate(5).toString());
				user.setFirstName(rs.getString(6));
				user.setLastName(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setCity(rs.getString(9));
				user.setState(rs.getString(10));
				user.setZip(rs.getString(11));
				user.setCreditCardNumber(rs.getString(12));
				Date paymentDate = rs.getDate(13); 
				if (paymentDate != null) {
					user.setLatestPaymentDate(paymentDate.toString()); 
				}
				System.out.println(user.getMembershipId());
				searchList.add(user);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		userArray = searchList.toArray(new User[searchList.size()]);
		return userArray;
	}


}
