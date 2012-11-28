package edu.sjsu.videolibrary.data;

import java.util.Random;
import edu.sjsu.videolibrary.db.BaseUserDAO;
import edu.sjsu.videolibrary.db.DAOFactory;

public class PremiumMemberData {
	
	static void insertPremiumMemberData() throws Exception {
		String userData[][] = {
				{"jg@yahoo.com","jg*","James","George","Premium","Scott Blvd","SanJose","CA","94325"},
				{"sm2yahoo.com","sm*","Stacy","Mayer","Premium","Keily Blvd","SantaClara","CA","23456"},
				{"rs@gmail.com","rs#","Ram","Sheth","Premium","Newark Street","SunnyVale","IL","67956"},
				{"mk@hotmail.com","mk","Michael","Kaleta","Premium","7th and Santa Clara","Newyork","NY","89654"},
				{"sp@yahoo.com","sp","Sean","Parker","Premium","4th and Santa Clara","NewJersey","NJ","78956"},
				{"bh@gmail.com","bh","Bobby","Hill","Premium","Boston Drive","Seatle","WA","80876"},
				{"rv@yahoo.com","rv","Robin","Vanguard","Premium","Pebble Dr","Anaheim","MO","54637"},
				{"rm@gmail.com","rm","Riya","Mehta","Premium","Idaho Dr","Gilroy","CA","93487"},
				{"sk@gmail.com","sk","Suhana","Kapoor","Premium","Washington Street","MorganHill","MA","23746"},
				{"jk@gmail.com","ja","Jiya","Khan","Premium","Alpine Street","Fremont","CA","92034"}
		};
		BaseUserDAO userDAO = DAOFactory.getUserDAO();
		
		for( int i = 1; i < 1001; i++) {
			for(String[] user : userData) {
								
				userDAO.signUpUser(i+user[0].trim(),user[1].trim()+i,user[4].trim(),user[2].trim()+i,user[3].trim()+i,user[5].trim(),user[6].trim(),user[7].trim(),user[8].trim(),getCC());
				
			}
			userDAO.release();
			userDAO = DAOFactory.getUserDAO();
			if( i % 10 == 0 ) {
				System.out.println(".");
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		insertPremiumMemberData();
	}
	
	public static String getCC() {
		StringBuilder builder = new StringBuilder();
		builder.append( new Random().nextInt(9)+1);
		for(int i = 0 ; i< 15; i++ ) {
			builder.append( new Random().nextInt(10));
		}
		return builder.toString();
	}
	

}
