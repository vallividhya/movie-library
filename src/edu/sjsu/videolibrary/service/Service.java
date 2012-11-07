package edu.sjsu.videolibrary.service;

import java.util.List;

import javax.jws.WebService;

import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.db.CartDAO;
@WebService

public class Service {
	
	// Add movies to shopping cart.
	CartDAO cartDAO = new CartDAO();
	
	public boolean addItemsToCart(int membershipId, int movieId){
		boolean isAddedToCart = false;
		try {
			cartDAO.addToCart(movieId, membershipId);
			isAddedToCart = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}  
		return isAddedToCart;
	}
	
	public boolean deleteMovieFromCart (int movieId, int membershipId) {
		boolean isDeletedFromCart = false;
		try {
			cartDAO.deleteFromCart(movieId, membershipId);
			isDeletedFromCart = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}  
		return isDeletedFromCart;
	}
	
	public ItemOnCart[] viewCart(int membershipId){
		List<ItemOnCart> cartItemsList = cartDAO.listCartItems(membershipId);
		ItemOnCart[] cartItems = new ItemOnCart[cartItemsList.size()];
		for (int i = 0; i < cartItemsList.size(); i++) {
			cartItems[i] = cartItemsList.get(i);
			System.out.println(cartItems[i]);
		}
		return cartItems;
	}
}
