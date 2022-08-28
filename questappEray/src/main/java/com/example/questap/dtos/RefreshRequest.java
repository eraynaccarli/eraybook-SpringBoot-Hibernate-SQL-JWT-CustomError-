package com.example.questap.dtos;

import lombok.Data;

@Data
public class RefreshRequest {

	private Long userId;
	private String refreshToken;
}
