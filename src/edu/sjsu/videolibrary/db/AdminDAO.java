package edu.sjsu.videolibrary.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.User;

public class AdminDAO extends VideoLibraryDAO {

	public User displayUserInformation (String membershipId){

		try{

			String query1 = "select user.FirstName,user.LastName,user.StartDate,"+
					"user.MembershipType,user.MembershipId,user.userId,user.Password,user.Address,user.City,"+
					"user.State,user.Zip,user.CreditCardNumber,user.latestPaymentDate from VideoLibrary.user"+
					" where user.MembershipId = "+ membershipId;

			ResultSet result1 = stmt.executeQuery(query1);
			User user = new User();

			while(result1.next()){
				user.setFirstName(result1.getString("FirstName"));
				user.setLastName(result1.getString("LastName"));
				user.setUserId(result1.getString("UserId"));
				user.setPassword(result1.getString("Password"));
				user.setMembershipType(result1.getString("MembershipType"));
				user.setMembershipId(result1.getInt("MembershipId"));
				user.setAddress(result1.getString("Address"));
				user.setCity(result1.getString("City"));
				user.setState(result1.getString("State"));
				user.setZip(result1.getString("Zip"));
				user.setCreditCardNumber(result1.getString("CreditCardNumber"));
				Date latestPaymentDate = result1.getDate("latestPaymentDate");
				if(latestPaymentDate !=null){
				user.setLatestPaymentDate(result1.getDate("latestPaymentDate").toString());
				}
				else{
					user.setLatestPaymentDate(null);
				}
			}

			String query2 = "select Movie.MovieName from VideoLibrary.RentMovieTransaction rm,VideoLibrary.Movie, VideoLibrary.PaymentTransaction pymnt "+
					"where pymnt.TransactionId = rm.TransactionId and ReturnDate is null and rm.MovieId = Movie.MovieId and MembershipId = "+membershipId;
			ResultSet result2 = stmt.executeQuery(query2);

			LinkedList<String> movies = new LinkedList<String>();

			while(result2.next()){				
				movies.add(result2.getString("MovieName"));
			}	
			String movies1[] = movies.toArray(new String[0]);
			user.setMovieList(movies1);
			return user;

		}
		catch(SQLException e){
			e.getMessage();
			return null;
		}
		catch(Exception e){
			e.getMessage();
			return null;
		}
	}

	public Movie displayMovieInformation (String movieId){

		try{
			double rentAmount = getRentAmountforMovie();
			String query1 = "select Movie.MovieId,Movie.MovieName,Movie.MovieBanner,Movie.ReleaseDate, "+
					" Movie.AvailableCopies,Category.CategoryName from VideoLibrary.Movie,VideoLibrary.Category" + 
					" where Movie.MovieId = "+movieId+" and Movie.CategoryId = Category.CategoryId";

			ResultSet result1 = stmt.executeQuery(query1);
			Movie movie = new Movie();

			while(result1.next()){
				movie.setMovieId(result1.getInt("MovieId"));
				movie.setMovieName(result1.getString("MovieName"));
				movie.setMovieBanner(result1.getString("MovieBanner"));
				movie.setReleaseDate(result1.getDate("ReleaseDate").toString());
				movie.setCatagory(result1.getString("CategoryName"));
				movie.setAvailableCopies(result1.getInt("AvailableCopies"));
				movie.setRentAmount(rentAmount);
			}

			String query2 = "select User.FirstName,User.LastName from VideoLibrary.User,VideoLibrary.PaymentTransaction,"+
					" VideoLibrary.RentMovieTransaction where RentMovieTransaction.MovieId = 1 and  RentMovieTransaction.ReturnDate "+
					"is null and  RentMovieTransaction.TransactionId = PaymentTransaction.TransactionId"+
					" and PaymentTransaction.MembershipId = User.MembershipId";

			ResultSet result2 = stmt.executeQuery(query2);
			LinkedList<String> buyerList = new LinkedList<String>();

			while(result2.next()){
				String fName = result2.getString("FirstName");
				String lName = result2.getString("LastName");
				String fullName = fName+" "+lName;				
				buyerList.add(fullName);				
			}

			String buyerList1[] = buyerList.toArray(new String[0]);
			movie.setBuyerList(buyerList1);
			return movie;
		}
		catch(SQLException e){
			e.getMessage();
			return null;
		}
		catch(Exception e){
			e.getMessage();
			return null;
		}
	}

	public double getRentAmountforMovie(){

		double rentAmount = 0;
		try{

			String query1 = "select amount from VideoLibrary.AmountDetails where membershipType = 'simple' "+
					" order by feesUpdateDate desc limit 1";

			ResultSet result1 = stmt.executeQuery(query1);

			while(result1.next()){
				rentAmount = result1.getDouble("amount");
			}
		}
		catch(SQLException e){
			e.getMessage();
			rentAmount = -1;
		}

		catch(Exception e){
			e.getMessage();
			rentAmount = -1;
		}
		return rentAmount;
	}

	public double getMonthlyFeesForPremiumMember(){

		double monthlyFees = 0;
		try{

			String query1 = "select amount from VideoLibrary.AmountDetails where membershipType = 'premium' "+
					"order by feesUpdateDate desc limit 1";

					ResultSet result1 = stmt.executeQuery(query1);

			while(result1.next()){
				monthlyFees = result1.getDouble("amount");
			}
		}
		catch(SQLException e){
			e.getMessage();
			monthlyFees = -1;
		}

		catch(Exception e){
			e.getMessage();
			monthlyFees = -1;
		}
		return monthlyFees;
	}

	public String updateMovieInfo(String movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId){
		String result = null;
		try{
			String query1 = "update VideoLibrary.Movie set movieName = '"+movieName+"',movieBanner = '"+movieBanner+"',releaseDate = '"+releaseDate+
					"', availableCopies = "+availableCopies+",categoryId = "+categoryId+ " where movieId = "+movieId;

			int rowcount = stmt.executeUpdate(query1);

			if(rowcount>0){
				result = "true";
				System.out.println("Update Successful");
			}
			else{
				System.out.println("Update unsuccessful.");
				result = "false";
			}
		}
		catch(SQLException e){
			e.getMessage();
			result = "false";
		}
		catch(Exception e){
			e.getMessage();
			result = null;
		}
		return result;
	}

	public String generateMonthlyStatement(String membershipId,int month,int year){
		String result = null;
		int statementId = 0;
		try{
			String query1 = "select pymnt.transactionId from VideoLibrary.PaymentTransaction pymnt "+
					" where extract(month from pymnt.rentDate) = "+month+" and extract(year from pymnt.rentDate) = "+year+
					" and pymnt.membershipId = "+membershipId;

			ResultSet result1 = stmt.executeQuery(query1);
			LinkedList<Integer> listOfTransId = new LinkedList<Integer>();
			while(result1.next()){
				listOfTransId.add(result1.getInt("transactionId"));
			}
			String query2 = "insert into VideoLibrary.Statement(month,year,membershipId) "+
					" value("+month+","+year+","+membershipId+")";
			int rowCount = stmt.executeUpdate(query2, Statement.RETURN_GENERATED_KEYS);
			if(rowCount>0){
				ResultSet result2 = stmt.getGeneratedKeys();
				statementId = result2.getInt("statementId");
			}
			else{
				result = null;
				return result;
			}
			for(Integer lst: listOfTransId){
				String query = "insert into VideoLibrary.StatementTransactions(statementId,TransactionId)"+
						" value("+statementId+","+lst+")";
				int rowcount = stmt.executeUpdate(query);
				if(rowcount<0){
					result = "false";
					return result;
				}
			}
			result = "true";
		}
		catch(SQLException e){
			e.getMessage();
			result = "false";
		}
		catch(Exception e){
			e.getMessage();
			result = null;
		}
		return result;
	}

	
}
