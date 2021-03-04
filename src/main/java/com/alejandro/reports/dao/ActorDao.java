package com.alejandro.reports.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


import com.alejandro.reports.model.Actor;

public class ActorDao {
   
	private Connection connection;
	
	private static final String QUERY = "SELECT first_name , last_name FROM actor";
	
	public ActorDao(Connection connection) {
		this.connection = connection;
	}
	
	public Set<Actor> findAll() {
		Set<Actor> result = new HashSet<>();
		try {
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);
			while(rs.next()) {
				result.add(new Actor(rs.getString("first_name"), rs.getString("last_name")));
			}
		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
		}
		return result;
	
	}

}
