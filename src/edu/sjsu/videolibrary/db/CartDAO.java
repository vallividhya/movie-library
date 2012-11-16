package edu.sjsu.videolibrary.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.videolibrary.model.ItemOnCart;

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
}
