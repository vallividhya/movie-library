package edu.sjsu.videolibrary.db;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.ItemAlreadyInCartException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.model.ItemOnCart;

public class SimpleCartDAO extends BaseCartDAO {

	// Add items to cart
	public String addToCart (int movieId, int membershipId) throws ItemAlreadyInCartException, InternalServerException {
		String result = null;
		String query = "INSERT INTO videolibrary.moviecart (movieId, membershipId) VALUES ("+movieId+", "+membershipId+")";
		try {
			getMovieById (movieId, membershipId);
			throw new ItemAlreadyInCartException("Item already in cart");
		} catch (InternalServerException e) {
			e.printStackTrace();
		} catch (NoMovieFoundException e) {
			try {
				stmt.executeUpdate(query);	
				result = "true";
				System.out.println("Cart insert successful");
				System.out.println(e.getMessage());
			} catch (SQLException e1) {
				result = "false";
				throw new InternalServerException("DB error", e);
			}
		} 
		return result;
	}

	public ItemOnCart getMovieById (int movieId, int membershipId) throws InternalServerException, NoMovieFoundException {
		String query = "SELECT movieId, membershipId FROM moviecart WHERE movieId = " + movieId + " AND membershipId = " + membershipId;
		ItemOnCart cartItem = new ItemOnCart();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(!rs.isBeforeFirst()) {
				throw new NoMovieFoundException("Movie does not exist in cart");
			}
			while (rs.next()){
				cartItem.setMovieId(rs.getInt("movieId"));
				System.out.println(cartItem.getMovieId());
			}
			rs.close();
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new InternalServerException("DB error", e);
			}
		}
		return cartItem;
	}

	public void deleteFromCart (int movieId, int membershipId) throws InternalServerException {

		String query = "DELETE FROM videolibrary.moviecart WHERE movieId = "+movieId+" AND membershipId = "+membershipId+";";
		try {

			stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		}
	}

	public List<ItemOnCart> listCartItems (int membershipId) throws InternalServerException {
		String query = "SELECT mc.movieId, m.movieId, m.movieName, m.movieBanner, u.membershipType, a.amount FROM videolibrary.moviecart mc, videolibrary.movie m, videolibrary.user u, videolibrary.amountdetails a WHERE mc.membershipId = "+ membershipId+" AND mc.movieId = m.movieId AND mc.membershipId = u.membershipId AND u.membershipType = a.membershipType;";
		List<ItemOnCart> cartItems = new ArrayList<ItemOnCart>();

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			if (!rs.isBeforeFirst()){
//				System.out.println("No items in cart"); 
			} 
			while (rs.next()) {
				ItemOnCart item = new ItemOnCart();
				item.setMovieId(rs.getInt("movieId"));
				item.setMovieName(rs.getString("movieName"));
				item.setMovieBanner(rs.getString("movieBanner"));			
				item.setRentAmount(rs.getDouble("amount"));
				cartItems.add(item);
			}
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new InternalServerException("DB error", e);
			}
		}
		return cartItems;
	}

	public int recordPaymentTransaction (double totalAmount, int membershipId) throws InternalServerException {
		int transactionId = 0;
		SimpleDateFormat reFormat = new SimpleDateFormat("yyyy-MM-dd") ;
		java.util.Date utilDate = new java.util.Date(); 
		reFormat.format(utilDate);
		java.sql.Date rentDate = new java.sql.Date(utilDate.getTime());
		System.out.println("Rent date = " + rentDate);
		String paymentQuery = "INSERT INTO videolibrary.paymenttransaction (rentDate, totalDueAmount, membershipId) VALUES ('"+rentDate+"',"+totalAmount+","+membershipId+")";

		try {
			int rowCount = stmt.executeUpdate(paymentQuery, Statement.RETURN_GENERATED_KEYS);
			if (rowCount > 0) {
				System.out.println("Inserted transaction successfully in payment tx");
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				transactionId = rs.getInt(1);
				System.out.println("TransactionId = " + transactionId);
			} else {
				System.out.println("No insert transaction");
			}
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		}
		return transactionId;
	}

	public void recordMovieTransaction(int movieId, int transactionId, int membershipId) throws InternalServerException {
		
		String movieTransactionQuery = "INSERT INTO videolibrary.rentmovietransaction (movieId, transactionId, membershipId) VALUES ("+movieId+","+transactionId+"," + membershipId +")";
		try {

			stmt.executeUpdate(movieTransactionQuery);
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		}
	}
	
	public void updateReturnDate(int movieId, int membershipId) throws InternalServerException {
		
		String query = "UPDATE videolibrary.rentmovietransaction SET returnDate = NOW() WHERE movieId = "+ movieId +" AND membershipId = "+ membershipId;
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		}
	}

	public void deleteCart(int membershipId) throws InternalServerException {
		String deleteQuery = "DELETE FROM videolibrary.moviecart WHERE membershipId = " + membershipId;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			int rowCount = stmt.executeUpdate(deleteQuery);
			if (rowCount > 0) {
				System.out.println("cart delete successful");
			} 
		} catch (SQLException e) {
			throw new InternalServerException("DB error", e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new InternalServerException("DB error", e);
			}
		}
	}
}
