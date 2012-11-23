package edu.sjsu.videolibrary.db;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class VideoLibraryDAO {
	protected Connection con = null;
	static ResultSet rs;
	Statement stmt = null;

	public VideoLibraryDAO(){

		try{
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/videoLibrary","root","ruh12ruh");
//			stmt = con.createStatement();
//			if (!con.isClosed()) {
//				System.out.println("");
//			}
			con = ConnectionPool.getInstance().getConnection();
			stmt = con.createStatement();
			//System.out.print("Created new Connection for " + this.getClass().getCanonicalName() );
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
		} catch( Exception e ) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void setCon (Connection con) {
		this.con = con;
	}

	public Connection getCon() {
		return con;
	}

	public void release() {
		try {
			stmt.close();
			ConnectionPool.getInstance().releaseConnection(con);
		} catch (Exception e) {
			System.err.println("Release connection failed for " + this.getClass().getCanonicalName());
			e.printStackTrace();
		}
	}

	/**
	 * Use this function to change autocommit on this DAO
	 * @param autoCommit
	 * @throws SQLException
	 */
	public void setAutoCommit( boolean autoCommit ) throws SQLException {
		con.setAutoCommit(autoCommit);
	}
}
