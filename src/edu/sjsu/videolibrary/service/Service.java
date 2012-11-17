package edu.sjsu.videolibrary.service;



import java.sql.SQLException;

import java.sql.Date;

import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;

import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.StatementInfo;
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

	// Add movies to shopping cart	

	public String addItemsToCart(int membershipId, int movieId){
		String isAddedToCart = "false";
		try {
			cartDAO.addToCart(movieId, membershipId);
			isAddedToCart = "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}  
		return isAddedToCart;
	}

	public String deleteMovieFromCart (int movieId, int membershipId) {
		String isDeletedFromCart = "false";
		try {
			cartDAO.deleteFromCart(movieId, membershipId);
			isDeletedFromCart = "true";
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

	public String signUpUser(String userId, String password, String memType,String firstName, String lastName, 
			String address, String city, String state, String zipCode,String ccNumber) throws SQLException 
			{
		return userDAO.signUpUser(userId, password, memType,new java.sql.Date(System.currentTimeMillis()), 
				firstName, lastName, address, city, state, zipCode, ccNumber, 
				new java.sql.Date(System.currentTimeMillis()));
			}
	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException
	{
		return userDAO.signUpAdmin(userId, password, firstName, lastName);
	}
	public String signInUser(String userId, String password) throws SQLException
	{
		return userDAO.signInUser(userId, password);
	}
	public String signInAdmin(String userId, String password) throws SQLException
	{
		return userDAO.signInAdmin(userId, password);
	}
	
	public String [] getStates () { 
		States states = new States();
		return states.getStates();
	}

	//List members
	public User [] viewMembers (String type){		
		List <User> memberList = adminDAO.listMembers(type);
		User [] members = (User[]) memberList.toArray();
		return members;

	}
	
	//List movies
	public Movie [] viewMovies (String category){		
		List <Movie> movielist = userDAO.listMovies(category);
		Movie [] movies = movielist.toArray(new Movie[0]);
		return movies;
	}	

	public Admin [] viewAdmins () {		
		List <Admin> memberList = adminDAO.listAdmins();
		Admin [] admins = memberList.toArray(new Admin[0]);
		return admins;
	}
	
	//Delete an user and admin account
	public String deleteUserAccount (String userId) {
		String isDeleted = "false"; 
		try {
			isDeleted = adminDAO.deleteUser(userId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}

	public String deleteAdminAccount (String userId) {
		String isDeleted = "false"; 
		try {
			isDeleted = adminDAO.deleteAdmin(userId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}	


	//Create and delete movie

	public String createNewMovie (String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId)  { 
		String isCreated = "false";
		try {
			isCreated = movieDAO.createNewMovie(movieName, movieBanner, releaseDate, availableCopies, categoryId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}		
		return isCreated;
	}

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

	public String updateUserPassword (String membershipId, String newPassword) {
		String result = adminDAO.updateUserPassword(membershipId, newPassword);
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

	public String generateMonthlyStatement(String membershipId,int month,int year){
		String result = null;
		try {
			result = adminDAO.generateMonthlyStatement(membershipId, month, year);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public StatementInfo[] viewStatement(String membershipId,int month,int year){
		StatementInfo [] stmnt = userDAO.viewStatement(membershipId, month, year).toArray(new StatementInfo[0]);
		return stmnt;
	}

	public PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(String membershipId,int month,int year){
		PaymentForPremiumMemInfo pymnt = adminDAO.generateMonthlyBillForPremiumMember(membershipId, month, year);
		return pymnt;
	}

	public double getRentAmountforMovie(){
		double movieRentAmount = adminDAO.getRentAmountforMovie();
		return movieRentAmount;
	}

	public double getMonthlyFeesForPremiumMember(){
		double monthlyFees = adminDAO.getMonthlyFeesForPremiumMember();
		return monthlyFees;
	}

}
