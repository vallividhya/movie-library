package edu.sjsu.videolibrary.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class VideoLibraryDAO {
	protected Connection con = null;
	static ResultSet rs;
	Statement stmt = null;

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
}
