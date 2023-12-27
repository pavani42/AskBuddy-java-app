package com.hcl.ask_buddy.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.ask_buddy.entity.UserOtp;
import com.hcl.ask_buddy.entity.User_entity;

@Repository
public interface UserOtp_repository extends JpaRepository<UserOtp, Integer>{
	
	@Modifying
	@Transactional
	@Query("update UserOtp user_otp set user_otp.otp = ?1, user_otp.dtntm = ?2 where user_otp.user = ?3")
	public int updateOtp(String otp, LocalDateTime timestam, User_entity user);
	
	@Query("select userotp from UserOtp userotp where userotp.user = ?1")
	public UserOtp getUser(User_entity user);
	
	@Modifying
	@Transactional
	@Query("delete from UserOtp user_otp where user_otp.user =?1")
	public void deleteByUser(User_entity user);

}
