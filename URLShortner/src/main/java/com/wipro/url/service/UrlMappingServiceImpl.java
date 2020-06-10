package com.wipro.url.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.url.model.UrlMapping;
import com.wipro.url.model.User;
import com.wipro.url.repo.UrlRepository;


@Service
public class UrlMappingServiceImpl implements UrlMappingService{

	@Autowired
	UrlRepository urlRepository;
	
	@Override
	public UrlMapping findByOldUrl(String oldurl) {
		return urlRepository.findByOldUrl(oldurl);
	}

	@Override
	public void addUrlDetails(String newUrl, String oldUrl, User user) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 6);
		UrlMapping urlMap = new UrlMapping();
		urlMap.setNewUrl(newUrl);
		urlMap.setOldUrl(oldUrl);
		urlMap.setCreatedDate(date);
		urlMap.setExpiryDate(calendar.getTime());
		urlMap.setUser(user);
		urlRepository.save(urlMap);
	}

}
