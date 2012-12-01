package edu.sjsu.videolibrary.db;

import java.sql.Connection;

public class DisabledConnectionPoolImpl extends ConnectionPool implements
		IConnectionPool {

	protected DisabledConnectionPoolImpl() throws Exception {
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
