package com.rolebase.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rolebase.helper.JwtUtil;
import com.rolebase.service.CustomUserService;

@Component
public class JwtAuthenticationFilter extends  OncePerRequestFilter {

	

    
    @Autowired
    private CustomUserService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


 @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {


      //get jwt
      //Bearer
      //validate

      String requsetTokenHeader=request.getHeader("Authorization");
      String username=null;
      String jwtToken=null;

      //null and format
      if(requsetTokenHeader!=null  && requsetTokenHeader.startsWith("Bearer ")) {

          jwtToken=requsetTokenHeader.substring(7);

          try {

            username= this.jwtUtil.getUsernameFromToken(jwtToken);

          }catch (Exception e) {

              e.printStackTrace();
        }

           UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(username);
          //security

          if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

              UsernamePasswordAuthenticationToken  usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

             usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 

             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

         }else
         {
             System.out.println("Token is not validated...");
         }



      }

      filterChain.doFilter(request, response);

}

 

    

 



}
