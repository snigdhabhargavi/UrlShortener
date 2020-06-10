package com.wipro.url.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {

    @Id
    private String userName;
    private String clientName;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="user")
  	private Set<UrlMapping> urls = new HashSet<UrlMapping>(0);
    
	public Set<UrlMapping> getUrls() {
		return urls;
	}
	public void setUrls(Set<UrlMapping> urls) {
		this.urls = urls;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", clientName=" + clientName + "]";
	}
	/**
	 * @param userName
	 * @param clientName
	 */
	public User(String userName, String clientName) {
		super();
		this.userName = userName;
		this.clientName = clientName;
	}
	/**
	 * 
	 */
	public User() {
		super();
	}

      
}
