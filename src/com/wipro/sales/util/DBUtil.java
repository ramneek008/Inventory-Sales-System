package com.wipro.sales.util;

import java.sql.*;

public class DBUtil {
	
	public static Connection getDBConnection() {
		Connection con =null;
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "D@ta00Found");
		// System.out.println("Connection done");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return con;
		
	}

}
