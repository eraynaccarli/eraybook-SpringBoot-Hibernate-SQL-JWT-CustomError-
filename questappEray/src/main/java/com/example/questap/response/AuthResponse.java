package com.example.questap.response;

import lombok.Data;

@Data
public class AuthResponse {

	 String message;
	
	 Long userId;
	
	 String accessToken;
	
	 String refreshToken;
}
