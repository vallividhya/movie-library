package edu.sjsu.videolibrary.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.sjsu.videolibrary.exception.InternalServerException;

public enum TransactionManager {
	INSTANCE;

	// Mapping to store a database connection per transaction 
	private Map<String, Connection> transactions = new HashMap<String, Connection>();

	private TransactionManager() {};

	public String startTransaction(Connection con) throws Exception {
		String transactionId;
		do {
			transactionId = UUID.randomUUID().toString();
//			System.out.println(transactionId);
		} while(transactions.containsKey(transactionId));

		con.setAutoCommit(false);

		transactions.put(transactionId, con);

		return transactionId;
	}

	public Connection getConnection(String transactionId) throws Exception {
		Connection con = transactions.get(transactionId);
		if (con == null) {
			throw new InternalServerException("There is no open connection for the transaction id " + transactionId);
		}

		return con;
	}

	public void commitTransaction(String transactionId) throws Exception {
		Connection con = transactions.get(transactionId);
		if (con == null) {
			throw new InternalServerException("There is no open connection for the transaction id " + transactionId);
		}

		con.commit();
		con.setAutoCommit(true);
		ConnectionPool.getInstance().releaseConnection(con);

		transactions.remove(transactionId);
	}

	public void rollbackTransaction(String transactionId) throws Exception {
		Connection con = transactions.get(transactionId);
		if (con == null) {
			throw new InternalServerException("There is no open connection for the transaction id " + transactionId);
		}

		con.rollback();
		con.setAutoCommit(true);
		ConnectionPool.getInstance().releaseConnection(con);

		transactions.remove(transactionId);
	}
}
