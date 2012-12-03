package edu.sjsu.videolibrary.model;

public class Transaction {
	
	private String purchaseDate;
	private double perMovieAmount;
	private String movieName;
	private String returnDate;
	private int transactionId;
	private int movieId;
	
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public double getPerMovieAmount() {
		return perMovieAmount;
	}
	public void setPerMovieAmount(double perMovieAmount) {
		this.perMovieAmount = perMovieAmount;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	public int getMovieId() {
		return this.movieId;
	}
	

}

