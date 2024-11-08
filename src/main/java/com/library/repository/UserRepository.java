package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("Select u from User u where u.userName =?1 ")
	User findUserByName(String name);

	@Query("Select u.userName from User u where u.id =?1 ")
	String findUserNameByID(Long id);
}
