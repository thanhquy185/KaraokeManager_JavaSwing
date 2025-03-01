package DAL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class JDBCUtil {
	// Properties
	private static Connection connection;

	// Methods
	public static Connection getConnection() {
		JDBCUtil.connection = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String url = "jdbc:mysql://localhost:3306/Karaoke";
			String username = "root";
			String password = "quy1832258";
			JDBCUtil.connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeConnection() {
		try {
			if (JDBCUtil.connection != null) {
				JDBCUtil.connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printInfo(Connection c) {
		try {
			DatabaseMetaData mtdt = c.getMetaData();
			System.out.println(mtdt.getDatabaseProductName().toString());
			System.out.println(mtdt.getDatabaseProductVersion().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
