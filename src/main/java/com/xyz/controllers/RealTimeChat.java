package com.xyz.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.xyz.models.ChatMessage;

public class RealTimeChat {
	
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/message")
	@SendTo("/group/message")
	public ChatMessage receiveMessage(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSend("/group/"+chatMessage.getChat().getId().toString(), chatMessage);
		return chatMessage;
	}

}
