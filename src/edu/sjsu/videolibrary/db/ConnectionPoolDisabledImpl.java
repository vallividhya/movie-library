package edu.sjsu.videolibrary.db;

import java.sql.Connection;


public class ConnectionPoolDisabledImpl extends ConnectionPool implements
		IConnectionPool {

	protected ConnectionPoolDisabledImpl() throws Exception {
		super();
	}
	
	@Override
	public void setUp() {
	}

	@Override
	public Connection getConnection() throws Exception {
		return createNewConnection();
	}

	@Override
	public boolean releaseConnection(Connection c) throws Exception {
		c.close();
		return true;
	}

	@Override
	public void printStatus() {
	}

}
