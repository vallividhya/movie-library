package edu.sjsu.videolibrary.db;

import java.sql.*;

import edu.sjsu.videolibrary.util.Utils;

public class MovieDAO extends VideoLibraryDAO {
	
	public MovieDAO () { } 
	
	public String createNewMovie (String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId)  { 
		try {
			String sql = "INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,AvailableCopies,categoryId)" + 
						 "VALUES (?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, movieName); 
			pst.setString(2, movieBanner);
			pst.setString(3,releaseDate); 
			pst.setInt(4, availableCopies);
			pst.setInt(5, categoryId);
			
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
		//PreparedStatement preparedStmt = null;
		
		//String query = "DELETE FROM videolibrary.movie WHERE movieId = ?"; 
		String query = "DELETE FROM videolibrary.movie WHERE movieId = " + Integer.parseInt(movieId); 

		try {
			
			int rowcount = stmt.executeUpdate(query);
			//preparedStmt = con.prepareStatement(query);
			//preparedStmt.setInt(1, Integer.parseInt(movieId));
			//preparedStmt.executeUpdate();
			if (rowcount > 0) {
				return "true"; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ""; 
	}
	
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
		return "true"; 
	}
	
	/* Main method for testing 
	public static void main (String [] args) { 
		MovieDAO m = new MovieDAO();
		m.deleteMovie("10");
	}
	*/

}



