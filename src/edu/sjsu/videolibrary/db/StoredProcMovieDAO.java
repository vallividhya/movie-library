package edu.sjsu.videolibrary.db;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoCategoryFoundException;
import edu.sjsu.videolibrary.exception.NoMovieFoundException;
import edu.sjsu.videolibrary.exception.NoMovieInCategoryException;
import edu.sjsu.videolibrary.model.Movie;

public class StoredProcMovieDAO extends BaseMovieDAO {

	@Override
	public String createNewMovie(String movieName, String movieBanner,
			String releaseDate, int availableCopies, int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteMovie(String movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnMovie(int membershipId, String movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] listCategories() throws NoCategoryFoundException,
	InternalServerException {
		ArrayList<String> list= new ArrayList<String>();
		try{
			String query = "call videolibrary.get_categories()";
			stmt.execute(query);
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

	@Override
	public Movie[] listMoviesByCategory(String categoryName,int start, int stop)
			throws NoMovieInCategoryException, InternalServerException {
		ArrayList<Movie> list = new ArrayList<Movie>();
		try{
			String query = "call videolibrary.listMoviesByCategory('"+categoryName+"',"+start+","+stop+")";
			stmt.execute(query);
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
			e.printStackTrace();
			throw new InternalServerException("Internal error has occurred.");
		}
		Movie[] array= list.toArray(new Movie[list.size()]);
		return array;
	}

	public Movie[] searchMovie(String movieName,String movieBanner, String releaseDate){
		ArrayList<Movie> list = new ArrayList<Movie>();		

		String query = "call videolibrary.searchMovie(";
		if( movieName == null ) {
			query += "null,";
		} else {
			query += "'" + movieName + "',";
		}
		if( movieBanner == null ) {
			query += "null,";
		} else {
			query += "'" + movieBanner+ "',";
		}
		if( releaseDate == null ) {
			query += "null";
		} else {
			query += "'" + movieBanner + "'";
		}
		
		query += ")";

		System.out.println(query);
		try{
			stmt.execute(query);
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



	@Override
	public Movie[] listAllMovies() throws NoMovieFoundException,
	InternalServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie[] searchByName(String userInput) throws NoMovieFoundException,
	InternalServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie[] searchByMovieBanner(String userInput)
			throws NoMovieFoundException, InternalServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie[] searchByReleaseDate(String userInput)
			throws NoMovieFoundException, InternalServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAvailableCopies(int movieId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String updateCopiesCount(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

}
