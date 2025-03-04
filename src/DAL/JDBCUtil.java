package DAL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class JDBCUtil {
	// Properties
	private static JDBCUtil instance;

	// Constructors
	public JDBCUtil() {

	}

	// Methods
	public static JDBCUtil getInstance() {
		if (JDBCUtil.instance == null) {
			JDBCUtil.instance = new JDBCUtil();
		}
		return JDBCUtil.instance;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String url = "jdbc:mysql://localhost:3306/Karaoke";
			String username = "root";
			String password = "quy1832258";
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printInfo(Connection connection) {
		try {
			DatabaseMetaData mtdt = connection.getMetaData();
			System.out.println(mtdt.getDatabaseProductName().toString());
			System.out.println(mtdt.getDatabaseProductVersion().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
