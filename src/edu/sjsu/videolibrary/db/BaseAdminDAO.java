package edu.sjsu.videolibrary.db;

import java.sql.SQLException;
import java.util.List;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;

public abstract class BaseAdminDAO extends VideoLibraryDAO {
	
	public abstract User displayUserInformation (int membershipId);

	public abstract Movie displayMovieInformation (int movieId);

	public abstract double getRentAmountforMovie();

	public abstract double getMonthlyFeesForPremiumMember();

	public abstract String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId);

	public abstract String generateMonthlyStatement(int membershipId,int month,int year) throws SQLException;
	
	public abstract String deleteUser (String userId);

	public abstract PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(int membershipId,int month,int year);

	public abstract String deleteAdmin (String userId);

	public abstract List <User> listMembers (String type);
	
	public abstract User[] searchUserByFirstName(String adminInput) throws NoUserFoundException, InternalServerException;
	public abstract User[] searchUserByLastName(String adminInput) throws NoUserFoundException, InternalServerException;
	
	public abstract User[] searchUserByCity(String adminInput) throws NoUserFoundException, InternalServerException;
	abstract public User[] searchUserByState(String adminInput) throws NoUserFoundException, InternalServerException;
	
	
	abstract public User[] searchUserByMemberShipType(String adminInput) throws NoUserFoundException, InternalServerException;
	
	
	abstract public User[]  searchUserByMembershipId(int adminInput) throws NoUserFoundException, InternalServerException;
	
	

}
