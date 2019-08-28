package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PlayerLoginDAO extends GetConnectionToDataBase{
	
	private static Connection databaseConnection = GetConnection();
	private static boolean isPlayerInDB;
	
	public static boolean IsPlayerInDataBaseVerification(String player) {
		try {
			Statement queryStatement = databaseConnection.createStatement();
			ResultSet playerNames = queryStatement.executeQuery("select playername from playerdetails");
			while(playerNames.next()) {
				System.out.println(playerNames.getString(1));
				if(playerNames.getString(1).equals(player)) {
					System.out.println(playerNames.getString(1));
					isPlayerInDB = true;
					break;
				}
				else {
					isPlayerInDB = false;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return isPlayerInDB;
	}
	
	public static boolean RegisterPlayerInDataBase(String player) {
		try {
				PreparedStatement registerStatement = databaseConnection.prepareStatement("insert into playerdetails values(?,?,?)");
				registerStatement.setString(1, player);
				registerStatement.setInt(2, 0);
				registerStatement.setInt(3, 0);
				int rowsUpdated = registerStatement.executeUpdate();
				System.out.println(rowsUpdated + " row is updated in the database");
				return true;
		}
		catch(Exception e) {
			return false; 
		}
	}
}
