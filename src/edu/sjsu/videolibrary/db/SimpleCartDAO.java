package edu.sjsu.videolibrary.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import edu.sjsu.videolibrary.model.ItemOnCart;

public class SimpleCartDAO extends BaseCartDAO {
	// Add items to cart
	public void addToCart (int movieId, int membershipId) {
		PreparedStatement preparedStmt = null;
		String query = "INSERT INTO videolibrary.moviecart (movieId, membershipId) VALUES (?, ?)";
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, movieId);
			preparedStmt.setInt(2, membershipId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFromCart (int movieId, int membershipId) {
		PreparedStatement preparedStmt = null;
		String query = "DELETE FROM videolibrary.moviecart WHERE movieId = ? AND membershipId = ?";
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, movieId);
			preparedStmt.setInt(2, membershipId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ItemOnCart> listCartItems (int membershipId){
		String query = "SELECT mc.movieId, m.movieId, m.movieName, m.movieBanner, u.membershipType, a.amount FROM videolibrary.moviecart mc, videolibrary.movie m, videolibrary.user u, videolibrary.amountdetails a WHERE mc.membershipId = "+ membershipId+" AND mc.movieId = m.movieId AND mc.membershipId = u.membershipId AND u.membershipType = a.membershipType;";
		System.out.println("MembershipId" + membershipId);
		System.out.println("Query" + query);
		List<ItemOnCart> cartItems = new ArrayList<ItemOnCart>();
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (!rs.isBeforeFirst()){
				System.out.println("No items in cart"); 
			} 
			while (rs.next()) {
				ItemOnCart item = new ItemOnCart();
				item.setMovieId(rs.getInt("movieId"));
				item.setMovieName(rs.getString("movieName"));
				item.setMovieBanner(rs.getString("movieBanner"));
				item.setRentAmount(rs.getDouble("amount"));
				cartItems.add(item);
			}
			System.out.println("Num of items in cart = " + cartItems.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartItems;
	}

	
	public int recordPaymentTransaction (double totalAmount, int membershipId) {
		int transactionId = 0;
			
		SimpleDateFormat reFormat = new SimpleDateFormat("yyyy-MM-dd") ;
		java.util.Date utilDate = new java.util.Date(); 
		reFormat.format(utilDate);
		java.sql.Date rentDate = new java.sql.Date(utilDate.getTime());
	    System.out.println("Rent date = " + rentDate);
		//String paymentQuery = "INSERT INTO videolibrary.paymenttransaction (rentDate, totalDueAmount, membershipId) VALUES (?, ?, ?)";
	    String paymentQuery = "INSERT INTO videolibrary.paymenttransaction (rentDate, totalDueAmount, membershipId) VALUES (NOW(), 4.5, 1)";
		try {
			
			//preparedStmt = con.prepareStatement(paymentQuery);
			//preparedStmt.setDate(1,rentDate);
			//preparedStmt.setDouble(2, totalAmount);
			//preparedStmt.setInt(3, membershipId);
			System.out.println("recordPayment insert Query " + paymentQuery);
			int rowCount = stmt.executeUpdate(paymentQuery, Statement.RETURN_GENERATED_KEYS);
			if (rowCount > 0) {
				System.out.println("Inserted transaction successfully in payment tx");
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				transactionId = rs.getInt(1);
				System.out.println("TransactionId *************** = " + transactionId);
			} else {
				System.out.println("No insert transaction");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactionId;
	}
	
	
	
	
	public void recordMovieTransaction(int movieId, int transactionId) {
		PreparedStatement preparedStmt = null;
		String movieTransactionQuery = "INSERT INTO videolibrary.rentmovietransaction (movieId, transactionId) VALUES (?, ?)";
		try {
			preparedStmt = con.prepareStatement(movieTransactionQuery);
			preparedStmt.setInt(1, movieId);
			preparedStmt.setInt(2, transactionId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateReturnDate(int movieId, int transactionId) {
		PreparedStatement preparedStmt = null;
		String query = "UPDATE videolibrary.rentmovietransaction SET returnDate = NOW() WHERE movieId = ? AND transactionId = ?";
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, movieId);
			preparedStmt.setInt(2, transactionId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteCart(int membershipId) {
		String deleteQuery = "DELETE FROM videolibrary.moviecart WHERE membershipId = " + membershipId;
		try {
			int rowCount = stmt.executeUpdate(deleteQuery);
			if (rowCount > 0) {
				System.out.println("cart delete successful");
			} 
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
