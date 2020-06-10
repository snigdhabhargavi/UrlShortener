package com.wipro.url.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.url.model.User;

public interface UserRepository extends JpaRepository<User, String>{

}
