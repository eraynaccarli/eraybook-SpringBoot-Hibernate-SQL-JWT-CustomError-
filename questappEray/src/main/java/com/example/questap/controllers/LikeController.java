package com.example.questap.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questap.dtos.LikeRequest;
import com.example.questap.entities.Like;
import com.example.questap.response.LikeResponse;
import com.example.questap.services.LikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {

	@Autowired
	private LikeService likeService; 


	@GetMapping
	public List<LikeResponse> getAllLikes(@RequestParam  Optional<Long> userId, @RequestParam Optional<Long> postId) {
		return this.likeService.getAllLikesWithParam(userId, postId);
	}
	
	@GetMapping("/{likeId}")
	public Like getOneLikeById(@PathVariable Long likeId) {
		return this.likeService.getOneLikeById(likeId);
	}
	
	@PostMapping
	public Like createOneLike(@RequestBody LikeRequest request) {
		return this.likeService.createOneLike(request);
	}
	
	@DeleteMapping("/{likeId}")
	public void deleteOneLike(@PathVariable Long likeId) {
	    this.deleteOneLike(likeId);
	}
}
