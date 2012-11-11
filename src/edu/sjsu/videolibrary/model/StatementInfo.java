package edu.sjsu.videolibrary.model;

public class StatementInfo {
	
	private String movieId;
	private String movieName;
	private String rentDate;
	private String returnDate;
	private String totalDueAmount;
	private String monthlyPayment;

	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getRentDate() {
		return rentDate;
	}
	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getTotalDueAmount() {
		return totalDueAmount;
	}
	public void setTotalDueAmount(String rentAmount) {
		this.totalDueAmount = totalDueAmount;
	}
	public String getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}


}
