package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Driver;

//数据库类
public class DataBase {
	private static Driver driver;
	private static Connection connection;
	private static Statement statement;

	// 连接数据库，返回statement对象
	public static Statement getStatement() {
		try {
			driver = new Driver();
			Properties info = new Properties();
			info.put("user", "root");
			info.put("password", "root");
			connection = driver.connect(
					"jdbc:mysql://localhost:3306/phonebook", info);
			statement = connection.createStatement();
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 与数据库断开连接
	public static void disconnectDataBase() {

	}
}
