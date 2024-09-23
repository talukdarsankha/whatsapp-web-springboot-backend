package com.xyz.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String chatName;
	private String chatImage;
	private boolean isGroup;
	
	@ManyToOne
	private User createdBy;
	
	@ManyToMany
	private Set<User> admins = new HashSet<>();
	
	@ManyToMany
	private Set<User> users = new HashSet<>();
	
	@JsonIgnore
	@OneToMany
	private List<ChatMessage> messages = new ArrayList<>() ;
	

}
