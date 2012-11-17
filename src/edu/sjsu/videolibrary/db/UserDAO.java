package edu.sjsu.videolibrary.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.db.AdminDAO;
import edu.sjsu.videolibrary.model.ItemOnCart;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.util.Utils;

public class UserDAO extends VideoLibraryDAO 
{
	public UserDAO()
	{ }

	public String signUpUser (String userId, String password, String memType, java.sql.Date startDate,
			String firstName, String lastName, String address, String city, 
			String state, String zipCode,String ccNumber, java.sql.Date latestPaymentDate) throws SQLException 
			{
		
			System.out.println("State:"+state);

		String sql = "INSERT INTO user (userId,password,membershipType,startDate,firstName,lastName," +
				"address,city,state,zip,creditCardNumber,latestPaymentDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setString(1, userId); pst.setString(2, Utils.encryptPassword(password));
		pst.setString(3,memType); pst.setDate(4, startDate);
		pst.setString(5, firstName);pst.setString(6, lastName);
		pst.setString(7, address);pst.setString(8, city);
		pst.setString(9, state);pst.setString(10, zipCode);
		pst.setString(11, ccNumber);pst.setDate(12, latestPaymentDate);
		pst.execute();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next())
		{
			Integer memID = rs.getInt(1);
			return memID.toString();
		}     
		return "";
			}

	public String signUpAdmin (String userId, String password, String firstName, String lastName) throws SQLException 
	{
		String sql = "INSERT INTO admin (userId,password,firstName,lastName) VALUES (?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setString(1, userId); pst.setString(2, Utils.encryptPassword(password));
		pst.setString(3, firstName);pst.setString(4, lastName);
		pst.execute();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next())
		{
			Integer memID = rs.getInt(1);
			return memID.toString();
		}     
		return "";
	}
 

	public String signInUser(String userId, String password) throws SQLException
	{
		String sql = "SELECT userId, password FROM user WHERE userId = '" + userId + "'" + " AND password = '" + Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "";
	}

	public String signInAdmin(String userId, String password) throws SQLException
	{
		String sql = "SELECT userId, password FROM admin where userId = '" + userId + "'" + " AND password = '" + Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			return rs.getString(1);
		}
		return "d";     
	}

	public LinkedList<Transaction> viewAccountTransactions(String membershipId){
		LinkedList<Transaction> ac = new LinkedList<Transaction>();
		try{
			String query1 = "select Movie.MovieName,pymnt.RentDate,rnt.ReturnDate,User.MembershipType from "+ 
					" VideoLibrary.RentMovieTransaction rnt,VideoLibrary.PaymentTransaction pymnt, "+
					" VideoLibrary.Movie,VideoLibrary.User  where User.MembershipId = "+membershipId+" and pymnt.TransactionId = rnt.TransactionId "+
					" and rnt.MovieId = Movie.MovieId and pymnt.MembershipId = User.MembershipId";

			ResultSet result1 = stmt.executeQuery(query1);

			while(result1.next()){
				Transaction trans = new Transaction();
				trans.setMovieName(result1.getString("MovieName"));
				String membershipType = result1.getString("MembershipType");
				if(membershipType.equalsIgnoreCase("Simple")){
					trans.setPerMovieAmount(1.5);
				}
				else{
					trans.setPerMovieAmount(0.0);
				}
				trans.setPurchaseDate((result1.getDate("RentDate").toString()));
				Date returnDate = result1.getDate("ReturnDate");
				if(returnDate != null){
					trans.setReturnDate((returnDate).toString());
				}
				else{
					trans.setReturnDate("Not Returned");					
				}

				ac.add(trans);
			}
		}
		catch(SQLException e){
			e.getMessage();
			return null;
		}
		catch(Exception e){
			e.getMessage();
			return null;
		}
		return ac;
	}

	public String makeMonthlyPayment(String membershipId){
		String result = null;
		try{
			String query1 = "UPDATE VideoLibrary.User SET latestPaymentDate = NOW() "+
					"WHERE User.membershipId = "+membershipId;
			int rowcount1 = stmt.executeUpdate(query1);
			if(rowcount1<0){
				result = "false";
				return result;
			}

			AdminDAO adminDAO = new AdminDAO();			
			double monthlyFees = adminDAO.getMonthlyFeesForPremiumMember();			

			String query2 = "INSERT INTO VideoLibrary.PaymentTransaction(rentDate,totalDueAmount,membershipId)"+
					" VALUES (NOW(),"+monthlyFees+","+membershipId+")";
			int rowcount2 = stmt.executeUpdate(query2);

			if(rowcount2>0){
				result = "true";
				System.out.println("Payment Successful");
			}
			else{
				System.out.println("Payment could not be proceeded.");
				result = "false";
			}

		}
		catch(SQLException e){
			e.getMessage();
			return null;
		}
		catch(Exception e){
			e.getMessage();
			return null;
		}
		return result;
	}


	public String updateUserInfo(String membershipId,String userId,String firstName, String lastName, String address, String city, String state, String zipCode, String membershipType,String creditCardNumber){

		String result = null;
		try{

			String query1 = "update VideoLibrary.User set userId = '"+userId+"' ,membershipType = '"+membershipType+" ',firstName = '"+firstName+
					"' ,lastName = '"+lastName+"',address = '"+address+"',city = '"+city+"',state = '"+state+"',zip = '"+98567+
					"' ,creditCardNumber = '"+creditCardNumber+" ' where membershipId = "+ membershipId;

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

	public String updatePassword(String membershipId,String oldPassword,String newPassword){
		String result =null;

		try{
			String query1 = "UPDATE VideoLibrary.User SET password = '"+Utils.encryptPassword(newPassword) +
					"' WHERE membershipId = "+membershipId+" AND password = '"+ Utils.encryptPassword(oldPassword)+"'";

			int rowcount = stmt.executeUpdate(query1);

			if(rowcount>0){
				result = "true";
				System.out.println("Update Successful");
			}
			else{
				System.out.println("Update unsuccessful.");
				result = "invalidOldPassword";
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

	public LinkedList<StatementInfo> viewStatement (String membershipId,int month,int year){
		LinkedList<StatementInfo> statementRows = new LinkedList<StatementInfo>();

		try{    
			String query1 = "SELECT statementId FROM VideoLibrary.Statement WHERE statement.month = "+month+
					" AND statement.year = "+year+" AND statement.membershipId = "+membershipId;
			ResultSet result1 = stmt.executeQuery(query1);
			if(result1.next()){
				int statementId = result1.getInt("statementId");
			}
			else{
				AdminDAO admin = new AdminDAO();
				admin.generateMonthlyStatement(membershipId, month, year);
			}


			String query2 = "SELECT pymnt.rentDate,pymnt.totaldueAmount,movie.movieName,rnt.returnDate"+
					" FROM VideoLibrary.RentMovieTransaction rnt,VideoLibrary.PaymentTransaction pymnt,VideoLibrary.Movie,"+
					" VideoLibrary.StatementTransactions,VideoLibrary.Statement WHERE StatementTransactions.transactionId = "+
					" pymnt.transactionId AND pymnt.transactionId = rnt.transactionId AND rnt.movieId = Movie.movieId "+
					" AND Statement.statementId = StatementTransactions.statementId AND month = "+month+" AND year = "+year+
					" and Statement.membershipId = '"+membershipId+"'";
			
		//	System.out.println(query2); 
				

			ResultSet result2 = stmt.executeQuery(query2);

			while(result2.next()){
				StatementInfo stmnt = new StatementInfo();
				stmnt.setMovieName(result2.getString("movieName"));
				stmnt.setTotalDueAmount(result2.getString("totalDueAmount"));
				stmnt.setRentDate(result2.getDate("rentDate").toString());
				Date returnDate = result2.getDate("returnDate");
				if (returnDate == null){
					stmnt.setReturnDate("Not returned");
				}
				else{
					stmnt.setReturnDate(returnDate.toString());
				}	
				statementRows.add(stmnt);
			}
		}
		catch(SQLException e){
			statementRows = null;
			e.getMessage();
		}
		catch(Exception e){
			statementRows = null;
			e.getMessage();
		}
		return statementRows;
	}
	
	public User[] searchUserByFirstName(String adminInput){
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where firstName like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByLastName(String adminInput){
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where lastName like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByCity(String adminInput){
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where city like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByState(String adminInput){
		ArrayList<User> list= new ArrayList<User>();		
		String str = "%"+adminInput.replace(' ','%')+"%";
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where state like '"+str+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[] searchUserByMemberShipType(String adminInput){
		ArrayList<User> list= new ArrayList<User>();		
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where membershipType='"+adminInput+"'";
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
	
	public User[]  searchUserByMembershipId(int adminInput){
		ArrayList<User> list = new ArrayList<User>();
		String query="Select membershipId, userId,firstName, lastName,startDate, membershipType, address, city, state,zip from User where membershipId="+adminInput;
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
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
			e.printStackTrace();
		}
		User[] array= list.toArray(new User[list.size()]);
		return array;
	}
}

