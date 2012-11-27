package edu.sjsu.videolibrary.db;

import java.sql.SQLException;
import java.util.List;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;

public abstract class BaseAdminDAO extends VideoLibraryDAO {

	public BaseAdminDAO() {
		super();
	}

	public BaseAdminDAO(String transactionId) {
		super(transactionId);
	}

	public abstract User displayUserInformation(int membershipId);

	public abstract Movie displayMovieInformation (int movieId);

	public abstract double getRentAmountforMovie();

	public abstract double getMonthlyFeesForPremiumMember();

	public abstract String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId);

	public abstract String generateMonthlyStatement(int membershipId,int month,int year) throws SQLException;
	
	public abstract String deleteUser (String userId);

	public abstract PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(int membershipId,int month,int year);

	public abstract String deleteAdmin (String userId);

	public abstract List <User> listMembers (String type);
	
	public abstract List <Admin> listAdmins(); 
	
	public abstract User[] searchUser(String membershipId, String userId,
			String membershipType, String startDate, String firstName,
			String lastName, String address, String city, String state,
			String zipCode) throws NoUserFoundException;
	
	abstract public Admin displayAdminInformation (String adminId) throws SQLException;
	
	abstract public String updateAdminInfo(String adminId,String firstName, String lastName, String password) throws SQLException;
	
	abstract public String updateUserPassword(int membershipId,String newPassword) throws SQLException;
	
	abstract public Admin signInAdminObject (String userId, String password) throws SQLException;
}
