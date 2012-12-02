package edu.sjsu.videolibrary.data;

import edu.sjsu.videolibrary.db.BaseUserDAO;
import edu.sjsu.videolibrary.db.DAOFactory;
import edu.sjsu.videolibrary.util.Utils;

public class AdminUserData {
	
	public static void insertAdminData() throws Exception {
		String userData[][] = {
				{"admin@admin.com","admin","Juan","Anthony"}
		};
		BaseUserDAO userDAO  = DAOFactory.getUserDAO();
		try {
			for(String[] user : userData) {
			userDAO.signUpAdmin(user[0], Utils.encryptPassword(user[1]), user[2], user[3]);
			}
		} finally {
			userDAO.release();
		}
	}
	
	public static void main(String args[]) throws Exception {
		insertAdminData();
	}

}
