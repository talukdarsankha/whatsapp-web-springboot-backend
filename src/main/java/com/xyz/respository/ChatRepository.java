package com.xyz.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyz.models.Chat;
import com.xyz.models.User;

public interface ChatRepository extends JpaRepository<Chat, Integer>{
	
	@Query("select c from Chat c join c.users u where u.id = :userId")
	public List<Chat> findChatsByUserId(Integer userId);
	
	@Query("select c from Chat c where c.isGroup=false And :reqUser Member of c.users And :user2 Member of c.users")
	public Chat findSingleChatByUsers(User reqUser, User user2);

}
