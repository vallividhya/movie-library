package edu.sjsu.videolibrary.db;

import java.sql.Connection;

import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;

public class ConnectionPoolImpl2 extends ConnectionPool implements IConnectionPool {

	private GenericObjectPool connectionPool = null;
	private DataSource dataSrc = null;

	protected ConnectionPoolImpl2() throws Exception {
		super();
	}

	public void setUp() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			ConnectionFactory cf = new DriverManagerConnectionFactory(
					"jdbc:mysql://localhost:3306/videoLibrary",
					"root",
					password);

			connectionPool = new GenericObjectPool();
			connectionPool.setMaxActive(poolSize);
			connectionPool.setMinIdle(poolSize);

			new PoolableConnectionFactory(cf, connectionPool,
					null, null, false, true);

			dataSrc = new PoolingDataSource(connectionPool);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (InstantiationException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Connection getConnection() throws Exception {
		return dataSrc.getConnection();
	}

	@Override
	public boolean releaseConnection(Connection c) throws Exception {
		c.close();
		return true;
	}

	public void printStatus() {
		System.err.println("Active: " + connectionPool.getNumActive() +
				" Idle: " + connectionPool.getNumIdle() );
	}
}
