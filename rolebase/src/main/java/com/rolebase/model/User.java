package com.rolebase.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
  @Id
  private String id;


  private String username;

  
  private String role;


  private String password;
  
  



  public User() {
  }





public String getId() {
	return id;
}





public void setId(String id) {
	this.id = id;
}





public String getUsername() {
	return username;
}





public void setUsername(String username) {
	this.username = username;
}





public String getRole() {
	return role;
}





public void setRole(String role) {
	this.role = role;
}





public String getPassword() {
	return password;
}





public User(String id, String username, String role, String password) {
	super();
	this.id = id;
	this.username = username;
	this.role = role;
	this.password = password;
}





public void setPassword(String password) {
	this.password = password;
}



 
 
}