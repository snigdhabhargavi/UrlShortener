package com.wipro.url.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wipro.url.model.User;
import com.wipro.url.repo.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository repo;
	
	public void saveUser(User user) {
		repo.save(user);
	}
	public boolean existsById(String username) {
		return repo.existsById(username);
	}
	
	public User findByUserName(String username) {
		return repo.findById(username).get();
	}
}
