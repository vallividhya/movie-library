package edu.sjsu.videolibrary.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebService;
<<<<<<< HEAD

import com.mysql.jdbc.Connection;

=======
>>>>>>> f148801017936a2896013f10606afc2182f1ba6e
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
<<<<<<< HEAD
import edu.sjsu.videolibrary.db.VideoLibraryDAO;
=======
import edu.sjsu.videolibrary.exception.InternalServerException;
>>>>>>> f148801017936a2896013f10606afc2182f1ba6e
import edu.sjsu.videolibrary.exception.NoCategoryFoundException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.exception.NoMovieInCategoryException;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
@WebService

public class Service {

<<<<<<< HEAD
	// Add movies to shopping cart.
	CartDAO cartDAO = new CartDAO();
	UserDAO userDAO = new UserDAO(); 
	MovieDAO movieDAO = new MovieDAO(); 
	AdminDAO adminDAO = new AdminDAO();
	VideoLibraryDAO videolibraryDAO = new VideoLibraryDAO();

=======
>>>>>>> f148801017936a2896013f10606afc2182f1ba6e
	// Add movies to shopping cart	

	public String addItemsToCart(int membershipId, int movieId){
		String isAddedToCart = "false";
		CartDAO cartDAO = new CartDAO();
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
		CartDAO cartDAO = new CartDAO();
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
		CartDAO cartDAO = new CartDAO();
		List<ItemOnCart> cartItemsList = cartDAO.listCartItems(membershipId);
		ItemOnCart[] cartItems = new ItemOnCart[cartItemsList.size()];
		for (int i = 0; i < cartItemsList.size(); i++) {
			cartItems[i] = cartItemsList.get(i);
			System.out.println(cartItems[i]);
		}
		return cartItems;
	}

<<<<<<< HEAD
	public String checkOutMovieCart(int membershipId, String creditCardNumber) throws SQLException {
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
	
	public String signUpUser(String userId, String password, String memType,String firstName, String lastName, 
			String address, String city, String state, String zipCode,String ccNumber) throws SQLException 
			{
		return userDAO.signUpUser(userId, password, memType,new java.sql.Date(System.currentTimeMillis()), 
				firstName, lastName, address, city, state, zipCode, ccNumber, 
				new java.sql.Date(System.currentTimeMillis()));
			}
=======
	public void checkOutMovieCart(int membershipId, String creditCardNumber) {

	}

	public User signUpUser (String userId, String password, String memType,String firstName, String lastName, 
			String address, String city,String state, String zipCode,String ccNumber){
		User user = new User();
		UserDAO userDAO = new UserDAO();
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
>>>>>>> f148801017936a2896013f10606afc2182f1ba6e
	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException
	{
		UserDAO userDAO = new UserDAO();
		return userDAO.signUpAdmin(userId, password, firstName, lastName);
	}
	public String signInUser(String userId, String password) throws SQLException
	{
		UserDAO userDAO = new UserDAO();
		return userDAO.signInUser(userId, password);
	}
	public String signInAdmin(String userId, String password) throws SQLException
	{
		UserDAO userDAO = new UserDAO();
		return userDAO.signInAdmin(userId, password);
	}

	//List members
	public User [] viewMembers (String type){	
		AdminDAO adminDAO = new AdminDAO();
		List <User> memberList = adminDAO.listMembers(type);
		User [] members = (User[]) memberList.toArray();
		return members;

	}

	//Delete an user and admin account
	public String deleteUserAccount (String userId) {
		String isDeleted = "false"; 
		AdminDAO adminDAO = new AdminDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
		MovieDAO movieDAO = new MovieDAO();
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
		MovieDAO movieDAO = new MovieDAO();
		try {
			isDeleted = movieDAO.deleteMovie(movieId);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
			e.printStackTrace();			
		}
		return isDeleted; 
	}

	public User displayUserInformation (int membershipId){
		AdminDAO adminDAO = new AdminDAO();
		User user = adminDAO.displayUserInformation(membershipId);
		return user;
	}

	public Movie displayMovieInformation (int movieId){
		AdminDAO adminDAO = new AdminDAO();
		Movie movie = adminDAO.displayMovieInformation(movieId);
		return movie;
	}

	public Transaction[] viewAccountTransactions(int membershipId){
		UserDAO userDAO = new UserDAO();
		LinkedList<Transaction> ac = userDAO.viewAccountTransactions(membershipId);
		Transaction[] trans = ac.toArray(new Transaction[0]);
		return trans;
	}

	public String makeMonthlyPayment(int membershipId){
		UserDAO userDAO = new UserDAO();
		String result = userDAO.makeMonthlyPayment(membershipId);
		return result;
	}

	public String updateUserInfo(int membershipId,String userId,String firstName, String lastName, String address, String city, String state, String zipCode, String membershipType,String creditCardNumber){
		UserDAO userDAO = new UserDAO();
		String result = userDAO.updateUserInfo(membershipId, userId, firstName, lastName, address, city, state, zipCode, membershipType, creditCardNumber);
		return result;
	}

	public String updatePassword(int membershipId,String oldPassword,String newPassword){
		UserDAO userDAO = new UserDAO();
		String result = userDAO.updatePassword(membershipId, oldPassword, newPassword);
		return result;
	}

	public String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, double rentAmount, int categoryId){
		AdminDAO adminDAO = new AdminDAO();
		String result = adminDAO.updateMovieInfo(movieId, movieName, movieBanner, releaseDate, availableCopies, categoryId);
		return result;
	}

	public String generateMonthlyStatement(int membershipId,int month,int year){
		String result = null;
		AdminDAO adminDAO = new AdminDAO();
		try {
			result = adminDAO.generateMonthlyStatement(membershipId, month, year);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public StatementInfo[] viewStatement(int membershipId,int month,int year){
		UserDAO userDAO = new UserDAO();
		StatementInfo [] stmnt = userDAO.viewStatement(membershipId, month, year).toArray(new StatementInfo[0]);
		return stmnt;
	}

	public PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(int membershipId,int month,int year){
		AdminDAO adminDAO = new AdminDAO();
		PaymentForPremiumMemInfo pymnt = adminDAO.generateMonthlyBillForPremiumMember(membershipId, month, year);
		return pymnt;
	}

	public double getRentAmountforMovie(){
		AdminDAO adminDAO = new AdminDAO();
		double movieRentAmount = adminDAO.getRentAmountforMovie();
		return movieRentAmount;
	}

	public double getMonthlyFeesForPremiumMember(){
		AdminDAO adminDAO = new AdminDAO();
		double monthlyFees = adminDAO.getMonthlyFeesForPremiumMember();
		return monthlyFees;
	}

	//List categories on home page
	public String[] listCategories(){
		String[] categoryName = null;
		MovieDAO movieDAO = new MovieDAO();
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
		MovieDAO movieDAO = new MovieDAO();
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
		MovieDAO movieDAO = new MovieDAO();
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
		MovieDAO movieDAO = new MovieDAO();
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
		MovieDAO movieDAO = new MovieDAO();
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
		MovieDAO movieDAO = new MovieDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
		AdminDAO adminDAO = new AdminDAO();
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
