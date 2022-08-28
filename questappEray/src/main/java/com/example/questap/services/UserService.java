package com.example.questap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.questap.dtos.UserRequest;
import com.example.questap.entities.User;
import com.example.questap.repositories.CommentRepository;
import com.example.questap.repositories.LikeRepository;
import com.example.questap.repositories.PostRepository;
import com.example.questap.repositories.UserRepository;

@Service
public class UserService {

	
	UserRepository userRepo;
	
	LikeRepository likeRepo;
	
	CommentRepository commentRepo;
	
	PostRepository postRepository;
	
	
	
	public UserService(UserRepository userRepo, LikeRepository likeRepo, CommentRepository commentRepo,
			PostRepository postRepository) {
		this.userRepo = userRepo;
		this.likeRepo = likeRepo;
		this.commentRepo = commentRepo;
		this.postRepository = postRepository;
	}



	public List<User> getAll(){
		return this.userRepo.findAll();
	}
	
	

	public User addUser(UserRequest request) {
		User u = new User();
		u.setUserName(request.getUserName());
		u.setPassword(request.getPassword());
		this.userRepo.save(u);
		return u;
	}
	
	

	public User saveOneUser(User user) {
		return userRepo.save(user);
	}
	
	
	public User getOneUserById(Long userId) {
		return this.userRepo.findById(userId).orElse(null);
	}
	
	
	public User updateOneUser(Long userId,UserRequest request) {
		Optional<User> user = this.userRepo.findById(userId);
		if(user.isPresent()) {
			User u = user.get();
			u.setUserName(request.getUserName());
			u.setPassword(request.getPassword());
			this.userRepo.save(u);
			return u;
		}
		else {
			return null;
		}
	}
	
	
	public void deleteOneUser(Long userId) {
		this.userRepo.deleteById(userId);
	}



	public User getOneUserByUserName(String userName) {
		return this.userRepo.findByUserName(userName);
	}

	// idsi verilen user'ın yaptıgı aktiviteleri( like ve yorumları) döndük 
	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = postRepository.findTopByUserId(userId);
		if(postIds.isEmpty())
			return null;
		List<Object> comments = commentRepo.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepo.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}
	
}
