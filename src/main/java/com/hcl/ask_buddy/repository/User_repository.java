package com.hcl.ask_buddy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.ask_buddy.entity.User_entity;

// User Repository
@Repository
public interface User_repository extends JpaRepository<User_entity,String>{
	@Query("select user from User_entity user where user.sap_Id = ?1 and user.password = ?2")
	public User_entity login(long userId, String password);
	
	public User_entity getByUsername(String username);
	
	public User_entity getByMail(String usermail);

	@Query("select user from User_entity user where user.sap_Id = ?1")
	public User_entity getBySap_Id(long id);

	@Modifying
	@Transactional
	@Query("update User_entity user set user.password = ?1 where user.sap_Id = ?2 and user.mail = ?3")
	public int updatePassword(String password, long sap_Id, String mail);
	
	@Modifying
	@Transactional
	@Query("update User_entity user set user.pic = ?1 where user.sap_Id = ?2")
	public int uploadImage(byte[] img, long sap_Id);
	
  

}
