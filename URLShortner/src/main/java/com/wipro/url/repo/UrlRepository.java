package com.wipro.url.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.url.model.UrlMapping;


@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, String>{
	
	@Query("from UrlMapping u where u.oldUrl=?1")
	public UrlMapping findByOldUrl(String oldurl);
}
