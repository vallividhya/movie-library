package edu.sjsu.videolibrary.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.ItemAlreadyInCartException;
import edu.sjsu.videolibrary.model.ItemOnCart;

public class StoredProcCartDAO extends BaseCartDAO {

	public StoredProcCartDAO(String transactionId) {
		super(transactionId);
	}

	// check this function tomorrow
	@Override
	public String addToCart(int movieId, int membershipId)
			throws ItemAlreadyInCartException, InternalServerException {
		String result = null;
		return result;
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFromCart(int movieId, int membershipId)
			throws InternalServerException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ItemOnCart> listCartItems(int membershipId)
			throws InternalServerException {
		String query = "call videolibrary.listCartItems(" + membershipId + ")";
		System.out.println("MembershipId" + membershipId);
		System.out.println("Query" + query);
		List<ItemOnCart> cartItems = new ArrayList<ItemOnCart>();

		try {
			stmt.execute(query);
			ResultSet rs = stmt.getResultSet();
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

	@Override
	public int recordPaymentTransaction(double totalAmount, int membershipId)
			throws InternalServerException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public void updateReturnDate(int movieId, int transactionId)
			throws InternalServerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCart(int membershipId) throws InternalServerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void recordMovieTransaction(int movieId, int transactionId,
			int membershipId) throws InternalServerException {
		// TODO Auto-generated method stub
		
	}

}
