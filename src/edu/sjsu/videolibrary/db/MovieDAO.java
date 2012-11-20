package edu.sjsu.videolibrary.db;

import java.sql.*;
import java.util.ArrayList;
import edu.sjsu.videolibrary.exception.*;
import edu.sjsu.videolibrary.model.Movie;
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
	//List categories on home page
		public String[] listCategories() throws NoCategoryFoundException, InternalServerException{
			ArrayList<String> list= new ArrayList<String>();
			try{
				String query = "Select CategoryName from Category";
				stmt.executeQuery(query);
				rs=stmt.getResultSet();
				if(!rs.isBeforeFirst())
					throw new NoCategoryFoundException("There are no categories to be displayed");
				while(rs.next()){
					list.add(rs.getString(1));
				}
				rs.close();
				stmt.close();
			}catch(SQLException e){
				throw new InternalServerException("Internal error has occurred.");
			}
			String[] categoryName = list.toArray(new String[list.size()]);
			return categoryName;
		}
		
		//List movies by chosen category
		public Movie[] listMoviesByCategory(String categoryName) throws NoMovieInCategoryException, InternalServerException{
			ArrayList<Movie> list = new ArrayList<Movie>();
			try{
				String query = "Select movieId,movieName,movieBanner,releaseDate,availableCopies from Movie where categoryId=(select categoryId from Category where categoryName='"+categoryName+"')";
				stmt.executeQuery(query);
				rs=stmt.getResultSet();
				if(!rs.isBeforeFirst())
					throw new NoMovieInCategoryException("There are no movies in this category");
				while(rs.next()){
					Movie movie = new Movie();
					movie.setMovieId(rs.getInt(1));
					movie.setMovieName(rs.getString(2));
					movie.setMovieBanner(rs.getString(3));
					movie.setReleaseDate(rs.getString(4));
					movie.setAvailableCopies(rs.getInt(5));
					list.add(movie);
				}
				rs.close();
				stmt.close();
			}catch(SQLException e){
				throw new InternalServerException("Internal erro has occurred.");
			}
			Movie[] array= list.toArray(new Movie[list.size()]);
			return array;		
		}
		
		//Display all Movies
		public Movie[] listAllMovies() throws NoMovieFoundException,InternalServerException{
			ArrayList<Movie> list = new ArrayList<Movie>();		
			try{
				String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId";
				stmt.executeQuery(query);
				rs=stmt.getResultSet();
				if(!rs.isBeforeFirst()){
					throw new NoMovieFoundException("There are no movies to display.");
				}
				while(rs.next()){
					Movie movie = new Movie();
					movie.setMovieId(rs.getInt(1));
					movie.setMovieName(rs.getString(2));
					movie.setMovieBanner(rs.getString(3));
					movie.setReleaseDate(rs.getString(4));				
					movie.setAvailableCopies(rs.getInt(5));
					movie.setCategoryName(rs.getString(6));
					list.add(movie);
				}
				rs.close();
				stmt.close();
			}catch(SQLException e){
				throw new InternalServerException("Internal error has occurred.");
			}
			Movie[] array= list.toArray(new Movie[list.size()]);
			return array;
		}
		
		public Movie[] searchByName(String userInput) throws NoMovieFoundException, InternalServerException{
			ArrayList<Movie> list = new ArrayList<Movie>();		
			String str = "%"+userInput.replace(' ','%')+"%";
			String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId and m.movieName like '"+str+"'";
			try{
				stmt.executeQuery(query);
				rs=stmt.getResultSet();
				if(!rs.isBeforeFirst())
					throw new NoMovieFoundException("No movie found with the entered name.");
				while(rs.next()){
					Movie movie = new Movie();
					movie.setMovieId(rs.getInt(1));
					movie.setMovieName(rs.getString(2));
					movie.setMovieBanner(rs.getString(3));
					movie.setReleaseDate(rs.getString(4));				
					movie.setAvailableCopies(rs.getInt(5));
					movie.setCategoryName(rs.getString(6));
					list.add(movie);
				}
				rs.close();
				stmt.close();
			}catch(SQLException e){
				throw new InternalServerException("Internal Error has occurred.");
			}
			Movie[] array= list.toArray(new Movie[list.size()]);
			return array;
		}
		
		public Movie[] searchByMovieBanner(String userInput) throws NoMovieFoundException, InternalServerException{
			ArrayList<Movie> list = new ArrayList<Movie>();		
			String str = "%"+userInput.replace(' ','%')+"%";
			String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId and m.movieBanner like '"+str+"'"; 
			try{
				stmt.executeQuery(query);
				rs=stmt.getResultSet();
				if(!rs.isBeforeFirst())
					throw new NoMovieFoundException("No movie found for the entered banner.");
				while(rs.next()){
					Movie movie = new Movie();
					movie.setMovieId(rs.getInt(1));
					movie.setMovieName(rs.getString(2));
					movie.setMovieBanner(rs.getString(3));
					movie.setReleaseDate(rs.getString(4));				
					movie.setAvailableCopies(rs.getInt(5));
					movie.setCategoryName(rs.getString(6));
					list.add(movie);
				}
				rs.close();
				stmt.close();
			}catch(SQLException e){
				throw new InternalServerException("Internal error has occurred");
			}
			Movie[] array= list.toArray(new Movie[list.size()]);
			return array;
		}
		
		public Movie[] searchByReleaseDate(String userInput) throws NoMovieFoundException, InternalServerException{
			ArrayList<Movie> list = new ArrayList<Movie>();		
			String str = "%"+userInput.replace(' ','%')+"%";
			String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId and m.releaseDate like '"+str+"'";
			try{
				stmt.executeQuery(query);
				rs=stmt.getResultSet();
				if(!rs.isBeforeFirst())
					throw new NoMovieFoundException("No movie found for the entered banner.");
				while(rs.next()){
					Movie movie = new Movie();
					movie.setMovieId(rs.getInt(1));
					movie.setMovieName(rs.getString(2));
					movie.setMovieBanner(rs.getString(3));
					movie.setReleaseDate(rs.getString(4));				
					movie.setAvailableCopies(rs.getInt(5));
					movie.setCategoryName(rs.getString(6));
					list.add(movie);
				}
				rs.close();
				stmt.close();
			}catch(SQLException e){
				throw new InternalServerException("Internal error has occurred");
			}
			Movie[] array= list.toArray(new Movie[list.size()]);
			return array;
		}
		

}



