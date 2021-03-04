package com.alejandro.reports.model;

import com.google.gson.Gson;

public class Actor {
	
    private String firstname;
	private String lastname;

	public Actor(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this).toString();
	}
	

}
