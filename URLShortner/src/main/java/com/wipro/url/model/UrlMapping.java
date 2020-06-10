package com.wipro.url.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UrlMapping {
	
	@Id
	private String newUrl;
	@Column(length=1000)
	private String oldUrl;
	private Date createdDate;
	private Date expiryDate;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userName", nullable=false)
	private User user;
	public UrlMapping() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UrlMapping(String newUrl, String oldUrl, Date createdDate, Date expiryDate, User user) {
		super();
		this.newUrl = newUrl;
		this.oldUrl = oldUrl;
		this.createdDate = createdDate;
		this.expiryDate = expiryDate;
		this.user = user;
	}

	public String getNewUrl() {
		return newUrl;
	}
	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}
	public String getOldUrl() {
		return oldUrl;
	}
	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
