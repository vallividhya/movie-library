package edu.sjsu.videolibrary.data;

import java.util.Random;

import edu.sjsu.videolibrary.db.BaseMovieDAO;
import edu.sjsu.videolibrary.db.DAOFactory;

public class MovieData {

	static void insertMovieData() throws Exception {
		String movieData[][] = {
				{"The Shawshank Redemption ","Tim Robbins","1994-12-01"},
				{"The Godfather ","Marlon Brando","1972-10-02"},
				{"Pulp Fiction","John Travolta","1994-05-05"},
				{"Schindler's List ","Liam Neeson","1993-06-03"},
				{"12 Angry Men ","Martin Balsam","1957-08-09"},
				{"One Flew Over the Cuckoo's Nest ","Jack Nicholson","1975-09-07"},
				{"The Dark Knight ","Christian Bale","2008-04-12"},
				{"The Lord of the Rings: The Return of the King ","Elijah Wood","2003-03-02"},
				{"Star Wars ","Mark Hamill","1977-08-07"},
				{"Casablanca ","Humphrey Bogart","1942-09-06"},
				{"Goodfellas ","Robert De Niro","1990-07-05"},
				{"Raiders of the Lost Ark ","Harrison Ford","1981-11-11"},
				{"Rear Window","James Stewart","1954-10-10"},
				{"The Matrix ","Keanu Reeves","1999-12-12"},
				{"It's a Wonderful Life ","James Stewart","1946-09-09"},
				{"Dr. Strangelove","Peter Sellers","1964-11-10"},
				{"North by Northwest ","Cary Grant","1959-10-12"},
				{"Citizen Kane","Orson Welles","1941-12-10"},
				{"Forrest Gump ","Tom Hanks","1994-08-05"},
				{"Monty Python and the Holy Grail ","Graham Chapman","1975-09-06"},
				{"Up ","Edward Asner","2009-08-07"},
				{"Singin' in the Rain ","Gene Kelly","1952-09-03"},
				{"2001: A Space Odyssey ","Keir Dullea","1968-07-08"},
				{"Back to the Future ","Michael J. Fox","1985-06-06"},
				{"All About Eve","Bette Davis","1951-08-08"},
				{"The Wizard of Oz","Judy Garland","1939-04-04"},
				{"Ratatouille","Patton Oswalt","2007-02-02"},
				{"Monsters, Inc.","John Goodman","2001-01-01"}
		};
		BaseMovieDAO movieDao = DAOFactory.getMovieDAO();
		String[] categories = movieDao.listCategories();
		Random randomizer = new Random();
		for( int i = 1; i < 35716; i++) {
			for(String[] movie : movieData) {
				int movieCategoryId = randomizer.nextInt(categories.length)+1;
				int numberOfCopies = randomizer.nextInt(9)+1;
				movieDao.createNewMovie(movie[0].trim() + i, movie[1].trim(), movie[2].trim(), numberOfCopies, movieCategoryId);

			}
			movieDao.release();
			movieDao = DAOFactory.getMovieDAO();
			if( i % 1000 == 0 ) {
				System.out.println(".");
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		insertMovieData();
	}
}
