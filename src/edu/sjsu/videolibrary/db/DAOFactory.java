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
	
	public static BaseCartDAO getCartDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleCartDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			// TODO: Need to change this
			return new SimpleCartDAO();
		} else {
			// TODO: Need to change this
			return new SimpleCartDAO();
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

}
