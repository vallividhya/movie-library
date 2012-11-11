package edu.sjsu.videolibrary.db;

import java.sql.*;

import edu.sjsu.videolibrary.util.Utils;

public class MovieDAO extends VideoLibraryDAO {
	
	public MovieDAO () { } 
	
	public String createNewMovie (String movieName, String movieBanner, String releaseDate, int availableCopies, double rentAmount, int categoryId)  { 
		try {
			String sql = "INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,RentAmount,AvailableCopies,C_Id)" + 
						 "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, movieName); 
			pst.setString(2, movieBanner);
//			pst.setDate(3,releaseDate).; 
			pst.setInt(4, availableCopies);
			pst.setDouble(5, rentAmount);
			pst.setInt(6, categoryId);

			pst.execute();
			ResultSet rs = pst.getGeneratedKeys();
			
			if (rs.next()) {
				Integer movieID = rs.getInt(1);
				return movieID.toString();
			} 
			
		} catch (SQLException e) { } 
		return ""; 
	}	
	
	public String deleteMovie (String movieId) {
		PreparedStatement preparedStmt = null;
		String query = "DELETE FROM videolibrary.movie WHERE movieId = ?"; 
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Integer.parseInt(movieId));
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ""; 
	}
	
	//Questions 
	public String returnMovie(int membershipId, String movieId) {
		PreparedStatement preparedStmt = null;
		String query = "DELETE FROM videolibrary.RentMovieTransaction WHERE M_Id = ?"; 
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Integer.parseInt(movieId));
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ""; 
	}

}
