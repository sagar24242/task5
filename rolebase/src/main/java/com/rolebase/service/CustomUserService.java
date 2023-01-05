package com.rolebase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rolebase.model.CustomUserDetails;
import com.rolebase.model.User;
import com.rolebase.repositry.UserRepositry;


@Service
public class CustomUserService implements UserDetailsService{
	
	
	
	@Autowired
	UserRepositry userRepositry;
	
	


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepositry.findByUsername(username);
		
		System.out.println("CustomUserService: "+user);
		
		return new CustomUserDetails(user);

	}

}
