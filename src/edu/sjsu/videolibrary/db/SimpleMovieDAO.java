package edu.sjsu.videolibrary.db;

import java.sql.*;
import java.util.ArrayList;
import edu.sjsu.videolibrary.exception.*;
import edu.sjsu.videolibrary.model.Movie;

public class SimpleMovieDAO extends BaseMovieDAO {

	public SimpleMovieDAO() {
		super();
	}

	public SimpleMovieDAO(VideoLibraryDAO dao) {
		super(dao);
	}

	public String createNewMovie (String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId)  { 
		String s =null;
		try {
			String sql = "INSERT INTO VideoLibrary.Movie (MovieName,MovieBanner,ReleaseDate,AvailableCopies,categoryId)" + 
					"VALUES ('"+movieName+"','"+movieBanner+"','"+releaseDate+"',"+availableCopies+","+categoryId+")";
			int rc = stmt.executeUpdate(sql);
			System.out.println(sql);
			if (rc > 0) {
				return "true";		
			}
			else{
				return  "false";
			}
		} catch (SQLException e) {  return "error";  } 
		return s; 
	}	

	public String deleteMovie (String movieId) {
		//PreparedStatement preparedStmt = null;
		String query = "DELETE FROM videolibrary.movie WHERE movieId = " + Integer.parseInt(movieId); 
		String result = null;
		try {

			int rowcount = stmt.executeUpdate(query);
			//preparedStmt = con.prepareStatement(query);
			//preparedStmt.setInt(1, Integer.parseInt(movieId));
			//preparedStmt.executeUpdate();
			if (rowcount > 0) {
				result= "true"; 
			}
			else
				result = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result; 

	}

	public String returnMovie(int membershipId, String movieId) {
		String result = null;
		String query = "DELETE FROM videolibrary.RentMovieTransaction WHERE M_Id = "+movieId; 
		try {
			int rc = stmt.executeUpdate(query);
			if(rc>0)
				result =  "true";
			else
				result = "false";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

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
	@Override
	public Movie[] listMoviesByCategory(String categoryName, int start, int stop) throws NoMovieInCategoryException, InternalServerException {
		ArrayList<Movie> list = new ArrayList<Movie>();
		try{
			String query = "Select movieId,movieName,movieBanner,releaseDate,availableCopies from Movie where categoryId=(select categoryId from Category where categoryName='"+categoryName+
					"') limit " + start + "," + stop;
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
			throw new InternalServerException("Internal error has occurred." + e.getMessage());
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

	
	
	public Movie[] searchMovie(String movieName,String movieBanner, String releaseDate, int start, int stop){
		ArrayList<Movie> list = new ArrayList<Movie>();		
//		String str = "%"+userInput.replace(' ','%')+"%";
		String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId ";
		if(movieName!=null){
			String mName= "%"+movieName+"%";
			query = query +"and m.movieName like '"+mName+"'";
		}
		if(movieBanner!=null){
			String mBanner= "%"+movieBanner+"%";
			query = query +"and m.movieBanner like '"+mBanner+"'";
		}
		if(releaseDate!=null){
			String rDate= "%"+releaseDate+"%";
			query = query +"and m.releaseDate like '"+rDate+"'";
		}
		
		query = query + " LIMIT " + start + "," + stop;

		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		Movie[] array= list.toArray(new Movie[list.size()]);
		return array;
	}

	public String updateCopiesCount(int movieId, int numOfCopies) throws InternalServerException {
		System.out.println("Movie Id for updating = " + movieId);
		String result;
		int availableCopies = getAvailableCopies(movieId);
		System.out.println(availableCopies
				+ " are available for movie with movieId = " + movieId);
		try {
			String query = "UPDATE videolibrary.movie SET availableCopies = "
						+ numOfCopies + " WHERE movieId = " + movieId;
				
				int rowCount = stmt.executeUpdate(query);				
				if (rowCount > 0) {
					System.out.println("Update of availableCopies Successful");
					result="true";
				}
				else
					result="false";
			}  catch (SQLException e) {
				throw new InternalServerException ("Update of available movies failed", e);
		}
		return result;
	}

	public int getAvailableCopies(int movieId) {
		int availableCopies=0;
		String query = "SELECT availableCopies FROM videolibrary.movie WHERE movieId = "
				+ movieId;
		Movie movie = new Movie();
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {				
				availableCopies = rs.getInt("availableCopies");
				movie.setAvailableCopies(availableCopies);
			}
			else
				availableCopies = 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return availableCopies;
	}
}



