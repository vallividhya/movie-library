package edu.sjsu.videolibrary.data;

public class DataLoader {

	static public void main(String args[]) throws Exception {
		SimpleCustomerData.insertsimpleMemberData();
		PremiumMemberData.insertPremiumMemberData();
		AdminUserData.insertAdminData();
	}
}
