package com.example.questap.dtos;

import lombok.Data;

@Data
public class CommentRequest {

	private Long id;
	private Long userId;
	private Long postId;
	private String text;
}
