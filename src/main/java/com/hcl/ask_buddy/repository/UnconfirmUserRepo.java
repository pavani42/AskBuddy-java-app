package com.hcl.ask_buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.ask_buddy.entity.UnConfirmedUser;

@Repository
public interface UnconfirmUserRepo extends JpaRepository<UnConfirmedUser, String>{
	@Query("select user from UnConfirmedUser user where user.token = ?1")
	public UnConfirmedUser getUserData(String token);

	@Modifying
	@Transactional
	@Query("update UnConfirmedUser user set user.token = ?1 where user.sap_Id = ?2")
	public int updateToken(String token, long sap_Id);
}
