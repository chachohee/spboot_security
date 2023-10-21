package com.cos.security1.config.oauth.provider;

// API의 getAttribute 값이 다르기 때문에 NULL로 저장되는 경우가 생겨서 인터페이스 생성
public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();

}
