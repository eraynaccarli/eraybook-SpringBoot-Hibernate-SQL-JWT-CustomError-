package com.example.questap.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questap.dtos.CommentRequest;
import com.example.questap.dtos.CommentUpdateRequest;
import com.example.questap.entities.Comment;
import com.example.questap.entities.Post;
import com.example.questap.entities.User;
import com.example.questap.repositories.CommentRepository;
import com.example.questap.response.CommentResponse;

@Service
public class CommentService {
	

	private CommentRepository commentRepo;
	private UserService userService;
	private PostService postService;

	
	@Autowired
	public CommentService(CommentRepository commentRepo, UserService userService, PostService postService) {
		super();
		this.commentRepo = commentRepo;
		this.userService = userService;
		this.postService = postService;
	}


	public List<CommentResponse> getAllCommentsWithParam( Optional<Long> userId, Optional<Long> postId) {
		List<Comment> comments;
		
		if(userId.isPresent() && postId.isPresent()) {
			comments = this.commentRepo.findByUserIdAndPostId(userId.get(),postId.get());
		}
		else if(userId.isPresent()) {
			comments = this.commentRepo.findByUserId(userId.get());
		}
		else if(postId.isPresent()) {
			comments = this.commentRepo.findByPostId(userId.get());
		}
		else {
			comments = this.commentRepo.findAll();
		}
			return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	}




	public Comment getOneCommentById(Long commentId) {
		return this.commentRepo.findById(commentId).orElse(null);
	}



	public Comment createOneComment(CommentRequest request) {
		User u  = this.userService.getOneUserById(request.getUserId());
		Post p = this.postService.getOnePostById(request.getPostId());
		
		if(u != null && p != null ) {
			Comment c = new Comment();
			c.setPost(p);
			c.setUser(u);
			c.setText(request.getText());
			c.setCreateDate(new Date());
			return this.commentRepo.save(c);
		}
		else {
			return null;
		}
		
	}


	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
		Optional <Comment> c = this.commentRepo.findById(commentId);
		if(c.isPresent()) {
			Comment commentToUpdate = c.get();
			commentToUpdate.setText(request.getText());
			return this.commentRepo.save(commentToUpdate);
		}
		else {
			return null;
		}
	}


	public void deleteOneCommentById(Long commentId) {
		this.commentRepo.deleteById(commentId);
		
	}
	
	
}
