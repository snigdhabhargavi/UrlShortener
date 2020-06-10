package com.wipro.url.service;

import com.wipro.url.model.UrlMapping;
import com.wipro.url.model.User;

public interface UrlMappingService {
	public UrlMapping findByOldUrl(String oldurl);

	void addUrlDetails(String newUrl, String oldUrl, User user);
}
