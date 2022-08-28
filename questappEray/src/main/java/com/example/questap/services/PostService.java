package com.example.questap.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.questap.dtos.PostRequest;
import com.example.questap.dtos.PostUpdateRequest;

import com.example.questap.entities.Post;
import com.example.questap.entities.User;
import com.example.questap.repositories.PostRepository;
import com.example.questap.response.LikeResponse;
import com.example.questap.response.PostResponse;


@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserService userService;
	
	private LikeService likeService;

	
	public void setLikeService(LikeService likeSevice) {
		this.likeService = likeService;
	}
	
	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if(userId.isPresent()) {
			 list = postRepo.findByUserId(userId.get());
		}else
			list = postRepo.findAll();
		return list.stream().map(p -> { 
			List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
			return new PostResponse(p, likes);}).collect(Collectors.toList());
	}

	
	
	@PostMapping
	public Post createOnePost(PostRequest request) {
		User user = this.userService.getOneUserById(request.getUserId());
		if(user == null)
			return null;
		Post p = new Post();
		p.setUser(user);
		p.setTitle(request.getTitle());
		p.setText(request.getText());
		p.setCreateDate(new Date());
		return this.postRepo.save(p);
		
	}

	public Post getOnePostById(Long postId) {
		return this.postRepo.findById(postId).orElse(null);
	}

	public PostResponse getOnePostByIdWithLikes(Long postId) {
		Post post =  this.postRepo.findById(postId).orElse(null);
		List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(postId));
		return new PostResponse(post, likes);
	}
	
	public Post updateOnePostById(Long postId, PostUpdateRequest request) {
		Optional<Post> post = this.postRepo.findById(postId);
		if(post.isPresent()) {
			Post p = post.get();
			p.setText(request.getText());
			p.setTitle(request.getTitle());
			return this.postRepo.save(p);
		}
		return null;
		
		
	}

	public void deleteOnePost(Long postId) {
		this.postRepo.deleteById(postId);
		
	}
	
	
	
}
