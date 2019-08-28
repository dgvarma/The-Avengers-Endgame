package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileDAO extends GetConnectionToDataBase{
	private static Connection databaseConnection = GetConnection();
	private static int numberOfWins;
	private static int numberOfPlayedGames;
	private static boolean isNameUpdated;
	private static int currentNumberOfGames;
	
	public static int GetNumberOfGamesWon(String playerName) {
		try {
			PreparedStatement getWins = databaseConnection.prepareStatement("select numberofwins from playerdetails where playername = (?)");
			getWins.setString(1, playerName);
			ResultSet wins = getWins.executeQuery();
			while(wins.next()) {
				numberOfWins = wins.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfWins;
	}
	
	public static int GetNumberOfGamesPlayed(String playerName) {
		try {
			PreparedStatement getWins = databaseConnection.prepareStatement("select numberofgames from playerdetails where playername = (?)");
			getWins.setString(1, playerName);
			ResultSet wins = getWins.executeQuery();
			while(wins.next()) {
				numberOfPlayedGames = wins.getInt(1);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfPlayedGames;
	}
	
	public static boolean ChangeTheNameOfPlayer(String editedName, String playerName) {
		try {
			PreparedStatement updateName = databaseConnection.prepareStatement("update playerdetails set playername = (?) where playername = (?)");
			updateName.setString(1, editedName);
			updateName.setString(2, playerName);
			updateName.executeUpdate();
			isNameUpdated = true;
		}
		catch(Exception e) {
			System.out.println(e);
			isNameUpdated = false;
		}
		return isNameUpdated;
	}
	
	public static void DeleteProfile(String playerName) {
		try {
			PreparedStatement updateName = databaseConnection.prepareStatement("delete from playerdetails where playername = (?)");
			updateName.setString(1, playerName);
			updateName.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void UpdateNumberOfGamesPlayed(String playerName) {
		try {
			PreparedStatement getCurrentWins = databaseConnection.prepareStatement("select numberofgames from playerdetails where playername = (?)");
			getCurrentWins.setString(1, playerName);
			ResultSet numberofgames = getCurrentWins.executeQuery();
			while(numberofgames.next()) {
				currentNumberOfGames = numberofgames.getInt(1);
			}
			PreparedStatement updateWins = databaseConnection.prepareStatement("update playerdetails set numberofgames =(?) where playername = (?)");
			updateWins.setInt(1,currentNumberOfGames+1);
			updateWins.setString(2, playerName);
			updateWins.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
