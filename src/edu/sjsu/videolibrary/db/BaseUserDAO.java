package edu.sjsu.videolibrary.db;

import java.sql.SQLException;
import java.util.LinkedList;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.InvalidCreditCardException;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;

public abstract class BaseUserDAO extends VideoLibraryDAO {

	public BaseUserDAO() {
		super();
	}

	public BaseUserDAO(VideoLibraryDAO dao) {
		super(dao);
	}

	public abstract String signUpUser(String userId, String password, String memType,String firstName, String lastName, 
			String address, String city, 
			String state, String zipCode,String ccNumber) throws SQLException 
			;

	public abstract String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException;

	public abstract User signInUser(String userId, String password) throws SQLException;

	public abstract String signInAdmin(String userId, String password) throws SQLException;

	public abstract LinkedList<Transaction> viewAccountTransactions(int membershipId) throws Exception;

	public abstract String makeMonthlyPayment(int membershipId) throws Exception;

	public abstract String updateUserInfo(int membershipId,String userId,String firstName, String lastName, String address, String city, String state, String zipCode, String membershipType,String creditCardNumber) throws Exception;

	public abstract String updatePassword(int membershipId,String oldPassword,String newPassword) throws Exception;

	public abstract LinkedList<StatementInfo> viewStatement (int membershipId,int month,int year) throws Exception;
	
	public abstract User queryMembershipType (int membershipId) ;
	
	public abstract void updateRentedMoviesForUser (int membershipId, int rentedMovies) throws InternalServerException;
	
	public abstract String queryCreditCardNumber (int membershipId) throws InvalidCreditCardException;
	}



	

