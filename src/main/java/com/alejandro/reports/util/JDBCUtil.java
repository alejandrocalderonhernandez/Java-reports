package com.alejandro.reports.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
	
	public static Connection getConnection(String propsPath) {
		try {
			Properties props = new Properties();
			props.load(new FileReader(propsPath));
			Class.forName(props.getProperty("database.driver"));
			return DriverManager.getConnection(
					props.getProperty("database.url"),
					props.getProperty("database.user"),
					props.getProperty("database.pass")
			);
		} catch (SQLException e) {
			System.err.println("Error to create connection: " + e.getMessage());
			throw new RuntimeException();
		} catch (IOException e) {
			System.err.println("Error to get properties" + e.getMessage());
			throw new RuntimeException();
		} catch (ClassNotFoundException e) {
			System.err.println("Error to get driver class" + e.getMessage());
			throw new RuntimeException();
		}
	}

}
