package edu.sjsu.videolibrary.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;

public class StoredProcAdminDAO extends BaseAdminDAO {
	
	public StoredProcAdminDAO() {
		super();
	}
	
	public StoredProcAdminDAO(VideoLibraryDAO dao) {
		super(dao);
	}
	
	@Override
	public User[] searchUser(String membershipId, String userId,
			String membershipType, String startDate, String firstName,
			String lastName, String address, String city, String state,
			String zipCode, int start, int stop) throws NoUserFoundException {

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
		query = query + "," + start + "," + stop;
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
				//System.out.println(user.getMembershipId());
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
	
	@Override
	public Admin displayAdminInformation (String adminId) throws SQLException{
		return null;
	}
	
	@Override
	public String updateAdminInfo(String adminId,String firstName, String lastName, String password) throws SQLException{
		return null;
	}
	
	@Override
	public String updateUserPassword(int membershipId,String newPassword) throws SQLException{
		return null;
	}
	
	@Override
	public Admin signInAdminObject (String userId, String password) throws SQLException{
		return null;
	}
	
	@Override
	public User displayUserInformation(int membershipId){
		return null;
	}
	
	@Override
	public Movie displayMovieInformation (int movieId){
		return null;
	}
	
	@Override
	public double getRentAmountforMovie(){
		return (Double) null;
	}

	@Override
	public double getMonthlyFeesForPremiumMember(){
		return (Double) null;
	}

	@Override
	public String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId){
		return null;
	}

	@Override
	public String generateMonthlyStatement(int membershipId, int statementId,int month,int year) throws SQLException{
		return null;
	}
	
	@Override
	public String deleteUser (String userId){
		return null;
	}

	@Override
	public PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(int membershipId,int month,int year){
		return null;
	}

	@Override
	public String deleteAdmin (String userId){
		return null;
	}

	
	@Override
	public List <Admin> listAdmins(){
		return null;
	}

	@Override
	public List<User> listMembers(String type, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
