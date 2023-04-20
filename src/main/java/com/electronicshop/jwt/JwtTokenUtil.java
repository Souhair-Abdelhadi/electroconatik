package com.electronicshop.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.electronicshop.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	@Value("${app.jwt.expiration_date}")
	private long EXPIRE_DURATION;
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	
	public String generateAccessToken(User user) {
		return Jwts.builder()
				.setSubject(user.getId().toString())
				.claim("roles", user.getRoles().toString())
				.setIssuer("electronic shop")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
				
	}
	
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			System.out.println("access token verified");
			return true;
		} catch (ExpiredJwtException ex) {
			logger.error("JWT expired",ex);
		}
		catch (IllegalArgumentException ex) {
			logger.error("Token is null, empty or has only whitespaces",ex);
		}
		catch (MalformedJwtException ex) {
			logger.error("JWT is invalid",ex);
		}
		catch (UnsupportedJwtException ex) {
			logger.error("JWT is not supported",ex);
		}
		catch (SignatureException ex) {
			logger.error("Signature validation failed",ex);
		}
		return false;
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getSubject(String token) {
		System.out.println("token :" +token);
		return parseClaims(token).getSubject();
	}
	
	
	public Long getUserId(String token) {
		
		try {
			String[] chunks = token.substring(8).split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();
			String header = new String(decoder.decode(chunks[0]));
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String, Object> mapping = new ObjectMapper().readValue(payload, HashMap.class);
			System.out.println("sub value : "+mapping.get("sub"));
			String sub = (String) mapping.get("sub");
			String[] subsList = sub.split(",");
			return Long.parseLong(subsList[0]);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		
	}
		
	
	
	
}
