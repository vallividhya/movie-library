package edu.sjsu.videolibrary.db;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.tuple.Pair;

public class ConnectionPool {
	static final int poolSize = 5;
	static final boolean poolEnabled = false;
	static ConnectionPool INSTANCE = null;
	static final int MAX_BLOCK_TIME = 10;
	static final TimeUnit BLOCK_TIME_UNIT = TimeUnit.MILLISECONDS;

	LinkedBlockingQueue <Pair<Connection,Statement>> availablePool = 
			new LinkedBlockingQueue <Pair<Connection,Statement>>(poolSize);

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

	public Pair<Connection,Statement> getConnection() throws Exception {
		if( poolEnabled ) {
			Pair<Connection,Statement> pair = null;
			do {
				pair = availablePool.poll(MAX_BLOCK_TIME, BLOCK_TIME_UNIT);
			} while(pair == null );
			return pair;
		} else {
			return createNewConnection();
		}
	}

	public boolean releaseConnection(Pair<Connection,Statement> pair) throws Exception {
		if(poolEnabled){
			return availablePool.offer(pair, MAX_BLOCK_TIME, BLOCK_TIME_UNIT);
		}
		pair.getRight().close();
		pair.getLeft().close();
		return true;
	}

	private static Pair<Connection,Statement> createNewConnection() {
		Connection con = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/videoLibrary","root","ruh12ruh");
			if (!con.isClosed()) {
				System.out.println("");
			}
			stmt = con.createStatement();
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
		return Pair.of(con, stmt);
	}
}
