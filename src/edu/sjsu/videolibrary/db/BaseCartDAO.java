package edu.sjsu.videolibrary.db;

import java.util.List;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.ItemAlreadyInCartException;
import edu.sjsu.videolibrary.model.ItemOnCart;

public abstract class BaseCartDAO extends VideoLibraryDAO {
	public BaseCartDAO(String transactionId) {
		super(transactionId);
	}

	public abstract String addToCart (int movieId, int membershipId) throws ItemAlreadyInCartException, InternalServerException;
	
	public abstract void deleteFromCart (int movieId, int membershipId) throws InternalServerException;

	public abstract List<ItemOnCart> listCartItems (int membershipId) throws InternalServerException;
	
	public abstract int recordPaymentTransaction (double totalAmount, int membershipId) throws InternalServerException;	
	
	public abstract void recordMovieTransaction(int movieId, int transactionId) throws InternalServerException;
	
	public abstract void updateReturnDate(int movieId, int transactionId) throws InternalServerException;
	
	public abstract void deleteCart(int membershipId) throws InternalServerException;
}
