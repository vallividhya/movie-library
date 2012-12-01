package edu.sjsu.videolibrary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPoolImpl1 extends ConnectionPool implements IConnectionPool {

	static final int MAX_BLOCK_TIME = 10;
	static final TimeUnit BLOCK_TIME_UNIT = TimeUnit.MILLISECONDS;
	LinkedBlockingQueue <Connection> availablePool = null;

	protected ConnectionPoolImpl1() throws Exception {
		super();
	}

	@Override
	public void setUp() {
		availablePool = new LinkedBlockingQueue<Connection>(poolSize);
		for( int i  = 0; i < poolSize; i++ ) {
			availablePool.add(createNewConnection());
		}
	}

	@Override
	public Connection getConnection() throws Exception {
		Connection connection = null;
		do {
			//	System.err.println("Available Pool " + availablePool.size());
			connection = availablePool.poll(MAX_BLOCK_TIME, BLOCK_TIME_UNIT);
		} while(connection == null );
		return connection;
	}

	@Override
	public boolean releaseConnection(Connection c) throws Exception {
		//System.err.println("Release Connection " + availablePool.size());
		return availablePool.offer(c, MAX_BLOCK_TIME, BLOCK_TIME_UNIT);
	}

	public void printStatus() {
		System.err.println("PoolSize: " + availablePool.size() );
	}
}
