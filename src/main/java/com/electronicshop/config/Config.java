package com.electronicshop.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.electronicshop.jwt.JwtTokenFilter;
import com.electronicshop.repository.UserRepo;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false,securedEnabled = false,jsr250Enabled = true)
public class Config extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtTokenFilter jwtTokenfilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> userRepo.findByEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException("user with email "+username+" not found"))
				);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
					
		http.cors().and().csrf().disable();
		
		http.httpBasic().disable();
		
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers("/*").permitAll();
		
//		http.csrf().disable()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()
//		.authorizeRequests()
//		.antMatchers("/auth/login").permitAll()
//		.anyRequest().authenticated();
//		
//		http.exceptionHandling().authenticationEntryPoint((request,response,ex)->{
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,ex.getMessage());
//			System.out.println(ex.getMessage());
//		});
//		
//		http.addFilterBefore(jwtTokenfilter, UsernamePasswordAuthenticationFilter.class); 
		
		
	}
	
	
}
