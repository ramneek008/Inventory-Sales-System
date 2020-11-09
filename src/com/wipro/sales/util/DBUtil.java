package com.wipro.sales.util;

import java.sql.*;

public class DBUtil {
	
	public static Connection getDBConnection() {
		Connection con = null;
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "123456"); //Enter your password
		// System.out.println("Connection done");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return con;
		
	}


}
