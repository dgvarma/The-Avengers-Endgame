package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetConnectionToDataBase {
	public static Connection GetConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","444");
		}
		catch(Exception connectionException) {
			System.out.println(connectionException);
		}
		return con;
	}
}
