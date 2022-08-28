package com.example.questap.dtos;

import lombok.Data;

@Data
public class LikeRequest {

	private Long id;
	
	private long postId;
	
	private long userId;
}
