package com.rolebase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolebase.helper.JwtUtil;
import com.rolebase.model.CustomUserDetails;
import com.rolebase.model.JwtResponse;
import com.rolebase.model.User;
import com.rolebase.repositry.UserRepositry;
import com.rolebase.service.CustomUserService;


@RestController
@RequestMapping("/users")

public class UserController {

	@Autowired
	UserRepositry userRepositry;
	
	@Autowired
	JwtUtil jwtUtil;
//	@Autowired
//	CustomUserDetails customUserDetails;
	
	@Autowired
	CustomUserService customUserService;


	@Autowired
	AuthenticationManager authenticationManager;
	
	
	
	@GetMapping("/getAll")
	@PreAuthorize("hasAuthority('ADMIN') ")
	private ResponseEntity<?>  getAllUser(){
		return ResponseEntity.ok(this.userRepositry.findAll());
	}
	
	@GetMapping("/getById/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<User> getUserById(@PathVariable String id){
		User user1 =userRepositry.findById(id).orElseThrow();
		return ResponseEntity.ok(user1);
	}	
	
	@PostMapping("/insert")
	@PreAuthorize("hasAuthority('ADMIN') ")
	public ResponseEntity<User> addUser(@RequestBody User user){
		User save=this.userRepositry.save(user);
		return ResponseEntity.ok(save);
	}
	
	@PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody User user) throws Exception{


          try {

              this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

          }catch (UsernameNotFoundException  e) {
              e.printStackTrace();
              throw new Exception("not found");
          }
            catch(BadCredentialsException e) {
                e.printStackTrace();
                throw new Exception("BadCredentials");
            }

          System.out.println("user"+user);
          //fine area
         UserDetails userDetails= this.customUserService.loadUserByUsername(user.getUsername());

       String token=  this.jwtUtil.generateToken(userDetails);
       System.out.println("jwt " +token);

   

       return ResponseEntity.ok(new JwtResponse(token));

    }
	
	@GetMapping("/home")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ADMIN')")
	public String home() {
		String msg="this is home page";
		return msg;
	}
	

}
