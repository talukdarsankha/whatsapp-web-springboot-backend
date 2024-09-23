package com.xyz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.models.ChatMessage;
import com.xyz.models.User;
import com.xyz.request.MessageRequest;
import com.xyz.response.ApiResponse;
import com.xyz.service.MessageService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<ChatMessage> sendMessageHandler(@RequestBody MessageRequest messageRequest, @RequestHeader("Authorization") String jwt ) throws Exception{   
		User reqUser = userService.findUserByToken(jwt);
		
		messageRequest.setUserId(reqUser.getId());
		ChatMessage chatMessage= messageService.sendMessage(messageRequest);
		
		return new ResponseEntity<ChatMessage>(chatMessage,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<ChatMessage>> getAllMessageFromChat(@PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		List<ChatMessage> allmessages= messageService.getChatMessages(chatId, reqUser);
		return new ResponseEntity(allmessages,HttpStatus.OK);
	}
	
	@GetMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable("messageId") Integer messageId, @RequestHeader("Authorization") String jwt) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		messageService.deleteMessage(messageId, reqUser);
		return new ResponseEntity(new ApiResponse("Message deleted successfully...", true),HttpStatus.OK);
	}
	
	

}
