package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.CustomBCryptPasswordEncoder;
import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomBCryptPasswordEncoder bCryptPasswordEncoder;
	
	// loadUser() -> 구글 로그인 후 후처리하는 메서드
	// 무엇을 후처리? -> 구글로부터 받은 userRequest 데이터에 대한 후처리
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println("getAccessToken: " + userRequest.getAccessToken());
		System.out.println("getTokenValue: " + userRequest.getAccessToken().getTokenValue());
		System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); 
		// userRequest.getClientRegistration()
		// 우리 서버의 기본 정보들
		// registrationId로 어떤 OAuth로 로그인했는지 알 수 있음. registrationId='google'
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println(super.loadUser(userRequest).getAttributes());
		// userRequest 정보:
		// 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code 리턴(OAuth-client 라이브러리) -> Access Token 요청
		// userRequest 정보를 통해서 loadUser 함수 호출 -> 구글로부터 회원프로필 받아준다.
		
		// 회원가입 강제로 진행해보기
		// 유저 정보 만들기
		String provider = userRequest.getClientRegistration().getRegistrationId(); // google
		String providerId = oauth2User.getAttribute("sub");
		String username = provider + "_" + providerId;
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			
			System.out.println("구글 로그인이 최초입니다.");
			
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			
			userRepository.save(userEntity);
		} else {
			System.out.println("이미 구글 로그인한 회원입니다.");
		}
		
		return new PrincipalDetails(userEntity, oauth2User.getAttributes()); // authentication 객체 안에 저장됨. -> 세션에 저장
	}
}
