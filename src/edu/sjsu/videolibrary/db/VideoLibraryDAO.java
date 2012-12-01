package edu.sjsu.videolibrary.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import edu.sjsu.videolibrary.exception.InternalServerException;


public abstract class VideoLibraryDAO {
	protected Connection con = null;
	static ResultSet rs;
	Statement stmt = null;
	PreparedStatement pst = null;
	final int DEFAULT_BATCH_SIZE = 100;

	protected String transactionId = null;
	boolean inheritedTransaction = false;

	public VideoLibraryDAO() {
		try {
			IConnectionPool pool = ConnectionPool.getInstance();
//			Thread.currentThread().dumpStack();
//			pool.printStatus();
			this.con = pool.getConnection();
//			pool.printStatus();
			this.stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	public VideoLibraryDAO(VideoLibraryDAO dao) {
		try {
			this.con = dao.con;
			this.stmt = con.createStatement();
			this.transactionId = dao.transactionId;
			// We inherit this transaction, so no rollback or commit will work for us
			inheritedTransaction = true;
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	public void release() {
		try {
			if( stmt != null ) {
				stmt.close();
			}
			ConnectionPool.getInstance().releaseConnection(con);
		} catch (Exception e) {
			System.err.println("Release connection failed for " + this.getClass().getCanonicalName());
			e.printStackTrace();
		}
	}

	public void startTransaction() throws InternalServerException {
		if( transactionId == null ) {
			try {
				this.transactionId = TransactionManager.INSTANCE.startTransaction( con );
			} catch (Exception e) {
				throw new InternalServerException(e.getMessage());
			}
		}
	}

	public void commitTransaction() throws InternalServerException {
		if( !inheritedTransaction && transactionId != null ) {
			try {
				TransactionManager.INSTANCE.commitTransaction(this.transactionId);
			} catch (Exception e) {
				throw new InternalServerException(e.getMessage());
			}
		}
	}

	public void rollbackTransaction() throws InternalServerException {
		if( !inheritedTransaction && transactionId != null ) {
			try {
				TransactionManager.INSTANCE.rollbackTransaction(this.transactionId);
			} catch (Exception e) {
				throw new InternalServerException(e.getMessage());
			}
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
