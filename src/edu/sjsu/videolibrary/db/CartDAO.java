package edu.sjsu.videolibrary.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.model.Transaction;

public class CartDAO extends VideoLibraryDAO {
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
	
	public void checkOutCart(int membershipId, double totalAmount, int movieId) {
		PreparedStatement updatePayment = null;
		PreparedStatement updateMovieDetails = null; 
		String paymentQuery = "INSERT INTO videolibrary.paymenttransaction (rentDate, totalDueAmount, membershipId) VALUES (NOW(), ?, ?)";
		String movieTransactionQuery = "INSERT INTO videolibrary.rentmovietransaction (movieId, transactionId) VALUES (?, ?)";
		
		try {
			con.setAutoCommit(false);
			updatePayment = con.prepareStatement(paymentQuery);
			updatePayment.setDouble(1, totalAmount);
			updatePayment.setInt(2, membershipId);
			updatePayment.executeUpdate();
			String getTransactionIdQuery = "SELECT transactionId FROM videolibrary.paymenttransaction WHERE membershipId = " + membershipId + " AND totalDueAmount = " + totalAmount + " AND rentDate = NOW()";
			ResultSet rs = stmt.executeQuery(getTransactionIdQuery);
			Transaction transaction = new Transaction();
			if (!rs.isBeforeFirst()){
				System.out.println("No transactionId retrieved"); 
			} 
			while (rs.next()) {
				transaction.setTransactionId(rs.getInt("transactionId"));
				updateMovieDetails = con.prepareStatement(movieTransactionQuery);
				updateMovieDetails.setInt(1, movieId);
				updateMovieDetails.setInt(2, transaction.getTransactionId());
				updateMovieDetails.executeUpdate();
				
			}
			String deleteQuery = "DELETE FROM videolibrary.moviecart WHERE membershipId = " + membershipId;
			try {
				int rowCount = stmt.executeUpdate(deleteQuery);
				if (rowCount > 0) {
					System.out.println("cart delete successful");
				} 
			
			} finally {
				if (updatePayment != null) {
					updatePayment.close();
				}
				if (updateMovieDetails != null) {
					updateMovieDetails.close();
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		
	}
	
	public void recordPaymentTransaction (double totalAmount, int membershipId) {
		PreparedStatement preparedStmt = null;
		String paymentQuery = "INSERT INTO videolibrary.paymenttransaction (rentDate, totalDueAmount, membershipId) VALUES (NOW(), ?, ?)";
		try {
			preparedStmt = con.prepareStatement(paymentQuery);
			preparedStmt.setDouble(1, totalAmount);
			preparedStmt.setInt(2, membershipId);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
}
