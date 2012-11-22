package edu.sjsu.videolibrary.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebService;

import org.eclipse.jdt.internal.compiler.apt.model.Factory;

import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.db.BaseAdminDAO;
import edu.sjsu.videolibrary.db.BaseCartDAO;
import edu.sjsu.videolibrary.db.BaseMovieDAO;
import edu.sjsu.videolibrary.db.BaseUserDAO;
import edu.sjsu.videolibrary.db.DAOFactory;
import edu.sjsu.videolibrary.db.SimpleMovieDAO;
import edu.sjsu.videolibrary.db.SimpleUserDAO;
import edu.sjsu.videolibrary.db.VideoLibraryDAO;
import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoCategoryFoundException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.exception.NoMovieInCategoryException;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
@WebService

public class Service {

	// Add movies to shopping cart	

	public String addItemsToCart(int membershipId, int movieId){
		String isAddedToCart = "false";
		BaseCartDAO cartDAO = DAOFactory.getCartDAO();
		try {				
			cartDAO.addToCart(movieId, membershipId);
			isAddedToCart = "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		finally {
			cartDAO.release();
		}
		return isAddedToCart;
	}

	public String deleteMovieFromCart (int movieId, int membershipId) {
		String isDeletedFromCart = "false";
		BaseCartDAO cartDAO = DAOFactory.getCartDAO();
		try {
			cartDAO.deleteFromCart(movieId, membershipId);
			isDeletedFromCart = "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			cartDAO.release();
		}
		return isDeletedFromCart;
	}

	public ItemOnCart[] viewCart(int membershipId){
		BaseCartDAO cartDAO = DAOFactory.getCartDAO();
		List<ItemOnCart> cartItemsList;
		ItemOnCart[] cartItems;
		try{
			cartItemsList	= cartDAO.listCartItems(membershipId);
			cartItems = new ItemOnCart[cartItemsList.size()];
			for (int i = 0; i < cartItemsList.size(); i++) {
				cartItems[i] = cartItemsList.get(i);
				System.out.println(cartItems[i]);
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			cartItems = null;
		}
		finally{
			cartDAO.release();
		}
		
		return cartItems;
	}


	public String checkOutMovieCart(int membershipId, String creditCardNumber) throws SQLException {
		VideoLibraryDAO videolibraryDAO = new VideoLibraryDAO();
		BaseCartDAO cartDAO = DAOFactory.getCartDAO();
		java.sql.Connection con = videolibraryDAO.getCon();
		
		// Check credit card 
		boolean isCardValid = false;
		if (creditCardNumber.length() == 16) {
			isCardValid = true;
		}
		String result = "true";
		double totalAmount = 0;
		boolean processComplete = false;
		try {
			
			con.setAutoCommit(false);
			if (isCardValid) {
				ItemOnCart[] cartItems = viewCart(membershipId); 
				System.out.println("Num of movies on Cart for this memeber = " + cartItems.length);
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
				}
				cartDAO.deleteCart(membershipId);
			}
			processComplete = true;
			
		} catch (SQLException e) {
			result = "false";
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if ( processComplete ) {
				con.commit();
			} else {
				con.rollback();
			}
			con.setAutoCommit(true);
		}
		
		
		return result;
	}
	
	/*public String signUpUser(String userId, String password, String memType,String firstName, String lastName, 
			String address, String city, String state, String zipCode,String ccNumber) throws SQLException 
			{
		return userDAO.signUpUser(userId, password, memType,new java.sql.Date(System.currentTimeMillis()), 
				firstName, lastName, address, city, state, zipCode, ccNumber, 
				new java.sql.Date(System.currentTimeMillis()));
			}*/


	public User signUpUser (String userId, String password, String memType,String firstName, String lastName, 
			String address, String city,String state, String zipCode,String ccNumber){
		User user = new User();
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		
		try{
			user = userDAO.signUpUser(userId, password, memType, firstName, lastName, address, city, state, zipCode, ccNumber);
		}
		catch(SQLException e){
			e.getMessage();
			user = null;
		}
		catch(Exception e){
			e.getMessage();
			user = null;

		}
		return user;
	}

	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException
	{
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		return userDAO.signUpAdmin(userId, password, firstName, lastName);
	}
	public String signInUser(String userId, String password) throws SQLException
	{
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		return userDAO.signInUser(userId, password);
	}
	public String signInAdmin(String userId, String password) throws SQLException
	{
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		return userDAO.signInAdmin(userId, password);
	}

	//List members
	public User [] viewMembers (String type){	
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		List <User> memberList = adminDAO.listMembers(type);
		User [] members = (User[]) memberList.toArray();
		return members;

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
		return result;
	}

	public String updatePassword(int membershipId,String oldPassword,String newPassword){
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		String result = userDAO.updatePassword(membershipId, oldPassword, newPassword);
		return result;
	}

	public String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, double rentAmount, int categoryId){
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
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			categoryName = movieDAO.listCategories();
		} catch (NoCategoryFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return categoryName;
	}

	//List movies by chosen category
	public Movie[] listMoviesByCategory(String categoryName) {
		Movie[] array = null;		
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			array = movieDAO.listMoviesByCategory(categoryName);
		} catch (NoMovieInCategoryException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//Display all Movies
	public Movie[] listAllMovies(){
		Movie[] array=null;
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			array = movieDAO.listAllMovies();
		} catch (NoMovieFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search movies by name
	public Movie[] searchByName(String userInput){
		Movie[] array = null;
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			array = movieDAO.searchByName(userInput);
		} catch (NoMovieFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search movies by banner
	public Movie[] searchByMovieBanner(String userInput){
		Movie[] array = null;
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			array = movieDAO.searchByMovieBanner(userInput);
		} catch (NoMovieFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search movies by release date
	public Movie[] searchByReleaseDate(String userInput){
		Movie[] array = null;
		BaseMovieDAO movieDAO = DAOFactory.getMovieDAO();
		try {
			array = movieDAO.searchByReleaseDate(userInput);
		} catch (NoMovieFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search user by first name
	public User[] searchUserByFirstName(String adminInput){
		User[] array = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			array = adminDAO.searchUserByFirstName(adminInput);
		} catch (NoUserFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search user by last name
	public User[] searchUserByLastName(String adminInput){
		User[] array = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			array = adminDAO.searchUserByLastName(adminInput);
		} catch (NoUserFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search user by city
	public User[] searchUserByCity(String adminInput){
		User[] array = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			array = adminDAO.searchUserByCity(adminInput);
		} catch (NoUserFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search user by state
	public User[] searchUserByState(String adminInput){
		User[] array = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			array = adminDAO.searchUserByState(adminInput);
		} catch (NoUserFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search user by membership type
	public User[] searchUserByMemberShipType(String adminInput){
		User[] array = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			array = adminDAO.searchUserByMemberShipType(adminInput);
		} catch (NoUserFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

	//search user by member id
	public User[] searchUserByMembershipId(int adminInput){
		User[] array = null;
		BaseAdminDAO adminDAO = DAOFactory.getAdminDAO();
		try {
			array = adminDAO.searchUserByMembershipId(adminInput);
		} catch (NoUserFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		} catch (InternalServerException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return array;
	}

}
