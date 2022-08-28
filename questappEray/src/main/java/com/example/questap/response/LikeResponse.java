package com.example.questap.response;

import com.example.questap.entities.Like;

import lombok.Data;

@Data
public class LikeResponse {

	private Long id;
	private Long userId;
	private Long postId;
	
	
	public LikeResponse(Like entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.postId = entity.getUser().getId();
	}
	
}
