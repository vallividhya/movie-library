package edu.sjsu.videolibrary.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import edu.sjsu.videolibrary.model.Transaction;
import edu.sjsu.videolibrary.db.SimpleAdminDAO;
import edu.sjsu.videolibrary.exception.NoUserFoundException;
import edu.sjsu.videolibrary.model.Movie;
import edu.sjsu.videolibrary.model.StatementInfo;
import edu.sjsu.videolibrary.model.User;
import edu.sjsu.videolibrary.util.Utils;

public class SimpleUserDAO extends BaseUserDAO {

	public SimpleUserDAO() {
		super();
	}

	public SimpleUserDAO(String transactionId) {
		super(transactionId);
	}

	public User signUpUser(String userId, String password, String memType,
			String firstName, String lastName, String address, String city,
			String state, String zipCode, String ccNumber) throws SQLException {
		User user = new User();

		// System.out.println("State:"+state);

		String sql = "INSERT INTO user (userId,password,membershipType,startDate,firstName,lastName,"
				+ "address,city,state,zip,creditCardNumber,latestPaymentDate) VALUES (?,?,?,NOW(),?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql,
				PreparedStatement.RETURN_GENERATED_KEYS);
		pst.setString(1, userId);
		pst.setString(2, Utils.encryptPassword(password));
		pst.setString(3, memType);
		pst.setString(4, firstName);
		pst.setString(5, lastName);
		pst.setString(6, address);
		pst.setString(7, city);
		pst.setString(8, state);
		pst.setString(9, zipCode);
		pst.setString(10, ccNumber);
		pst.setString(11, null);
		pst.execute();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next()) {
			int membershipId = rs.getInt("membershipId");
			Date date = new Date();

			user.setMembershipId(membershipId);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setCity(city);
			user.setCreditCardNumber(ccNumber);
			user.setMembershipType(memType);
			user.setState(state);
			user.setZip(zipCode);
			user.setStartDate(date.toString());
			user.setLatestPaymentDate(null);
			user.setUserId(userId);
			user.setPassword(password);
		} else {
			user = null;
		}
		return user;
	}

	public String signUpAdmin(String userId, String password, String firstName,
			String lastName) throws SQLException {
		String result = null;
		String sql = "INSERT INTO admin (userId,password,firstName,lastName) VALUES (?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, userId);
		pst.setString(2, Utils.encryptPassword(password));
		pst.setString(3, firstName);
		pst.setString(4, lastName);
		pst.execute();
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	public String signInUser(String userId, String password)
			throws SQLException {
		String result = null;
		String sql = "SELECT userId, password FROM user WHERE userId = '"
				+ userId + "'" + " AND password = '"
				+ Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	public String signInAdmin(String userId, String password)
			throws SQLException {
		String result = null;
		String sql = "SELECT userId, password FROM admin where userId = '"
				+ userId + "'" + " AND password = '"
				+ Utils.encryptPassword(password) + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	public LinkedList<Transaction> viewAccountTransactions(int membershipId) {
		LinkedList<Transaction> ac = new LinkedList<Transaction>();
		try {
			String query1 = "select Movie.MovieName,pymnt.RentDate,rnt.ReturnDate,User.MembershipType from "
					+ " VideoLibrary.RentMovieTransaction rnt,VideoLibrary.PaymentTransaction pymnt, "
					+ " VideoLibrary.Movie,VideoLibrary.User  where User.MembershipId = "
					+ membershipId
					+ " and pymnt.TransactionId = rnt.TransactionId "
					+ " and rnt.MovieId = Movie.MovieId and pymnt.MembershipId = User.MembershipId";

			ResultSet result1 = stmt.executeQuery(query1);

			while (result1.next()) {
				Transaction trans = new Transaction();
				trans.setMovieName(result1.getString("MovieName"));
				String membershipType = result1.getString("MembershipType");
				if (membershipType.equalsIgnoreCase("Simple")) {
					trans.setPerMovieAmount(1.5);
				} else {
					trans.setPerMovieAmount(0.0);
				}
				trans.setPurchaseDate((result1.getDate("RentDate").toString()));
				Date returnDate = result1.getDate("ReturnDate");
				if (returnDate != null) {
					trans.setReturnDate((returnDate).toString());
				} else {
					trans.setReturnDate("Not Returned");
				}

				ac.add(trans);
			}
		} catch (SQLException e) {
			e.getMessage();
			return null;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
		return ac;
	}

	public String makeMonthlyPayment(int membershipId) {
		String result = null;
		try {
			String query1 = "UPDATE VideoLibrary.User SET latestPaymentDate = NOW() "
					+ "WHERE User.membershipId = " + membershipId;
			int rowcount1 = stmt.executeUpdate(query1);
			if (rowcount1 < 0) {
				result = "false";
				return result;
			}

			SimpleAdminDAO adminDAO = new SimpleAdminDAO();
			double monthlyFees = adminDAO.getMonthlyFeesForPremiumMember();

			String query2 = "INSERT INTO VideoLibrary.PaymentTransaction(rentDate,totalDueAmount,membershipId)"
					+ " VALUES (NOW(),"
					+ monthlyFees
					+ ","
					+ membershipId
					+ ")";
			int rowcount2 = stmt.executeUpdate(query2);

			if (rowcount2 > 0) {
				result = "true";
				System.out.println("Payment Successful");
			} else {
				System.out.println("Payment could not be proceeded.");
				result = "false";
			}

		} catch (SQLException e) {
			e.getMessage();
			return null;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
		return result;
	}

	public String updateUserInfo(int membershipId, String userId,
			String firstName, String lastName, String address, String city,
			String state, String zipCode, String membershipType,
			String creditCardNumber) {

		String result = null;
		try {

			String query1 = "update VideoLibrary.User set userId = '" + userId
					+ "' ,membershipType = '" + membershipType
					+ " ',firstName = '" + firstName + "' ,lastName = '"
					+ lastName + "',address = '" + address + "',city = '"
					+ city + "',state = '" + state + "',zip = '" + 98567
					+ "' ,creditCardNumber = '" + creditCardNumber
					+ " ' where membershipId = " + membershipId;

			int rowcount = stmt.executeUpdate(query1);

			if (rowcount > 0) {
				result = "true";
				System.out.println("Update Successful");
			} else {
				System.out.println("Update unsuccessful.");
				result = "false";
			}

		} catch (SQLException e) {
			e.getMessage();
			result = "false";
		} catch (Exception e) {
			e.getMessage();
			result = null;
		}
		return result;

	}

	public String updatePassword(int membershipId, String oldPassword,
			String newPassword) {
		String result = null;

		try {
			String query1 = "UPDATE VideoLibrary.User SET password = '"
					+ Utils.encryptPassword(newPassword)
					+ "' WHERE membershipId = " + membershipId
					+ " AND password = '" + Utils.encryptPassword(oldPassword)
					+ "'";

			int rowcount = stmt.executeUpdate(query1);

			if (rowcount > 0) {
				result = "true";
				System.out.println("Update Successful");
			} else {
				System.out.println("Update unsuccessful.");
				result = "invalidOldPassword";
			}
		} catch (SQLException e) {
			e.getMessage();
			result = "false";
		} catch (Exception e) {
			e.getMessage();
			result = null;
		}
		return result;
	}

	public LinkedList<StatementInfo> viewStatement(int membershipId, int month,
			int year) {
		LinkedList<StatementInfo> statementRows = new LinkedList<StatementInfo>();

		// TODO: Need to check the month is not earlier than user join date
		try {
			String query1 = "SELECT statementId FROM VideoLibrary.Statement WHERE statement.month = "
					+ month
					+ " AND statement.year = "
					+ year
					+ " AND statement.membershipId = " + membershipId;
			ResultSet result1 = stmt.executeQuery(query1);
			if (result1.next()) {
				result1.getInt("statementId");
			} else {
				SimpleAdminDAO admin = new SimpleAdminDAO();
				admin.generateMonthlyStatement(membershipId, month, year);
			}

			String query2 = "SELECT pymnt.rentDate,pymnt.totaldueAmount,movie.movieName,rnt.returnDate"
					+ " FROM VideoLibrary.RentMovieTransaction rnt,VideoLibrary.PaymentTransaction pymnt,VideoLibrary.Movie,"
					+ " VideoLibrary.StatementTransactions,VideoLibrary.Statement WHERE StatementTransactions.transactionId = "
					+ " pymnt.transactionId AND pymnt.transactionId = rnt.transactionId AND rnt.movieId = Movie.movieId "
					+ " AND Statement.statementId = StatementTransactions.statementId AND month = "
					+ month
					+ " AND year = "
					+ year
					+ " and Statement.membershipId = " + membershipId;

			// System.out.println(query2);

			ResultSet result2 = stmt.executeQuery(query2);

			while (result2.next()) {
				StatementInfo stmnt = new StatementInfo();
				stmnt.setMovieName(result2.getString("movieName"));
				stmnt.setTotalDueAmount(result2.getString("totalDueAmount"));
				stmnt.setRentDate(result2.getDate("rentDate").toString());
				Date returnDate = result2.getDate("returnDate");
				if (returnDate == null) {
					stmnt.setReturnDate("Not returned");
				} else {
					stmnt.setReturnDate(returnDate.toString());
				}
				statementRows.add(stmnt);
			}
		} catch (SQLException e) {
			statementRows = null;
			e.getMessage();
		} catch (Exception e) {
			statementRows = null;
			e.getMessage();
		}
		return statementRows;
	}

	public User[] searchUser(String membershipId, String userId,
			String membershipType, String startDate, String firstName,
			String lastName, String address, String city, String state,
			String zipCode) throws NoUserFoundException {

		ArrayList<User> searchList = new ArrayList<User>();
		User[] userArray = null;
		Map<String, String> queryParameters = new HashMap<String, String>();
		if (membershipId != null) {
			queryParameters.put("membershipId", membershipId);
		}
		if (userId != null) {
			queryParameters.put("userId", userId);
		}
		if (membershipType != null) {
			queryParameters.put("membershipType", membershipType);
		}
		if (startDate != null) {
			queryParameters.put("startDate", startDate);
		}
		if (firstName != null) {
			queryParameters.put("firstName", firstName);
		}
		if (lastName != null) {
			queryParameters.put("lastName", lastName);
		}
		if (address != null) {
			queryParameters.put("address", address);
		}
		if (city != null) {
			queryParameters.put("city", city);
		}
		if (state != null) {
			queryParameters.put("state", state);
		}
		if (zipCode != null) {
			queryParameters.put("zipCode", zipCode);
		}

		StringBuilder query = new StringBuilder("SELECT membershipId, userId, password, membershipType, startDate, firstName, lastName, address, city, state, zip, creditCardNumber, latestPaymentDate FROM videolibrary.user WHERE ");

		Iterator<Entry<String, String>> paramIter = queryParameters.entrySet().iterator();
		while (paramIter.hasNext()) {
			Entry<String, String> entry = paramIter.next();
			query.append(entry.getKey());
			query.append(" LIKE '%").append(entry.getValue()).append("%'");
			if (paramIter.hasNext()) {
				query.append(" AND ");
			}
		}

		Statement stmt = null;
		System.out.println(" My search Query : " + query);
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			if (!rs.isBeforeFirst()) {
				throw new NoUserFoundException(
						"No users found with the given search criteria");
			}
			while (rs.next()) {
				User user = new User();
				user.setMembershipId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setMembershipType(rs.getString(4));
				user.setStartDate(rs.getDate(5).toString());
				user.setFirstName(rs.getString(6));
				user.setLastName(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setCity(rs.getString(9));
				user.setState(rs.getString(10));
				user.setZip(rs.getString(11));
				user.setCreditCardNumber(rs.getString(12));
				Date paymentDate = rs.getDate(13); 
				if (paymentDate != null) {
					user.setLatestPaymentDate(paymentDate.toString()); 
				}
				System.out.println(user.getMembershipId());
				searchList.add(user);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		userArray = searchList.toArray(new User[searchList.size()]);
		return userArray;
	}
}
