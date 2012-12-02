package edu.sjsu.videolibrary.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoCategoryFoundException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.exception.NoMovieInCategoryException;
import edu.sjsu.videolibrary.model.Movie;

public class PreparedStatMovieDAO extends BaseMovieDAO{
	public PreparedStatMovieDAO() {
		super();
	}

	public PreparedStatMovieDAO(VideoLibraryDAO dao) {
		super(dao);
	}

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
	@Override
	public Movie[] listMoviesByCategory(String categoryName, int start, int stop) throws NoMovieInCategoryException, InternalServerException {
		ArrayList<Movie> list = new ArrayList<Movie>();
		PreparedStatement pst = null;
		try{
			String query = "Select movieId,movieName,movieBanner,releaseDate,availableCopies from Movie where categoryId=(select categoryId from Category where categoryName=? ) limit ?,?";
			pst = con.prepareStatement(query);
			pst.setString(1, categoryName);
			pst.setInt(2, start);
			pst.setInt(3, stop);
			pst.execute();
			rs=pst.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoMovieInCategoryException("There are no movies in this category.");
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
	public Movie[] listAllMovies(int start,int stop) throws NoMovieFoundException,InternalServerException{
		ArrayList<Movie> list = new ArrayList<Movie>();		
		try{
			String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId LIMIT "+
			start + "," + stop;
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
		PreparedStatement pst = null;
		String mName = null;
		String mBanner = null;
		String rDate = null;
//		String str = "%"+userInput.replace(' ','%')+"%";
		String query = "Select m.movieId,m.movieName,m.movieBanner,m.releaseDate,m.availableCopies,c.categoryName from Movie m, Category c where m.categoryId=c.categoryId ";
		if(movieName!=null){
			mName= "%"+movieName+"%";
			query = query +"and m.movieName like ?";
		}
		if(movieBanner!=null){
			 mBanner= "%"+movieBanner+"%";
			query = query +"and m.movieBanner like ?";
		}
		if(releaseDate!=null){
			 rDate= "%"+releaseDate+"%";
			query = query +"and m.releaseDate like ?";
		}
		
		query = query + " LIMIT " + start + "," + stop;

		try{
			pst = con.prepareStatement(query);
			int index = 1;
			if(movieName!=null){
				pst.setString(index++, mName);
			}
			if(movieBanner!=null){
				pst.setString(index++, mBanner);
			}
			if(releaseDate!=null){
				pst.setString(index++, rDate);
			}
			pst.executeQuery();
			rs=pst.getResultSet();
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

	public String updateCopiesCount(int movieId) {
		System.out.println("Movie Id for updating = " + movieId);
		int availableCopies = getAvailableCopies(movieId);
		String isCopiesUpdated = "false";
		System.out.println(availableCopies
				+ " are available for movie with movieId = " + movieId);
		try {
			if (availableCopies > 0) {
				availableCopies = availableCopies - 1;
				System.out.println("Available copies after renting out = "
						+ availableCopies);
				String query = "UPDATE videolibrary.movie SET availableCopies = "
						+ availableCopies + " WHERE movieId = " + movieId;
				System.out.println("Start time of update tx "
						+ (System.currentTimeMillis() / 1000) + " seconds");
				int rowCount = stmt.executeUpdate(query);
				System.out.println("Stop time of update tx "
						+ (System.currentTimeMillis() / 1000) + " seconds");
				if (rowCount > 0) {
					isCopiesUpdated = "true";
					System.out.println("Update of availableCopies Successful");
				}
			} else {
				System.out
						.println("All copies rented out. Nothing available now");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return isCopiesUpdated;
	}

	public int getAvailableCopies(int movieId) {
		String query = "SELECT availableCopies FROM videolibrary.movie WHERE movieId = "
				+ movieId;
		Movie movie = new Movie();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				movie.setAvailableCopies(rs.getInt("availableCopies"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return movie.getAvailableCopies();
	}

	@Override
	public String updateCopiesCount(int movieId, int numOfCopies)
			throws InternalServerException {
		return null;
		
	}
}

