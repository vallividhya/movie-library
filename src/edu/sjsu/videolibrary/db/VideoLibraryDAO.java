package edu.sjsu.videolibrary.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.sjsu.videolibrary.exception.InternalServerException;


public abstract class VideoLibraryDAO {
	protected Connection con = null;
	static ResultSet rs;
	Statement stmt = null;
	PreparedStatement pst = null;
	final int DEFAULT_BATCH_SIZE = 100;

	protected String transactionId;

	public VideoLibraryDAO() {
		try {
			this.con = ConnectionPool.getInstance().getConnection();
			this.stmt = con.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	public VideoLibraryDAO(String transactionId){
		try {
			this.transactionId = transactionId;

			this.con = TransactionManager.INSTANCE.getConnection(transactionId);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void release() {
		try {
			stmt.close();
			if (transactionId == null) {
				ConnectionPool.getInstance().releaseConnection(con);
			}
		} catch (Exception e) {
			System.err.println("Release connection failed for " + this.getClass().getCanonicalName());
			e.printStackTrace();
		}
	}
	
	public void startTransaction() throws InternalServerException {
		try {
			this.transactionId = TransactionManager.INSTANCE.startTransaction( con );
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	public void commitTransaction() throws InternalServerException {
		try {
			TransactionManager.INSTANCE.commitTransaction(this.transactionId);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	public void rollbackTransaction() throws InternalServerException {
		try {
			TransactionManager.INSTANCE.rollbackTransaction(this.transactionId);
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	
	/**
	 * This function is just for testing
	 * @param mockedConnection
	 */
	@SuppressWarnings("unused")
	private void setConnection( Connection mockedConnection ) {
		this.con = mockedConnection;
	}
	
	/**
	 * This function is just for testing
	 * @param mockedConnection
	 */
	@SuppressWarnings("unused")
	private void setStatement( Statement mockedStatement ) {
		this.stmt = mockedStatement;
	}
}
