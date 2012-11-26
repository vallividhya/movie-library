package edu.sjsu.videolibrary.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import edu.sjsu.videolibrary.exception.InternalServerException;
import edu.sjsu.videolibrary.exception.NoCategoryFoundException;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.User;

public class Test {
	private static int MAX_RETRIES = 3;
	public static void main(String[] args) throws Exception {
	//	abc();
	//	SP_searchMovie();
		SP_searchUser();
	}

	public static void test() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date today = dateFormat.parse(dateFormat.format(new Date()));
			System.out.println(today);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int retryCount = 0;
		while (retryCount < MAX_RETRIES) {
			try {
				String id = UUID.randomUUID().toString();
				// Insert into the table
				break;
			} catch (Exception e) {
				retryCount++;
				continue;
			}
		}

		if (retryCount == MAX_RETRIES) {
			//throw new InternalException();
		}
	}

	public static void storedProc() throws Exception {
		StoredProcMovieDAO dao = new StoredProcMovieDAO();
		for( String str : dao.listCategories() ) {
			System.out.println(str);
		}
	}

	public static void storedProc2() throws Exception {
		StoredProcMovieDAO dao = new StoredProcMovieDAO();
		for( Movie mv : dao.listMoviesByCategory("Drama",0,10) ) {
			System.out.println(mv.getMovieName());
		}
	}

	public static void abc() throws Exception {
		SimpleMovieDAO dao = new SimpleMovieDAO();
		for( Movie m: dao.searchMovie("Lord of", null, null) ) {
			System.out.println(m.getMovieName());
			System.out.println(m.getMovieId());
		}
	}
	
	public static void SP_searchMovie() throws Exception {
		StoredProcMovieDAO dao = new StoredProcMovieDAO();
		for( Movie m: dao.searchMovie("Lord of", null, null) ) {
			System.out.println(m.getMovieName());
			System.out.println(m.getMovieId());
		}
	}
	
	public static void SP_searchUser() throws Exception {
		StoredProcUserDAO dao = new StoredProcUserDAO();
		for( User usr: dao.searchUser("1",null,null,null,null,null,null,null,"CA",null)){
			System.out.println(usr.getFirstName());
			System.out.println(usr.getUserId());
		}
	}
}
