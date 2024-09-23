package com.xyz.service;

import java.util.List;

import com.xyz.exception.ChatException;
import com.xyz.exception.UserException;
import com.xyz.models.Chat;
import com.xyz.models.User;
import com.xyz.request.GroupChatRequest;

public interface ChatService {
	
	public Chat createChat(User reqUser,Integer userid2) throws UserException;
	
	public Chat findChatById(Integer chatId) throws ChatException;
	
	public List<Chat> findAllChatByUserId(Integer userId);
	
	public Chat createGroup(GroupChatRequest gcr , User reqUser) throws UserException;
	
	public Chat addUserToGroup(Integer adduserid, Integer chatId,User reqUser) throws ChatException, UserException ;
	
	public Chat renameGroup(String groupName, Integer chatid ,  User reqUser) throws ChatException;
	
	public Chat removeFromGroup(Integer chatId, Integer userid, User reqUser) throws ChatException, UserException;
	
	public void deleteChat(Integer chatId, Integer userId)throws ChatException, UserException;

}
