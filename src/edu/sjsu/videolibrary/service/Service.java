package edu.sjsu.videolibrary.service;



import java.sql.SQLException;

import java.sql.Date;

import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;

import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.db.CartDAO;
import edu.sjsu.videolibrary.db.MovieDAO;
import edu.sjsu.videolibrary.db.UserDAO;
import edu.sjsu.videolibrary.db.AdminDAO;
@WebService

public class Service {

	// Add movies to shopping cart.
	CartDAO cartDAO = new CartDAO();
	UserDAO userDAO = new UserDAO(); 
	MovieDAO movieDAO = new MovieDAO(); 
	AdminDAO adminDAO  =new AdminDAO();

	public boolean addItemsToCart(int membershipId, int movieId){
		boolean isAddedToCart = false;
		try {
			cartDAO.addToCart(movieId, membershipId);
			isAddedToCart = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}  
		return isAddedToCart;
	}

	public boolean deleteMovieFromCart (int movieId, int membershipId) {
		boolean isDeletedFromCart = false;
		try {
			cartDAO.deleteFromCart(movieId, membershipId);
			isDeletedFromCart = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}  
		return isDeletedFromCart;
	}

	public ItemOnCart[] viewCart(int membershipId){
		List<ItemOnCart> cartItemsList = cartDAO.listCartItems(membershipId);
		ItemOnCart[] cartItems = new ItemOnCart[cartItemsList.size()];
		for (int i = 0; i < cartItemsList.size(); i++) {
			cartItems[i] = cartItemsList.get(i);
			System.out.println(cartItems[i]);
		}
		return cartItems;
	}
	//	public String signUpUser (String userId, String password, String memType, String startDate,
	//			String firstName, String lastName, String address, String city, 
	//			String state, String zipCode,String ccNumber, java.sql.Date latestPaymentDate) throws SQLException 
	//	{
	//		
	//		return new UserDAO().signUpUser(userId, password, memType, startDate, firstName, lastName, 
	//				address, city, state, zipCode, ccNumber, latestPaymentDate);
	//	}
	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException
	{
		return new UserDAO().signUpAdmin(userId, password, firstName, lastName);
	}
	public String signInUser(String userId, String password) throws SQLException
	{
		return new UserDAO().signInUser(userId, password);
	}
	public String signInAdmin(String userId, String password) throws SQLException
	{
		return new UserDAO().signInAdmin(userId, password);
	}


	//List members
	public User [] viewMembers (String type){		
		List <User> memberList = userDAO.listMembers(type);
		User [] members = (User[]) memberList.toArray();
		return members;

	}

	//Delete an user and admin account
	public String deleteUserAccount (String userId) {
		String isDeleted = "false"; 
		try {
			isDeleted = userDAO.deleteUser(userId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}

	public String deleteAdminAccount (String userId) {
		String isDeleted = "false"; 
		try {
			isDeleted = userDAO.deleteAdmin(userId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}	


	//Create and delete movie

	//	public String createNewMovie (String movieName, String movieBanner, Date releaseDate, int availableCopies, double rentAmount, int categoryId)  { 
	//		String isCreated = "false";
	//		try {
	//			isCreated = movieDAO.createNewMovie(movieName, movieBanner, releaseDate, availableCopies, rentAmount, categoryId);
	//		} catch (Exception e) { 
	//			System.out.println(e.getMessage());
	//			e.printStackTrace();			
	//		}		
	//		return isCreated;
	//	}

	public String deleteMovie (String movieId) {
		String isDeleted = "false"; 
		try {
			isDeleted = movieDAO.deleteMovie(movieId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}

	public User displayUserInformation (String membershipId){
		User user = adminDAO.displayUserInformation(membershipId);
		return user;
	}

	public Movie displayMovieInformation (String movieId){
		Movie movie = adminDAO.displayMovieInformation(movieId);
		return movie;
	}

	public Transaction[] viewAccountTransactions(String membershipId){
		LinkedList<Transaction> ac = userDAO.viewAccountTransactions(membershipId);
		Transaction[] trans = ac.toArray(new Transaction[0]);
		return trans;
	}

	public String makeMonthlyPayment(String membershipId){
		String result = userDAO.makeMonthlyPayment(membershipId);
		return result;
	}

	public String updateUserInfo(String membershipId,String userId,String firstName, String lastName, String address, String city, String state, String zipCode, String membershipType,String creditCardNumber){
		String result = userDAO.updateUserInfo(membershipId, userId, firstName, lastName, address, city, state, zipCode, membershipType, creditCardNumber);
		return result;
	}

	public String updatePassword(String membershipId,String oldPassword,String newPassword){
		String result = userDAO.updatePassword(membershipId, oldPassword, newPassword);
		return result;
	}

	public String updateMovieInfo(String movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, double rentAmount, int categoryId){
		String result = adminDAO.updateMovieInfo(movieId, movieName, movieBanner, releaseDate, availableCopies, categoryId);
		return result;
	}

	

}
