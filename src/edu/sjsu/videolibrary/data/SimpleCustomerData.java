package edu.sjsu.videolibrary.data;

import java.util.Random;
import edu.sjsu.videolibrary.db.BaseUserDAO;
import edu.sjsu.videolibrary.db.DAOFactory;

public class SimpleCustomerData {
	
	static void insertsimpleMemberData() throws Exception {
		String userData[][] = {
				{"js@yahoo.com","js*","Jack","Smith","Simple","Scott Blvd","SanJose","CA","94325"},
				{"jm2yahoo.com","jm*","John","Mayer","Simple","Keily Blvd","SantaClara","CA","23456"},
				{"mm@gmail.com","mm#","Mike","McLinn","Simple","Newark Street","SunnyVale","IL","67956"},
				{"mh@hotmail.com","mh","Matt","Huleka","Simple","7th and Santa Clara","Newyork","NY","89654"},
				{"ws@yahoo.com","wh","Will","Shane","Simple","4th and Santa Clara","NewJersey","NJ","78956"},
				{"bs@gmail.com","bs","Bob","Smith","Simple","Boston Drive","Seatle","WA","80876"},
				{"ry@yahoo.com","ry","Rob","Yang","Simple","Pebble Dr","Anaheim","MO","54637"},
				{"rh@gmail.com","rh","Ryan","Hung","Simple","Idaho Dr","Gilroy","CA","93487"},
				{"sl@gmail.com","sl","Shen","Li","Simple","Washington Street","MorganHill","MA","23746"},
				{"ja@gmail.com","ja","Juan","Anthony","Simple","Alpine Street","Fremont","CA","92034"}
		};
		BaseUserDAO userDAO = DAOFactory.getUserDAO();
		
		for( int i = 1; i < 1001; i++) {
			for(String[] user : userData) {
								
				userDAO.signUpUser(i+user[0].trim(),user[1].trim()+i,user[4].trim(),user[2].trim()+i,user[3].trim()+i,user[5].trim(),user[6].trim(),user[7].trim(),user[8].trim(),null);
				
			}
			userDAO.release();
			userDAO = DAOFactory.getUserDAO();
			if( i % 10 == 0 ) {
				System.out.println(".");
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		insertsimpleMemberData();
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
