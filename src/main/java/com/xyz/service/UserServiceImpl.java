package com.xyz.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.config.TokenProvider;
import com.xyz.exception.UserException;
import com.xyz.models.User;
import com.xyz.request.UserRequest;
import com.xyz.respository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public User findUserById(Integer id) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> opt = userRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new UserException("User Not Found with this id : "+id);
		

	}

	@Override
	public User findUserByToken(String jwt) throws Exception {
		// TODO Auto-generated method stub
		String email = tokenProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if (user==null) {
			throw new UserException("User Not Found with this email :"+email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UserRequest updateUserRequest) throws UserException {
		// TODO Auto-generated method stub
		
		User user = findUserById(userId);
		if (updateUserRequest.getFull_name()!=null) {
			user.setFull_name(updateUserRequest.getFull_name());
		}
		if (updateUserRequest.getProfile_picture()!=null) {
			user.setProfile_picture(updateUserRequest.getProfile_picture());
		}
		if (updateUserRequest.getPassword()!=null) {
			user.setPassword(updateUserRequest.getPassword());
		}
		
//		user.setId(userId);
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		// TODO Auto-generated method stub
		List<User> searchUsers = userRepository.searchUser(query);
		return searchUsers;
	}

}
