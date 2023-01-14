package com.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		Get token
		String requestToken = request.getHeader("Authorization");
		
		System.out.println(requestToken);
		
		String username = null;
		
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
		
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			}
			
			catch(IllegalArgumentException ex) {
				System.out.println("Unable to get JWT Token");
			}
			
			catch(ExpiredJwtException ex) {
				System.out.println("JWT token has expired");
			}
			
			catch(MalformedJwtException ex) {
				System.out.println("Invalid JWT");
			}
		}
		
		else {
			System.out.println("Jwt token does not begin with Bearer");
		}
		
//		Once we get the token, Now, we validate it
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.ValidateToken(token,userDetails)) {
//				 Authentication krna h
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
			else {
				System.out.println("Invalid jwt token");
			}
		}
		
		else {
			System.out.println("Username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}
}
