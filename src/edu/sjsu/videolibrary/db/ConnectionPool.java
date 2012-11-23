package edu.sjsu.videolibrary.db;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
	static final int poolSize = 5;
	static final boolean poolEnabled = false;
	static ConnectionPool INSTANCE = null;
	static final int MAX_BLOCK_TIME = 10;
	static final TimeUnit BLOCK_TIME_UNIT = TimeUnit.MILLISECONDS;

	LinkedBlockingQueue <Connection> availablePool = 
			new LinkedBlockingQueue<Connection>(poolSize);

	private ConnectionPool() throws Exception {
		if( poolEnabled ) {
			for( int i  = 0; i < poolSize; i++ ) {
				availablePool.add(createNewConnection());
			}
		}
	}

	synchronized public static ConnectionPool getInstance() throws Exception {
		if( INSTANCE == null ) {
			INSTANCE = new ConnectionPool();
		}
		return INSTANCE;
	}

	public Connection getConnection() throws Exception {
		if( poolEnabled ) {
			Connection connection = null;
			do {
				connection = availablePool.poll(MAX_BLOCK_TIME, BLOCK_TIME_UNIT);
			} while(connection == null );
			return connection;
		} else {
			return createNewConnection();
		}
	}

	public boolean releaseConnection(Connection connection) throws Exception {
		if(poolEnabled){
			return availablePool.offer(connection, MAX_BLOCK_TIME, BLOCK_TIME_UNIT);
		}
		connection.close();
		return true;
	}

	private static Connection createNewConnection() {
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/videoLibrary","root","ruh12ruh");
			if (!con.isClosed()) {
				System.out.println("");
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
