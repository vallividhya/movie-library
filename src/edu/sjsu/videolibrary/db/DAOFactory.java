package edu.sjsu.videolibrary.db;

public class DAOFactory {
	
	private DAOFactory(){
		
	}
	
	enum DAOObjectType {
		Simple,
		Prepared,
		StoredProc
	};
	
	static final DAOObjectType currentObj = DAOObjectType.StoredProc;

	public static BaseAdminDAO getAdminDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleAdminDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
		
			return new PreparedStatAdminDAO();
		} else {
			
			return new StoredProcAdminDAO();
		}
	}

//	public static BaseAdminDAO getAdminDAO(String transactionId) {
//		if( currentObj.equals(DAOObjectType.Simple)) {
//			return new SimpleAdminDAO(transactionId);
//		} else if( currentObj.equals(DAOObjectType.Prepared)) {
//			// TODO: Need to change this
//			return new SimpleAdminDAO(transactionId);
//		} else {
//			// TODO: Need to change this
//			return new SimpleAdminDAO(transactionId);
//		}
//	}

	public static BaseCartDAO getCartDAO() {
		if(currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleCartDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
		
			return new PreparedStatCartDAO();
		} else {
			
			return new StoredProcCartDAO();
		}
	}
	
	public static BaseUserDAO getUserDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleUserDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			
			return new PreparedStatUserDAO();
		} else {
			return new StoredProcUserDAO();
		}
	}
	
//	public static BaseUserDAO getUserDAO(String transactionId) {
//		if( currentObj.equals(DAOObjectType.Simple)) {
//			return new SimpleUserDAO(transactionId);
//		} else if( currentObj.equals(DAOObjectType.Prepared)) {
//			return new PreparedStatUserDAO(transactionId);
//		} else {
//			return new StoredProcUserDAO(transactionId);
//		}
//	}

	public static BaseMovieDAO getMovieDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleMovieDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			return new PreparedStatMovieDAO();
		} else {
			return new StoredProcMovieDAO();
		}
	}

	public static BaseMovieDAO getMovieDAO(String transactionId) {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleMovieDAO(transactionId);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			return new PreparedStatMovieDAO(transactionId);
		} else {
			return new StoredProcMovieDAO();
		}
	}
}
