package com.rolebase.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rolebase.model.User;


@Repository
public interface UserRepositry extends MongoRepository<User, String> {



	User findByUsername(String username);

	User getByUsernameAndPassword(String username, String password);









	



	
	
}
