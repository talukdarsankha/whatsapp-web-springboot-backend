package com.xyz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.exception.ChatException;
import com.xyz.models.Chat;
import com.xyz.models.User;
import com.xyz.request.GroupChatRequest;
import com.xyz.response.ApiResponse;
import com.xyz.service.ChatService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create/{userId}")
	public ResponseEntity<Chat> createChatHandler(@PathVariable("userId") Integer userId ,@RequestHeader("Authorization") String jwt) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		Chat chat = chatService.createChat(reqUser, userId);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.CREATED);
	}
	
	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest cgr, @RequestHeader("Authorization") String jwt) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		Chat groupChat = chatService.createGroup(cgr, reqUser);
		
		return new ResponseEntity<Chat>(groupChat,HttpStatus.CREATED);
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt) throws ChatException {
		Chat chat = chatService.findChatById(chatId);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findChatByUserIdHandler( @RequestHeader("Authorization") String jwt) throws Exception {
		List<Chat> chats = chatService.findAllChatByUserId(userService.findUserByToken(jwt).getId());
		return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable("chatId") Integer chatid, @PathVariable("userId") Integer userId) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		 Chat chat = chatService.addUserToGroup(userId, chatid, reqUser);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserToGroupHandler(@RequestHeader("Authorization") String jwt, @PathVariable("chatId") Integer chatid, @PathVariable("userId") Integer userId) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		 Chat chat = chatService.removeFromGroup(chatid, userId, reqUser);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@RequestHeader("Authorization") String jwt,@PathVariable("chatId") Integer chatid) throws Exception{
		
	  User reqUser = userService.findUserByToken(jwt);
		chatService.deleteChat(chatid, reqUser.getId());
		return new ResponseEntity<ApiResponse>(new ApiResponse("Chat deleted..", true),HttpStatus.OK);
	}
	
	@PutMapping("/rename/{chatName}/{chatId}")
	public ResponseEntity<Chat> renameChatHandler(@PathVariable("chatName") String chatName, @PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt) throws Exception{
		User reqUser = userService.findUserByToken(jwt);
		Chat chat= chatService.renameGroup(chatName, chatId, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.ACCEPTED);
	}
	
}
