package edu.sjsu.videolibrary.model;

public class User {
	
	private int membershipId;
	private String userId; 
	private String password;
	private String firstName;
	private String lastName;
	private String StartDate;
	private String MembershipType;
	private String Address;
	private String 	City;
	private String 	State;
	private String 	Zip;
	private String CreditCardNumber;
	private String latestPaymentDate;
	private String movieList[];
	private int rentedMovies;
	
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getMembershipType() {
		return MembershipType;
	}
	public void setMembershipType(String membershipType) {
		MembershipType = membershipType;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		Zip = zip;
	}
	public String getCreditCardNumber() {
		return CreditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		CreditCardNumber = creditCardNumber;
	}
	public String getLatestPaymentDate() {
		return latestPaymentDate;
	}
	public void setLatestPaymentDate(String latestPaymentDate) {
		this.latestPaymentDate = latestPaymentDate;
	}
	public String[] getMovieList() {
		return movieList;
	}
	public void setMovieList(String[] movieList) {
		this.movieList = movieList;
	}

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}
	public void setRentedMovies(int rentedMovies) {
		this.rentedMovies = rentedMovies;
	}
	public int getRentedMovies() {
		return rentedMovies;
	} 
	
	
	
	
}
