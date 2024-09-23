package com.xyz.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	@Query("SELECT u from User u where u.full_name like %:query%")
	public List<User> searchUser(String query);

}
