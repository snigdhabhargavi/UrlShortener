package com.wipro.url.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.url.model.UrlMapping;
import com.wipro.url.model.User;
import com.wipro.url.repo.UrlRepository;

import org.junit.runner.RunWith;


import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UrlMappingServiceImplTest {

	@InjectMocks
	UrlMappingServiceImpl userService;
	
	@Mock
	UrlRepository urlRepository;
	
	String newurl = "https://shorturl/4c76ddfe";
	String oldurl = "https://kafka.apache.org/uses";
	Date date = new Date();
	Calendar calendar = Calendar.getInstance();
	
	@Test
	void testFindByOldUrl() {
		User user = new User("Sara", "Google");
		calendar.add(Calendar.MONTH, 6);
		UrlMapping urlMap = new UrlMapping(newurl, oldurl, date, calendar.getTime(), user);
		urlRepository.save(urlMap);
		when(urlRepository.findByOldUrl(oldurl)).thenReturn(urlMap);
		assertEquals(urlMap, urlRepository.findByOldUrl(oldurl));
	}

	@Test
	void testAddUrlDetails() {
		User user = new User("Sara", "Google");
		calendar.add(Calendar.MONTH, 6);
		UrlMapping urlMap = new UrlMapping(newurl, oldurl, date, calendar.getTime(), user);
		urlRepository.save(urlMap);
		verify(urlRepository, times(1)).save(urlMap);		
	}
}
