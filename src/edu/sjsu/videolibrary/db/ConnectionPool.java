package edu.sjsu.videolibrary.db;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;

import edu.sjsu.videolibrary.util.Config;

public class ConnectionPool {
	static final int poolSize = edu.sjsu.videolibrary.util.Config.poolSize;
	static final boolean poolEnabled = edu.sjsu.videolibrary.util.Config.poolEnabled;
	static IConnectionPool INSTANCE = null;
	static final HashMap<String,String> passwordHash;
	static final String USERNAME = "root";
	static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/videoLibrary";

	static {
		passwordHash = new HashMap<String,String>();
		// Add you mysql passwords here with your hostname
		passwordHash.put("Valli", "1248");
	}

	// This is the default mysql password
	static String password = "1248";

	protected ConnectionPool() throws Exception {
		String localhostname = java.net.InetAddress.getLocalHost().getHostName();
		if( passwordHash.containsKey(localhostname) ) {
			password = passwordHash.get(localhostname);
		}
	}

	synchronized public static IConnectionPool getInstance() throws Exception {
		if( INSTANCE == null ) {
			if( poolEnabled ) {
				INSTANCE = (IConnectionPool) Config.connectionPoolImpl.newInstance();
				INSTANCE.setUp();
			} else {
				INSTANCE = new ConnectionPoolDisabledImpl();
			}
		}
		return INSTANCE;
	}

	protected static Connection createNewConnection() {
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(CONNECTION_STRING, USERNAME,password);
			if (!con.isClosed()) {
				//System.out.println("");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (InstantiationException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return con;
	}
}
