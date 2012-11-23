package edu.sjsu.videolibrary.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import edu.sjsu.videolibrary.exception.*;
import edu.sjsu.videolibrary.model.Admin;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.PaymentForPremiumMemInfo;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.util.Utils;

public class SimpleAdminDAO extends BaseAdminDAO {

	public User displayUserInformation (int membershipId){
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

	public Movie displayMovieInformation (int movieId){

		try{
			double rentAmount = getRentAmountforMovie();
			String query1 = "SELECT Movie.MovieId,Movie.MovieName,Movie.MovieBanner,Movie.ReleaseDate, "+
					" Movie.AvailableCopies,Category.CategoryName FROM VideoLibrary.Movie,VideoLibrary.Category" + 
					" WHERE Movie.MovieId = "+movieId+" AND Movie.CategoryId = Category.CategoryId";

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

	public String updateMovieInfo(int movieId,String movieName, String movieBanner, String releaseDate, int availableCopies, int categoryId){
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

	public String generateMonthlyStatement(int membershipId,int month,int year) throws SQLException{
		String result = null;
		int statementId = 0;
		boolean processComplete = false;
		// TODO: Need to check the month is not earlier than user join date
		try {
			con.setAutoCommit(false);
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
				result2.next();
				statementId = result2.getInt(1);
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
			processComplete = true;
		} finally {
			if ( processComplete ) {
				con.commit();
			} else {
				con.rollback();
			}
			con.setAutoCommit(true);
		}
		result = "true";
		return result;
	}

	//Moved from UserDAO 
	public String deleteUser (String userId) {
		try {
			String sql = "DELETE from user WHERE membershipId  = ?";

			PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, userId); 
			pst.execute();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next())
			{
				Integer memID = rs.getInt(1);
				return memID.toString();
			}    
		} catch (SQLException e) { e.printStackTrace(); } 
		return "";		
	}

	public PaymentForPremiumMemInfo generateMonthlyBillForPremiumMember(int membershipId,int month,int year){
		PaymentForPremiumMemInfo pymnt = new PaymentForPremiumMemInfo();
		try{
			String query1 = "select * from  VideoLibrary.PaymentTransaction pymnt where pymnt.membershipId = "+
		membershipId+" AND pymnt.transactionId not in( select rnt.transactionid from VideoLibrary.RentMovieTransaction rnt group by rnt.transactionid )";
		
		ResultSet result = stmt.executeQuery(query1);
		
		if(result.next()){
			Date paymentDate = result.getDate("rentDate");
			if(paymentDate == null){
				pymnt.setPaymentDate("None");				
			}
			else{
				pymnt.setPaymentDate(paymentDate.toString());
			}
			pymnt.setMonthlyPaymentAmount(result.getDouble("totalDueAmount"));
			pymnt.setPaymentStatus("Payment Received");
		}
		else{
			pymnt.setPaymentStatus("Payment not received");
		}
		}
		catch(SQLException e){
			e.getMessage();
			pymnt = null;
		}
		catch(Exception e){
			e.getMessage();
			pymnt = null;
		}
		return pymnt;
	}

	public String deleteAdmin (String userId) {

		//SuperAdmin should not be removed from the Database
		if (!userId.equals("Admin")) {
			//if (Integer.parseInt(userId) != 1) {	
			try {
				String sql = "DELETE FROM admin WHERE userId = " + userId;
				System.out.println(sql);
				//PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
				//pst.setString(1, userId); 
				//pst.execute();
				//ResultSet rs = pst.getGeneratedKeys();
				int rowcount = stmt.executeUpdate(sql);
				if (rowcount > 0) {
					Integer memID = rs.getInt(1);
					return memID.toString();
				}    
			} catch (SQLException e) { e.printStackTrace(); } 
		} else { 

		}
		return "false";		
	}


	public List <User> listMembers (String type){		
		//PreparedStatement preparedStmt = null;
		List <User> members = new ArrayList<User>();
		String query = ""; 

		if (type.equals("all")) {
			query = "SELECT user.membershipId, user.userId, user.firstName, user.lastName FROM user";
		} else { 
			query = "SELECT user.membershipId, user.userId, user.firstName, user.lastName FROM user WHERE user.membershipType = '" + type + "'"; 
		}

		try {
			//preparedStmt = con.prepareStatement(query);
			//System.out.println(preparedStmt.toString());
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				User member = new User();
				member.setMembershipId(rs.getInt("membershipId"));
				member.setUserId(rs.getString("userId"));
				member.setFirstName(rs.getString("firstName"));
				member.setLastName(rs.getString("lastName"));
				members.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//User test = (User) members.toArray()[0];
		//System.out.println(test.getFirstName());
		return members;
	}
	
	public User[] searchUserByFirstName(String adminInput) throws NoUserFoundException, InternalServerException{
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where firstName like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoUserFoundException("No user found with the entered first name.");
			while(rs.next()){
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setStartDate(rs.getString(5));
				user.setMembershipType(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				list.add(user);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e){
			throw new InternalServerException("Internal error has occurred.");
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByLastName(String adminInput) throws NoUserFoundException, InternalServerException{
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where lastName like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoUserFoundException("No user found with the entered last name.");
			while(rs.next()){
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setStartDate(rs.getString(5));
				user.setMembershipType(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				list.add(user);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e){
			throw new InternalServerException("Internal error has occurred");
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByCity(String adminInput) throws NoUserFoundException, InternalServerException{
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where city like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoUserFoundException("No user found for the entered city.");
			while(rs.next()){
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setStartDate(rs.getString(5));
				user.setMembershipType(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				list.add(user);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e){
			throw new InternalServerException("Internal error has occurred.");
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByState(String adminInput) throws NoUserFoundException, InternalServerException{
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where state like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoUserFoundException("No user found for the entered state.");
			while(rs.next()){
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setStartDate(rs.getString(5));
				user.setMembershipType(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				list.add(user);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e){
			throw new InternalServerException("Internal error has occurred.");
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByMemberShipType(String adminInput) throws NoUserFoundException, InternalServerException{
		ArrayList<User> list= new ArrayList<User>();		
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where membershipType='"+adminInput+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoUserFoundException("No user found for the entered membership type.");
			while(rs.next()){
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setStartDate(rs.getString(5));
				user.setMembershipType(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				list.add(user);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e){
			throw new InternalServerException("Internal error has occurred.");
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[]  searchUserByMembershipId(int adminInput) throws NoUserFoundException, InternalServerException{
		ArrayList<User> list = new ArrayList<User>();
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where membershipId="+adminInput;
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
			if(!rs.isBeforeFirst())
				throw new NoUserFoundException("No user found for the entered membership id.");
			while(rs.next()){
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setStartDate(rs.getString(5));
				user.setMembershipType(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setCity(rs.getString(8));
				user.setState(rs.getString(9));
				user.setZip(rs.getString(10));
				list.add(user);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e){
			throw new InternalServerException("Internal error has occurred.");
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	//Added recently
		public String updateAdminInfo(String adminId,String firstName, String lastName, String password){
			String result = "false";
			try {
				String query1 = "UPDATE VideoLibrary.admin SET firstName = '"+firstName+"' ,lastName = '"+lastName +"' WHERE userId = '" + adminId + "'";
				System.out.println(query1);
				int rowcount = stmt.executeUpdate(query1);

				if(rowcount>0){
					result = "true";
				}
			}
			catch(Exception e){ e.printStackTrace(); }
			return result;
		}
		
		public Admin displayAdminInformation (String adminId) {
			Admin admin = new Admin();
			try {
				String query = "SELECT admin.firstName, admin.lastName, admin.password FROM admin WHERE userId = '" + adminId + "'";
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()){
					admin.setAdminId(adminId);
					admin.setFirstName(rs.getString("firstName"));
					admin.setLastName(rs.getString("lastName"));
					admin.setPassword(rs.getString("password"));
				}
			} catch (SQLException e) { 
				return null;
			}
			
			return admin; 
		}
		
		public String updateUserPassword(int membershipId,String newPassword){
			String result = null;

			try{
				String query1 = "UPDATE VideoLibrary.User SET password = '"+Utils.encryptPassword(newPassword) + "' WHERE membershipId = "+membershipId;

				int rowcount = stmt.executeUpdate(query1);

				if(rowcount > 0){
					result = "true";
	 			}
				else{
	 				result = "invalidID";
				}
			}
			catch(Exception e){
	 			result = "false";
			}
			return result;		
		}
		
		public Admin signInAdminObject (String userId, String password)  {
//			Admin bean = new Admin(); 
//			bean.setAdminId(userId);
//			bean.setPassword(password);
//			String sql = "SELECT userId, password, firstName, lastName FROM admin WHERE userId = '" + userId + "'" + " AND password = '" + Utils.encryptPassword(password) + "'";
//			System.out.println(sql);
//			try { 
//			
//				Statement stmt = con.createStatement();
//				ResultSet rs = stmt.executeQuery(sql);
//				if(rs.next())
//				{
//			        String firstName = rs.getString("firstName");
//			        String lastName = rs.getString("lastName");
//					
//					bean.setFirstName(firstName);
//					bean.setLastName(lastName);
//					// Could not find this function. So code is disabled.
//					
//					// Please fix this.
//					bean.setValid(true);
//					return bean;
//				} else {
//					System.out.println("else");
//					bean.setValid(false);
//				}
//			} catch (SQLException e) { } 
//			return bean;
			return null;
		}
		
		

}
	
	
