import java.sql.*;

public class DBConn {
	public static Connection dbConnection() {
		Connection conn;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/employeedb?serverTimezone=UTC", "root", "0000");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("\n - DB 연결 에러 - \n");
			return null;
		}
	}
}
