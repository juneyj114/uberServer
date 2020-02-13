package com.juney.webservice.config.auth;

import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.juney.webservice.config.auth.dto.OAuthAttributes;
import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String registratinId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		OAuthAttributes attributes = OAuthAttributes.of(registratinId, userNameAttributeName, oAuth2User.getAttributes());
		User user = saveOrUpdate(attributes);
		httpSession.setAttribute("id", user.getId());
		return UserPrincipal.create(user);
	}

	private User saveOrUpdate(OAuthAttributes attributes) {	
		User user = userRepository.findByFbId(attributes.getFbId()).orElse(attributes.toEntity());
		return userRepository.save(user);
	}
	
	
	
}
