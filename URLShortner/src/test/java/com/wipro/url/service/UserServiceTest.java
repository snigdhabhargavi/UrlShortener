package com.wipro.url.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.url.model.User;
import com.wipro.url.repo.UserRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceTest {

	@Mock
	UserRepository repo;

	@Test
	public void saveUserTest(){
		User user=new User("smith13","Google");
		repo.save(user);
		verify(repo, times(1)).save(user);
	}
	
	@Test
	public void testFindById() {
		User user1=new User("John123","Github");
		repo.save(user1);
		when(repo.getOne("John123")).thenReturn(user1);
		assertEquals(user1,repo.getOne("John123"));
	}
	
	@Test
	public void testExistsById() {
		//User u=new User("SS","facebook");
		//repo.save(u);
		when(repo.existsById("SS")).thenReturn(true);
		assertEquals(true,repo.existsById("SS"));
	}

}
