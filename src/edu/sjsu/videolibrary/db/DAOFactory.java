package edu.sjsu.videolibrary.db;

public class DAOFactory {
	
	private DAOFactory(){
		
	}
	
	enum DAOObjectType {
		Simple,
		Prepared,
		StoredProc
	};
	
	static final DAOObjectType currentObj = DAOObjectType.Simple;

	public static BaseAdminDAO getAdminDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleAdminDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleAdminDAO();
		} else {
			// TODO: Need to change this
			return new SimpleAdminDAO();
		}
	}

	public static BaseAdminDAO getAdminDAO(String transactionId) {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleAdminDAO(transactionId);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleAdminDAO(transactionId);
		} else {
			// TODO: Need to change this
			return new SimpleAdminDAO(transactionId);
		}
	}

	public static BaseCartDAO getCartDAO(String transactionId) {
		if(currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleCartDAO(transactionId);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleCartDAO(transactionId);
		} else {
			// TODO: Need to change this
			return new SimpleCartDAO(transactionId);
		}
	}
	
	public static BaseUserDAO getUserDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleUserDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleUserDAO();
		} else {
			// TODO: Need to change this
			return new SimpleUserDAO();
		}
	}
	
	public static BaseUserDAO getUserDAO(String transactionId) {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleUserDAO(transactionId);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleUserDAO(transactionId);
		} else {
			// TODO: Need to change this
			return new SimpleUserDAO(transactionId);
		}
	}

	public static BaseMovieDAO getMovieDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleMovieDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleMovieDAO();
		} else {
			return new StoredProcMovieDAO();
		}
	}

	public static BaseMovieDAO getMovieDAO(String transactionId) {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleMovieDAO(transactionId);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleMovieDAO(transactionId);
		} else {
			// TODO: Need to change this
			return new SimpleMovieDAO(transactionId);
		}
	}
}
