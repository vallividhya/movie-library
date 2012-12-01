package edu.sjsu.videolibrary.db;

import java.sql.Connection;

public interface IConnectionPool {
	public void setUp();
	public Connection getConnection() throws Exception;
	public boolean releaseConnection(Connection c) throws Exception;
	public void printStatus();
}
