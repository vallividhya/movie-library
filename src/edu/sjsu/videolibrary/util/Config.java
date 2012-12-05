package edu.sjsu.videolibrary.util;

import edu.sjsu.videolibrary.db.DAOFactory.DAOObjectType;

public class Config {
	public static final int poolSize = 10;
	public static final boolean poolEnabled = true;
	public static final int cacheSize = 100;
	public static final boolean cacheEnabled = true;
	public static final DAOObjectType currentObject = DAOObjectType.Simple;
	public static final Class<?> connectionPoolImpl = edu.sjsu.videolibrary.db.ConnectionPoolImpl2.class; 
}
