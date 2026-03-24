package restfiul_jersy;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TestConnect {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/soa";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	static Connection dbConnection = null;
	static Statement statement = null;

	public static void main(String[] args) throws SQLException {
// TODO Auto-generated method stub
		String insertTableSQL = "INSERT INTO CUSTOMER" + "(Name, pwd)" + "VALUES" + " ('HelloHibernate', '123')";
		dbConnection = getDBConnection();
		statement = (Statement) dbConnection.createStatement();
		statement.executeUpdate(insertTableSQL);
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
}
