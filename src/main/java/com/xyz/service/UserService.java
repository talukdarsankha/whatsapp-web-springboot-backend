package com.xyz.service;

import java.util.List;

import com.xyz.exception.UserException;
import com.xyz.models.User;
import com.xyz.request.UserRequest;

public interface UserService {
	
	public User findUserById(Integer id) throws UserException;
	
	public User findUserByToken(String jwt) throws Exception;
	
	public User updateUser(Integer userId, UserRequest updateUserRequest) throws UserException;
	
	public List<User> searchUser(String query);

}
