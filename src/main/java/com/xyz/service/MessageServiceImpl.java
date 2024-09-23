package com.xyz.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.exception.ChatException;
import com.xyz.exception.MessageException;
import com.xyz.exception.UserException;
import com.xyz.models.Chat;
import com.xyz.models.ChatMessage;
import com.xyz.models.User;
import com.xyz.request.MessageRequest;
import com.xyz.respository.ChatRepository;
import com.xyz.respository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private ChatService chatService;
	
	@Autowired
    private MessageRepository messageRepository;
	
	@Autowired
    private ChatRepository chatRepository;
	

	@Override
	public ChatMessage sendMessage(MessageRequest messageRequest) throws UserException, ChatException {
		// TODO Auto-generated method stub
		
		User createdUser = userService.findUserById(messageRequest.getUserId());
		Chat chat = chatService.findChatById(messageRequest.getChatId());
		
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUser(createdUser);
		chatMessage.setChat(chat);
		chatMessage.setContent(messageRequest.getContent());
		chatMessage.setTimeStamp(LocalDateTime.now());
		
		chatMessage = messageRepository.save(chatMessage);
		
		chat.getMessages().add(chatMessage);
		chatRepository.save(chat);
		
		return chatMessage;
	}

	@Override
	public List<ChatMessage> getChatMessages(Integer chatId, User reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub
		
		Chat chat = chatService.findChatById(chatId);
		if (chat.getUsers().contains(reqUser)) {
			return messageRepository.findMessageByChatId(chatId);
		}
		
		throw new UserException("you are not a related to this chat..");
	}

	@Override
	public ChatMessage findMessageById(Integer messageId) throws MessageException {
		// TODO Auto-generated method stub
		
		 Optional<ChatMessage> opt= messageRepository.findById(messageId);
		 if (opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message not found with this id :"+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
		// TODO Auto-generated method stub
		
		ChatMessage chatMessage = findMessageById(messageId);
		if (chatMessage.getUser().equals(reqUser)) {
			messageRepository.delete(chatMessage);
		}
		throw new UserException("you don't have to access delete another user message...");
		
	}

}
