package com.kartik.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/rrs_db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "password";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading JDBC driver");
			e.printStackTrace();
		}
	}
	
	public static Connection Connect() {

		Connection con = null;
		
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Connection Successful.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in establishing connection to the database." + e.getMessage());
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void closeResources(Connection con, PreparedStatement ps, ResultSet rs) {
		
		try {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBUtils.Connect();
	}

}
