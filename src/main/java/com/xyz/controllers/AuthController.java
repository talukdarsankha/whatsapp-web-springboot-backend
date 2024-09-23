package com.xyz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.config.TokenProvider;
import com.xyz.exception.UserException;
import com.xyz.models.User;
import com.xyz.request.LoginRequest;
import com.xyz.response.AuthResponse;
import com.xyz.respository.UserRepository;
import com.xyz.service.CustomUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		String email = user.getEmail();
		String password = user.getPassword();
		String fullname = user.getFull_name();
		
		User findUser = userRepository.findByEmail(email);
		if (findUser!=null){
			throw new UserException("Email is alredy used in another account....");
		}
		
		User createUser = new User();
		createUser.setEmail(email);
		createUser.setPassword(passwordEncoder.encode(password));
		createUser.setFull_name(fullname);
		
		userRepository.save(createUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = tokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token, true);
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
			
	 }
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(email, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(jwt, true);
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.ACCEPTED);
		
	}

    public Authentication authenticate(String email,String password) {
    	UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
    	
    	if (userDetails==null) {
			throw new BadCredentialsException("invalid email");
		}
    	if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password !!!");
		}
    	
    	return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
    	
      }
    }
	

