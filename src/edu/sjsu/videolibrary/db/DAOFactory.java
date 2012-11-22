package edu.sjsu.videolibrary.db;

public class DAOFactory {
	
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

}
