package com.xyz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.models.User;
import com.xyz.request.UserRequest;
import com.xyz.response.ApiResponse;
import com.xyz.respository.UserRepository;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController  {
	
	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws Exception{
	 User user=	userService.findUserByToken(token);
	 return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> serchUserHandler(@RequestParam("query") String query){
		List<User> searchUsers = userService.searchUser(query);
		return new ResponseEntity<List<User>>(searchUsers, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UserRequest updateUser, @RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByToken(jwt);
		 userService.updateUser(user.getId(), updateUser);
		 
		 ApiResponse apiResponse = new ApiResponse("Account Updated Successfully...", true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
	}
	
}
