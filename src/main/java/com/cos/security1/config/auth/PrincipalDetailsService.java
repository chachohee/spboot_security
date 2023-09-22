package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 .loginProcessingUrl("/login"); 해줬기 때문에
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있 loadUserByUsername 함수가 실행됨(규칙임)
// PrinciplaDetailService(@Service)가 IoC되어 있는 거고, 그 안에 loadUserByUsername 함수가 실행되는 거
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	// 시큐리티 세션(내부 Authentication(내부 UserDetails))
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 파라미터로 받는 username은 html에서 form태그로 넘어온 파라미터 중 name이 username인 거
		
		// 유저 정보 찾기
		User userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			return new PrincipalDetails(userEntity); // 이때, PrincipalDetails가 Authentication 안에 들어감.
		}
		return null;
	}

}
