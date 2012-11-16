package edu.sjsu.videolibrary.model;

public class PaymentForPremiumMemInfo {
	
	private double monthlyPaymentAmount;
	private String paymentDate;
	private String paymentStatus;
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public double getMonthlyPaymentAmount() {
		return monthlyPaymentAmount;
	}
	public void setMonthlyPaymentAmount(double monthlyPaymentAmount) {
		this.monthlyPaymentAmount = monthlyPaymentAmount;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	

}
