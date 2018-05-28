package com.marcin.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionCreator;

public class JdbcTest {

	public static void main(String[] args) {
		
		String username = "accessor";
		String password = "accessor";
		String jdbcUrl = "jdbc:mysql://localhost:3306/phonebook_contacts?useSSL=false&serverTimezone=UTC";
	
		try {
			DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Connection succesfull!");
		} catch (SQLException e) {
			System.out.println("Connection unsuccesfull.");
			e.printStackTrace();
		}
	}	
}
