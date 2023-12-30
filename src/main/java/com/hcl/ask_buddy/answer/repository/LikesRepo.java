package com.hcl.ask_buddy.answer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.ask_buddy.answer.entity.Answers;
import com.hcl.ask_buddy.answer.entity.Likes;
import com.hcl.ask_buddy.answer.entity.User;


@Repository
public interface LikesRepo extends JpaRepository<Likes, Double>{
	
	@Query("select likes from Likes likes where likes.user = ?1 and likes.answer = ?2")
	public Likes getlikes(User user, Answers answer);
	
	@Modifying
	@Transactional
	@Query("update Likes likes set likes.ansLike = true, likes.ansDislike = false where likes.user = ?1 and likes.answer = ?2")
	public int updateLikes(User user, Answers answer);
	
	@Modifying
	@Transactional
	@Query("update Likes likes set likes.ansLike = false, likes.ansDislike = true where likes.user = ?1 and likes.answer = ?2")
	public int updateDisLikes(User user, Answers answer);

	@Query("select count(*) from Likes likes where likes.ansLike = true and likes.answer.id = ?1")
	public int getNoOfLikes(long answer);
	
	@Query("select count(*) from Likes likes where likes.ansDislike = true and likes.answer.id = ?1")
	public int getNoOfDisLikes(long answer);
	
	@Query("select likes from Likes likes where likes.user = ?1")
	public List<Likes> getLikesList(User user);
}
