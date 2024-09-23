package com.xyz.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.exception.ChatException;
import com.xyz.exception.UserException;
import com.xyz.models.Chat;
import com.xyz.models.User;
import com.xyz.request.GroupChatRequest;
import com.xyz.respository.ChatRepository;

import jakarta.persistence.Id;


@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Chat createChat(User reqUser, Integer userid2) throws UserException {
		// TODO Auto-generated method stub
		
		User chatuser2 = userService.findUserById(userid2);
		Chat existChat = chatRepository.findSingleChatByUsers(reqUser, chatuser2);
		
		if (existChat!=null) {
			return existChat;
		} 
		Chat createdChat = new Chat();
		createdChat.setCreatedBy(reqUser);
		createdChat.getUsers().add(reqUser);
		createdChat.getUsers().add(chatuser2);
		createdChat.setGroup(false);
		
		return chatRepository.save(createdChat);
		
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		// TODO Auto-generated method stub
		Chat chat = chatRepository.findById(chatId).get();
		if (chat!=null) {
			return chat;
		}

		throw new ChatException("Chat not found with this id :"+chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) {
		// TODO Auto-generated method stub
		
		return chatRepository.findChatsByUserId(userId);
	}

	@Override
	public Chat createGroup(GroupChatRequest gcr ,  User reqUser) throws UserException {
		// TODO Auto-generated method stub
		Chat groupChat = new Chat();
		
		groupChat.setChatName(gcr.getChatName());
		groupChat.setChatImage(gcr.getChatImage());
		groupChat.setCreatedBy(reqUser);
		groupChat.getAdmins().add(reqUser);
		groupChat.setGroup(true);
		
		for(Integer e: gcr.getUserIds()) {
			User groupuser = userService.findUserById(e);
			groupChat.getUsers().add(groupuser);
		}
		groupChat.getUsers().add(reqUser);
		
		
		return chatRepository.save(groupChat);
	}

	@Override
	public Chat addUserToGroup(Integer adduserid, Integer chatId, User reqUser) throws ChatException, UserException {
		// TODO Auto-generated method stub
		Chat chat = findChatById(chatId);
		User addUser = userService.findUserById(adduserid);
		
		if (chat.getAdmins().contains(reqUser)) {
			chat.getUsers().add(addUser);
			return chatRepository.save(chat);
		}
		throw new ChatException("You are not a admin");
		
	}

	@Override
	public Chat renameGroup(String groupName, Integer chatid, User reqUser) throws ChatException {
		// TODO Auto-generated method stub
		
		Chat chat = findChatById(chatid);
		if(chat.getUsers().contains(reqUser)) {
			chat.setChatName(groupName);
			return chatRepository.save(chat);
			}
		
		throw new ChatException("you are not a member of this group.....");
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userid, User reqUser) throws ChatException, UserException {
		// TODO Auto-generated method stub
		
		Chat chat = findChatById(chatId);
		User user = userService.findUserById(userid);
		
		if(chat.getAdmins().contains(reqUser)) {
			chat.getUsers().remove(user);
			return chatRepository.save(chat);
		}else if (chat.getUsers().contains(reqUser)) {
			if (userid.equals(reqUser.getId())) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			}
			
		}
		
		throw new ChatException("You can't perform removeFromGroup action");
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		// TODO Auto-generated method stub
		Chat chat = findChatById(chatId);
		User user = userService.findUserById(userId);
		
		if (chat.getUsers().contains(user)) {
			 chatRepository.deleteById(chat.getId());
		}
		
		
	}
	

}
