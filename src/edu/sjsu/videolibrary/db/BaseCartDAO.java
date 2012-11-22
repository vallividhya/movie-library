package edu.sjsu.videolibrary.db;

import java.util.List;

import edu.sjsu.videolibrary.model.ItemOnCart;

public abstract class BaseCartDAO extends VideoLibraryDAO {
	public abstract void addToCart (int movieId, int membershipId);
	
	public abstract void deleteFromCart (int movieId, int membershipId);

	public abstract List<ItemOnCart> listCartItems (int membershipId);
	
	public abstract int recordPaymentTransaction (double totalAmount, int membershipId);	
	
	public abstract void recordMovieTransaction(int movieId, int transactionId);
	
	public abstract void updateReturnDate(int movieId, int transactionId);
	
	public abstract void deleteCart(int membershipId);
}
