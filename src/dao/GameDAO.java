package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameDAO extends GetConnectionToDataBase {
	
	private static Connection databaseConnection = GetConnection();
	
	private static int currentWins;
	private static int currentNumberOfGames;
	
	public static void UpdateNumberOfWins(String playerName) {
		try {
			PreparedStatement getCurrentWins = databaseConnection.prepareStatement("select numberofwins from playerdetails where playername = (?)");
			getCurrentWins.setString(1, playerName);
			ResultSet numberofwins = getCurrentWins.executeQuery();
			while(numberofwins.next()) {
				currentWins = numberofwins.getInt(1);
			}
			PreparedStatement updateWins = databaseConnection.prepareStatement("update playerdetails set numberofwins =(?) where playername = (?)");
			updateWins.setInt(1,currentWins+1);
			updateWins.setString(2, playerName);
			updateWins.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void UpdateNumberOfGames(String playerName) {
		try {
			PreparedStatement getCurrentWins = databaseConnection.prepareStatement("select numberofgames from playerdetails where playername = (?)");
			getCurrentWins.setString(1, playerName);
			ResultSet numberofgames = getCurrentWins.executeQuery();
			while(numberofgames.next()) {
				currentNumberOfGames = numberofgames.getInt(1);
			}
			PreparedStatement updateWins = databaseConnection.prepareStatement("update playerdetails set numberofgames =(?) where playername = (?)");
			updateWins.setInt(1,currentNumberOfGames-1);
			updateWins.setString(2, playerName);
			updateWins.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
