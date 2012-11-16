package edu.sjsu.videolibrary.test;

import java.sql.SQLException;
import java.util.LinkedList;

import edu.sjsu.videolibrary.db.AdminDAO;
import edu.sjsu.videolibrary.db.CartDAO;
import edu.sjsu.videolibrary.db.MovieDAO;
import edu.sjsu.videolibrary.db.UserDAO;
import edu.sjsu.videolibrary.db.VideoLibraryDAO;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.User;

public class Test {

	public static void main(String args[]){
		CartDAO cartDAO = new CartDAO();
		UserDAO userDAO = new UserDAO(); 
		MovieDAO movieDAO = new MovieDAO(); 
		AdminDAO adminDAO  = new AdminDAO();

		VideoLibraryDAO db = new VideoLibraryDAO();

		//		User user = adminDAO.displayUserInformation("1");
		//		System.out.println(user.getFirstName());
		//		System.out.println(user.getLastName());
		//		System.out.println(user.getAddress());
		//		System.out.println(user.getCity());
		//		System.out.println(user.getZip());
		//		System.out.println(user.getMembershipId());
		//		System.out.println(user.getMembershipType());
		//		System.out.println(user.getPassword());
		//		System.out.println(user.getStartDate());
		//		System.out.println(user.getCreditCardNumber());
		//		System.out.println(user.getLatestPaymentDate());

		//		Movie movie = adminDAO.displayMovieInformation("1");
		//		System.out.println(movie.getMovieId());
		//		System.out.println(movie.getMovieName());
		//		System.out.println(movie.getMovieBanner());
		//		System.out.println(movie.getReleaseDate());
		//		System.out.println(movie.getRentAmount());
		//		System.out.println(movie.getCatagory());
		//				

		//		double rent = adminDAO.getRentAmountforMovie();
		//		System.out.println(rent);
		//		
		//		double fees = adminDAO.getMonthlyFeesForPremiumMember();
		//		System.out.println(fees);

		//		String result = adminDAO.updateMovieInfo("2", "Lost", "Paramount", "2010-11-24", 9, 2);
		//		System.out.println(result);

		
//		try {
//		adminDAO.generateMonthlyStatement("2", 11, 2012);
//		} catch(SQLException e ) {
//			e.printStackTrace();
//		}
		
		LinkedList<StatementInfo> st = userDAO.viewStatement("2",11,2012);
		if(st != null && st.size() !=0){
			for(StatementInfo s:st){
				System.out.print(s.getMovieName()+"		");
				System.out.print(s.getTotalDueAmount()+"		");
				System.out.print(s.getRentDate()+"		");
				System.out.print(s.getReturnDate()+"		");
				System.out.println(); 
			}
		} else {
			System.out.println("Nothing to view");
		}

	}

}	


