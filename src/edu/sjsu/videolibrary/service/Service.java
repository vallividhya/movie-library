package edu.sjsu.videolibrary.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;

import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.States;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.db.BaseAdminDAO;
import edu.sjsu.videolibrary.db.BaseCartDAO;
import edu.sjsu.videolibrary.db.BaseMovieDAO;
import edu.sjsu.videolibrary.db.BaseUserDAO;
import edu.sjsu.videolibrary.db.Cache;
import edu.sjsu.videolibrary.db.DAOFactory;
import edu.sjsu.videolibrary.db.TransactionManager;
import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.ItemAlreadyInCartException;
import edu.sjsu.videolibrary.exception.NoCategoryFoundException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.exception.NoMovieInCategoryException;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
@WebService

public class Service {

	Cache cache = Cache.getInstance();

	// Add movies to shopping cart	
	public boolean addItemsToCart(int membershipId, int movieId) {
		boolean isAddedToCart = false;

		String dbTransaction = null;
		try {				
			dbTransaction = startTransaction();
			BaseCartDAO cartDAO = DAOFactory.getCartDAO(dbTransaction);

			cartDAO.addToCart(movieId, membershipId);
			System.out.println("Added to cart successfully");
			commitTransaction(dbTransaction);

			isAddedToCart = true;
			cache.invalidate("viewCart"+membershipId);
		} catch (ItemAlreadyInCartException e) {
			System.out.println(e.getMessage());
			try {
				commitTransaction(dbTransaction);
			} catch (InternalServerException e1) {
				e.printStackTrace();
			}
		} catch (InternalServerException e) {
			e.printStackTrace();
			try {
				rollbackTransaction(dbTransaction);
			} catch (InternalServerException e1) {
				e.printStackTrace();
			}
		}

		return isAddedToCart;
	}

	public boolean deleteMovieFromCart (int movieId, int membershipId) {
		boolean isDeletedFromCart = false;

		String dbTransaction = null;
		try {
			dbTransaction = startTransaction();
			BaseCartDAO cartDAO = DAOFactory.getCartDAO(dbTransaction);
			cartDAO.deleteFromCart(movieId, membershipId);
			commitTransaction(dbTransaction);

			isDeletedFromCart = true;
		} catch (InternalServerException e) {
			e.printStackTrace();
			try {
				rollbackTransaction(dbTransaction);
			} catch (InternalServerException e1) {
				e.printStackTrace();
			}
		}

		return isDeletedFromCart;
	}

	public ItemOnCart[] viewCart(int membershipId) {
		List<ItemOnCart> cartItemsList;
		ItemOnCart[] cartItems = null;

		cartItems = (ItemOnCart[])cache.get("viewCart" + membershipId);
		if(cartItems == null){
			String dbTransaction = null;
			try {
				dbTransaction = startTransaction();
				BaseCartDAO cartDAO = DAOFactory.getCartDAO(dbTransaction);
				cartItemsList	= cartDAO.listCartItems(membershipId);
				cartItems = new ItemOnCart[cartItemsList.size()];

				System.out.println("I've got " + cartItems.length + " items with me!");

				for (int i = 0; i < cartItemsList.size(); i++) {
					cartItems[i] = cartItemsList.get(i);
					System.out.println(cartItems[i].getMovieName());
				}	
				cache.put("viewCart" + membershipId, cartItems);
			} catch (InternalServerException e) {
				e.printStackTrace();
			} finally {
				try {
					commitTransaction(dbTransaction);
				} catch (InternalServerException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("I'm done");
		return cartItems;
	}

	public boolean checkOutMovieCart(int membershipId, String creditCardNumber) {

		// Check credit card 
		boolean isCardValid = false;
		if (creditCardNumber.length() == 16) {
			isCardValid = true;
		}

		double totalAmount = 0;
		boolean processComplete = false;
		String dbTransaction = null;
		try {
			dbTransaction = startTransaction();
			BaseCartDAO cartDAO = DAOFactory.getCartDAO(dbTransaction);
			BaseMovieDAO movieDAO = DAOFactory.getMovieDAO(dbTransaction);
			if (isCardValid) {
				ItemOnCart[] cartItems = viewCart(membershipId); 
				System.out.println("Num of movies on Cart for this member = " + cartItems.length);
				int[] movieId = new int[cartItems.length];
				double[] rentAmount = new double[cartItems.length];
				for (int i = 0; i< cartItems.length; i++) {
					if (cartItems[i] != null) {
						movieId[i] = cartItems[i].getMovieId();
						rentAmount[i] = cartItems[i].getRentAmount();
						totalAmount = totalAmount + rentAmount[i];
						System.out.println("Movie Id [" + i + "] = " + movieId[i] );
					}
				}
				System.out.println(totalAmount + " = total Amount");
				System.out.println(membershipId + " = memId");
				int transactionId = cartDAO.recordPaymentTransaction(totalAmount, membershipId);
				for (int i = 0; i < movieId.length; i++) {
					cartDAO.recordMovieTransaction(movieId[i], transactionId);
					movieDAO.updateCopiesCount(movieId[i]);
				}
				cartDAO.deleteCart(membershipId);
			}

			commitTransaction(dbTransaction);
			cache.invalidate("viewCart"+membershipId);
			processComplete = true;
		} catch (InternalServerException e) {
			e.printStackTrace();
			try {
				rollbackTransaction(dbTransaction);
			} catch (InternalServerException e1) {
				e.printStackTrace();
			}
		}

		return processComplete;
	}

	// Return Movie

	public void returnMovie () {

	}

	public String signUpUser (String userId, String password, String memType,String firstName, String lastName, 
			String address, String city,String state, String zipCode,String ccNumber){
		String result = null;
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();

		try{
			result = userDAO.signUpUser(userId, password, memType, firstName, lastName, address, city, state, zipCode, ccNumber);
		}
		catch(SQLException e){
			e.getMessage();
			result = "false";
		}
		catch(Exception e){
			e.getMessage();
			result = "false";

		}
		return result;
	}

	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException
	{
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		return userDAO.signUpAdmin(userId, password, firstName, lastName);
	}
	public User signInUser(String userId, String password) throws SQLException
	{
		User user = new User();
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		String result = (String)cache.get("signInUser"+userId+password);
		if(result == null){		
			user = userDAO.signInUser(userId, password);
			if(user != null){
				cache.put("signInUser"+userId+password, user);
			}
		}
		return user;
	}
	public Admin signInAdmin (String userId, String password) throws SQLException
	{
		BaseAdminDAO adminDAO  = DAOFactory.getAdminDAO();
		return adminDAO.signInAdminObject(userId, password);
	}

	//List members
	public User [] viewMembers (String type){	
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		List <User> memberList = adminDAO.listMembers(type);
		User [] members = (User[]) memberList.toArray();
		return members;

	}

	public Admin [] viewAdmins () {	
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		List <Admin> memberList = adminDAO.listAdmins();
		Admin [] admins = memberList.toArray(new Admin[0]);
		return admins;
	}


	//Delete an user and admin account
	public String deleteUserAccount (String userId) {
		String isDeleted = "false"; 
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
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
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
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
		cache.invalidate("listAllMovies");
		cache.invalidatePrefix("searchMovie");
		cache.invalidatePrefix("listMoviesByCategory");
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
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
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			isDeleted = movieDAO.deleteMovie(movieId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}

	public User displayUserInformation (int membershipId){
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		User user = adminDAO.displayUserInformation(membershipId);
		return user;
	}

	public Movie displayMovieInformation (int movieId){
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		Movie movie = adminDAO.displayMovieInformation(movieId);
		return movie;
	}

	public Transaction[] viewAccountTransactions(int membershipId){
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		LinkedList<Transaction> ac = userDAO.viewAccountTransactions(membershipId);
		Transaction[] trans = ac.toArray(new Transaction[0]);
		return trans;
	}

	public String makeMonthlyPayment(int membershipId){
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		String result = userDAO.makeMonthlyPayment(membershipId);
		return result;
	}

	public String updateUserInfo(int membershipId,String userId,String firstName, String lastName, String address, String city, String state, String zipCode, String membershipType,String creditCardNumber){
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		String result = userDAO.updateUserInfo(membershipId, userId, firstName, lastName, address, city, state, zipCode, membershipType, creditCardNumber);
		cache.invalidatePrefix("signInUser"+userId);
		cache.invalidatePrefix("searchUser");
		return result;
	}

	public String updatePassword(int membershipId,String oldPassword,String newPassword){
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		String result = userDAO.updatePassword(membershipId, oldPassword, newPassword);
		cache.invalidatePrefix("signInUser"); //invalidates caching for all users
		return result;
	}

	public String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId){
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		String result = adminDAO.updateMovieInfo(movieId, movieName, movieBanner, releaseDate, availableCopies, categoryId);
		return result;
	}

	public String generateMonthlyStatement(int membershipId,int month,int year){
		String result = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			result = adminDAO.generateMonthlyStatement(membershipId, month, year);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public StatementInfo[] viewStatement(int membershipId,int month,int year){
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		StatementInfo [] stmnt = userDAO.viewStatement(membershipId, month, year).toArray(new StatementInfo[0]);
		return stmnt;
	}

	public PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(int membershipId,int month,int year){
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		PaymentForPremiumMemInfo pymnt = adminDAO.generateMonthlyBillForPremiumMember(membershipId, month, year);
		return pymnt;
	}

	public double getRentAmountforMovie(){
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		double movieRentAmount = adminDAO.getRentAmountforMovie();
		return movieRentAmount;
	}

	public double getMonthlyFeesForPremiumMember(){
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		double monthlyFees = adminDAO.getMonthlyFeesForPremiumMember();
		return monthlyFees;
	}

	//List categories on home page
	public String[] listCategories(){
		String[] categoryName = null;
		categoryName = (String[])cache.get("listCategories");
		if( categoryName == null ) {
			BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
			try {
				categoryName = movieDAO.listCategories();
				cache.put("listCategories", categoryName);
			} catch (NoCategoryFoundException e) {
				e.getLocalizedMessage();
				e.printStackTrace();
			} catch (InternalServerException e) {
				e.getLocalizedMessage();
				e.printStackTrace();
			}
		}
		return categoryName;
	}

	//List movies by chosen category
	public Movie[] listMoviesByCategory(String categoryName) {
		Movie[] array = null;
		array = (Movie[]) cache.get("listMoviesByCategory" + categoryName);
		if( array == null ) {
			BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
			try {
				array = movieDAO.listMoviesByCategory(categoryName);
				cache.put("listMoviesByCategory" + categoryName, array);
			} catch (NoMovieInCategoryException e) {
				e.getLocalizedMessage();
				e.printStackTrace();
			} catch (InternalServerException e) {
				e.getLocalizedMessage();
				e.printStackTrace();
			}
		}
		return array;
	}

	//Display all Movies
	public Movie[] listAllMovies(){
		Movie[] array=null;
		array = (Movie[]) cache.get("listAllMovies");
		if( array == null ) {
			BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
			try {
				array = movieDAO.listAllMovies();
				cache.put("listAllMovies", array);
			} catch (NoMovieFoundException e) {
				e.getLocalizedMessage();
				e.printStackTrace();
			} catch (InternalServerException e) {
				e.getLocalizedMessage();
				e.printStackTrace();
			}
		}
		return array;
	}

	//search movies by name
	

	// Search User by any attribute 

	public User[] searchUser (String membershipId, String userId,
			String membershipType, String startDate, String firstName,
			String lastName, String address, String city, String state,
			String zipCode) {


		User[] users = null;
		String dbTransaction = null;
		users = (User[])cache.get("searchUser"+membershipId+ userId+ membershipType+startDate+ firstName+ lastName+ address+ city+ state+ zipCode);
		if(users == null){
			try {				
				dbTransaction = startTransaction();
				BaseAdminDAO adminDAO = DAOFactory.getAdminDAO(dbTransaction);
				users = adminDAO.searchUser(membershipId, userId, membershipType, startDate, firstName, lastName, address, city, state, zipCode);
				System.out.println("Users : ");
				for (User i:users) {
					System.out.println(i.getMembershipId() + " | " + i.getFirstName() + i.getLastName() +  " | " + i.getMembershipType() + " | " + i.getState() );
				}
				cache.put("searchUser"+membershipId+ userId+ membershipType+startDate+ firstName+ lastName+ address+ city+ state+ zipCode, users);
			} catch (NoUserFoundException e) {
				System.out.println(e.getMessage());
			} catch (InternalServerException e) {
				e.printStackTrace();
			} finally {
				try {
					commitTransaction(dbTransaction);
				} catch (InternalServerException e) {
					e.printStackTrace();
				}
			}
		}
		return users;
	}
	

	public Admin displayAdminInformation (String adminId) {
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		Admin admin = null;
		try {
			admin = adminDAO.displayAdminInformation(adminId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin; 
	}

	public String updateAdminInfo (String adminId,String firstName, String lastName, String password){
		BaseAdminDAO adminDAO  = DAOFactory.getAdminDAO();
		String result = null;
		try {
			result = adminDAO.updateAdminInfo(adminId, firstName, lastName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	public String updateUserPassword (int membershipId, String newPassword) {
		BaseAdminDAO adminDAO  = DAOFactory.getAdminDAO();
		String result = null;
		try {
			result = adminDAO.updateUserPassword(membershipId, newPassword);
			cache.invalidatePrefix("signInUser"); //invalidate caching for all users
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;		
	}

	public String [] getStates () { 
		States states = new States();
		return states.getStates();
	}

	private String startTransaction() throws InternalServerException {
		try {
			return TransactionManager.INSTANCE.startTransaction();
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	private void commitTransaction(String dbTransaction) throws InternalServerException {
		try {
			TransactionManager.INSTANCE.commitTransaction(dbTransaction);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	private void rollbackTransaction(String dbTransaction) throws InternalServerException {
		try {
			TransactionManager.INSTANCE.rollbackTransaction(dbTransaction);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}


	public Movie[] searchMovie(String movieName, String movieBanner, String releaseDate){
		Movie[] array = null;
		array = (Movie[])cache.get("searchMovie" + movieName + movieBanner + releaseDate);
		if( array == null ) {
			BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
			try {
				array = movieDAO.searchMovie(movieName, movieBanner, releaseDate);
				cache.put("searchMovie" + movieName + movieBanner + releaseDate, array);
			} catch (Exception e) {			
				e.printStackTrace();
			}
		}
		return array;
	}

}
