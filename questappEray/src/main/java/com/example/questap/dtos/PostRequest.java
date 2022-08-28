package com.example.questap.dtos;

import lombok.Data;

@Data
public class PostRequest {

	private Long userId;
	private String title;
	private String text;
	
	
}
