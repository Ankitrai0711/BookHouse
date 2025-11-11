package com.example.bookstore.entity;

//AuthenticationResponse.java

public class AuthenticationResponse {
	private String jwtToken;
	private User user;

	public AuthenticationResponse(String jwtToken, User user) {
		this.jwtToken = jwtToken;
		this.user = user;
	}

	// Getters and setters
	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}