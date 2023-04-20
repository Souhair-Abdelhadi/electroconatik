package com.electronicshop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.electronicshop.entities.Brand;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Component
public class StringToBrandConverter implements Converter<String, Brand> {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@SneakyThrows
	public Brand convert(String source) {

		return objectMapper.readValue(source, Brand.class);
	}

	
	
}
