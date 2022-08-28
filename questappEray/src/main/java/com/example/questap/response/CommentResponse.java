package com.example.questap.response;

import com.example.questap.entities.Comment;

import lombok.Data;

@Data
public class CommentResponse {

	Long id;
	Long userId;
	String text;
	String userName;
	
	public CommentResponse(Comment entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.text = entity.getText();
		this.userName = entity.getUser().getUserName();
	}
	
}
