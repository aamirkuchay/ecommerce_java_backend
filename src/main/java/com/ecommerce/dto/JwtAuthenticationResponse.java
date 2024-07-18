package com.ecommerce.dto;


import com.ecommerce.entity.User;

public class JwtAuthenticationResponse {
	
	private String token;
    private String refreshToken;
    private User user;
    
    public JwtAuthenticationResponse() {}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "JwtAuthenticationResponse [token=" + token + ", refreshToken=" + refreshToken + "]";
	}
    
    
    

}
