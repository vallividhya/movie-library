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
		String query = "INSERT INTO videolibrary.moviecart (M_Id, U_Id) VALUES (?, ?)";
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
		String query = "DELETE FROM videolibrary.moviecart WHERE M_Id = ? AND U_Id = ?";
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
		PreparedStatement preparedStmt = null;
		List<ItemOnCart> cartItems = new ArrayList<ItemOnCart>();
		String query = "SELECT mc.M_Id, m.MovieId, m.MovieName, m.RentAmount, m.C_Id, c.CategoryName " +
						"FROM videolibrary.moviecart mc, videolibrary.movie m, videolibrary.category c " +
						"WHERE mc.U_Id = ? AND mc.M_Id = m.MovieId AND m.C_Id = c.CategoryId";
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, membershipId);
			System.out.println(preparedStmt.toString());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				ItemOnCart item = new ItemOnCart();
				item.setMovieId(rs.getInt("M_Id"));
				item.setMovieName(rs.getString("MovieName"));
				item.setCategory(rs.getString("CategoryName"));
				item.setRentAmount(rs.getDouble("RentAmount"));
				cartItems.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartItems;
	}
}
