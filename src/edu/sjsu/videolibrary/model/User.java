package edu.sjsu.videolibrary.model;

public class User {
	
	private int membershipId;
	private String userId; 
	private String password;
	private String firstName;
	private String lastName;
	
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
	
	
	
	
}
