package edu.sjsu.videolibrary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.tuple.Pair;

public class VideoLibraryDAO {
	protected Connection con = null;
	static ResultSet rs;
	Statement stmt = null;

	public VideoLibraryDAO(){
		//		Pair<Connection,Statement> pair = ConnectionPool.getInstance().getConnection();
		//		con = pair.getLeft();
		//		stmt = pair.getRight();
		//	System.out.print("Created new Connection for " + this.getClass().getCanonicalName() );
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/videoLibrary","root","ruh12ruh");
			stmt = con.createStatement();
			if (!con.isClosed()) {
				System.out.println("");
			}
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
		}
	}

	public void release() {
		try {
			ConnectionPool.getInstance().releaseConnection(Pair.of(con,stmt));
		} catch (Exception e) {
			System.err.println("Release connection failed for " + this.getClass().getCanonicalName());
			e.printStackTrace();
		}
	}
}
