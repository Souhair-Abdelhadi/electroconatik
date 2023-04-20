package com.electronicshop.jwt;

import java.util.Base64;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DecodeToken {

	@JsonIgnore
	private String token;

	public DecodeToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DecodeToken(String token) {
		super();
		this.token = token;
	}

	
	@JsonIgnore
	public int myCompteId() {
		
		try {
			System.out.println("token start with Bearer "+ token.startsWith("Bearer"));
			System.out.println("token start with Bearer "+ token.substring(7));
			String[] chunks = token.substring(8).split("\\.");
			Base64.Decoder decoder = Base64.getUrlDecoder();
			String header = new String(decoder.decode(chunks[0]));
			String payload = new String(decoder.decode(chunks[1]));
			System.out.println(header);
			System.out.println(payload);
			HashMap<String, Object> mapping = new ObjectMapper().readValue(payload, HashMap.class);
			System.out.println("sub value : "+mapping.get("sub"));
			String sub = (String) mapping.get("sub");
			String[] subsList = sub.split(",");
			return Integer.parseInt(subsList[0]);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return 0;
		}
		
	}
	
	
}
