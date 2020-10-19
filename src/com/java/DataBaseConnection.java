package com.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
	
	public void database() {
		
		// JDBC driver name and database URL
		
		final String JDBC_DRIVER = "  ";
		final String DB_URL = " ";
		
		// DataBase Credentials
		
		final String user = "  ";
		final String pwd = "  ";
		
		
		Connection conn = null;
		Statement stmt = null;
		
		
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			
			try {
				//open the connection
				conn = DriverManager.getConnection(DB_URL, user, pwd);
				
				//Execute Query
				stmt = conn.createStatement();
				String sql = "SELECT * from tablename";
				ResultSet executequery = stmt.executeQuery(sql);
				
				// Extract data from Result set
				
				while(executequery.next()) {
					
					String fname = executequery.getString("First_Name");
					String lname = executequery.getString("Last_Name");
					
					System.out.println("First name: "+fname+"Last name: "+lname);
					
				}
				
				// close all connections
				
				executequery.close();
				stmt.close();
				conn.close();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
