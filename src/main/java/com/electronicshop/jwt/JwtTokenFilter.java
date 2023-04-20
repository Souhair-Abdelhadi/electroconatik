package com.electronicshop.jwt;

import java.io.IOException;
import java.rmi.ServerException;

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
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.electronicshop.entities.Role;
import com.electronicshop.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException{
		
		if(!hasAuthorizationHeader(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		String accessToken = getAccessToken(request);
		System.out.println("access token : "+accessToken);
		
		if(!jwtTokenUtil.validateAccessToken(accessToken)) {
			filterChain.doFilter(request, response);
			System.out.println("stopped at validate acces token");
			return;
		}
		
		setAuthenticationToken(accessToken,request);
		filterChain.doFilter(request, response);
	}
	
	
	public void setAuthenticationToken(String accessToken, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(accessToken);
		System.out.println("role : "+userDetails.getAuthorities());
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public UserDetails getUserDetails(String accessToken) {
		User user = new User();
		Claims claims = jwtTokenUtil.parseClaims(accessToken);
		String subject = (String) claims.get(Claims.SUBJECT);
	    String roles = (String) claims.get("roles");
	     System.out.println("user details : "+ roles);
	    roles = roles.replace("[", "").replace("]", "");
	    String[] roleNames = roles.split(",");
	     
	    for (String roleName : roleNames) {
	        user.addRole(new Role(roleName));;
	    }
		
		//String[] subjectArray = jwtTokenUtil.getSubject(accessToken).split(",");
	    System.out.println("sub : "+subject);
	    String[] jwtSybject = subject.split(",");
		user.setId(Long.parseLong(jwtSybject[0]));
		return user;
	}

	public boolean hasAuthorizationHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}

	public String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1];
		return token.trim();
	}
	
}
