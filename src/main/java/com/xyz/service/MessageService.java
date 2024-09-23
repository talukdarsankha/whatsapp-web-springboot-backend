package com.xyz.service;

import java.util.List;

import com.xyz.exception.ChatException;
import com.xyz.exception.MessageException;
import com.xyz.exception.UserException;
import com.xyz.models.ChatMessage;
import com.xyz.models.User;
import com.xyz.request.MessageRequest;

public interface MessageService {
	
	public ChatMessage sendMessage(MessageRequest messageRequest) throws UserException ,ChatException;
	
	public List<ChatMessage> getChatMessages(Integer chatId, User reqUser) throws UserException, ChatException;
	
	public ChatMessage findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;

}
