package com.xyz.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.models.ChatMessage;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Integer> {

	@Query("select m from ChatMessage m join m.chat c where c.id = :chatid")
	public List<ChatMessage> findMessageByChatId(Integer chatid);
	
}
