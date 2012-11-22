package edu.sjsu.videolibrary.db;

import java.sql.SQLException;
import java.util.LinkedList;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;

public abstract class BaseUserDAO extends VideoLibraryDAO {

	public abstract User signUpUser (String userId, String password, String memType,String firstName, String lastName, 
			String address, String city, 
			String state, String zipCode,String ccNumber) throws SQLException 
			;

	public abstract String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException;


	public abstract String signInUser(String userId, String password) throws SQLException;

	public abstract String signInAdmin(String userId, String password) throws SQLException;

	public abstract LinkedList<Transaction> viewAccountTransactions(int membershipId);

	public abstract String makeMonthlyPayment(int membershipId);


	public abstract String updateUserInfo(int membershipId,String userId,String firstName, String lastName, String address, String city, String state, String zipCode, String membershipType,String creditCardNumber);

	public abstract String updatePassword(int membershipId,String oldPassword,String newPassword);

	public abstract LinkedList<StatementInfo> viewStatement (int membershipId,int month,int year);
}
