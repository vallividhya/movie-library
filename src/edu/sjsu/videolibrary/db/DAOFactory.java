package edu.sjsu.videolibrary.db;

public class DAOFactory {
	
	private DAOFactory(){
		
	}
	
	enum DAOObjectType {
		Simple,
		Prepared,
		StoredProc
	};
	
	static final DAOObjectType currentObj = DAOObjectType.Prepared;

	public static BaseAdminDAO getAdminDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleAdminDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			return new PreparedStatAdminDAO();
		} else {
			return new StoredProcAdminDAO();
		}
	}

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
	
	public static BaseUserDAO getUserDAO(VideoLibraryDAO dao) {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleUserDAO(dao);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			return new PreparedStatUserDAO(dao);
		} else {
			return new StoredProcUserDAO(dao);
		}
	}

	public static BaseMovieDAO getMovieDAO() {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleMovieDAO();
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			return new PreparedStatMovieDAO();
		} else {
			return new StoredProcMovieDAO();
		}
	}

	public static BaseMovieDAO getMovieDAO(VideoLibraryDAO dao) {
		if( currentObj.equals(DAOObjectType.Simple)) {
			return new SimpleMovieDAO(dao);
		} else if( currentObj.equals(DAOObjectType.Prepared)) {
			return new PreparedStatMovieDAO(dao);
		} else {
			return new StoredProcMovieDAO(dao);
		}
	}
}
