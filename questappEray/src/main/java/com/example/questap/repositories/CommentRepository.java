package com.example.questap.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.questap.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long
> {	

	List<Comment> findByUserIdAndPostId(Long userId, Long postId);

	List<Comment> findByUserId(Long userId);

	List<Comment> findByPostId(Long postId);

	// user tablosu nun id si ile comment tablosunun user_id sini joinledik ve bu iki tablodan commentlerin post idsini, userların avatarını 
	// ve userların name ini cektik
	@Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from "
			+ "comment c left join user u on u.id = c.user_id "
			+ "where c.post_id in :postIds limit 5", nativeQuery = true)
	List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);
}
