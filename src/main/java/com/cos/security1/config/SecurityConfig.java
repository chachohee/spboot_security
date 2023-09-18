package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable();
	        http.authorizeHttpRequests()
	                .requestMatchers("/user/**").authenticated()
	                .requestMatchers("/manager/**").hasAnyRole("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')")
	                .requestMatchers("/admin/**").hasAnyRole("hasRole('ROLE_ADMIN')")
	                .anyRequest().permitAll()
	                .and()
	                .formLogin()
	                .loginPage("/loginForm");

	        return http.build();
	    }
}
