package com.juney.webservice.config.auth.dto;

import java.util.Map;

import com.juney.webservice.domain.user.Role;
import com.juney.webservice.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String firstName;
	private String lastName;
	private String email;
	private Long fbId;
	private String picture;
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		System.out.println(registrationId);
		return ofFacebook(userNameAttributeName, attributes);
	}
	
	public static OAuthAttributes ofFacebook(String userNameAttributeName, Map<String, Object> attributes) {
		OAuthAttributes oAuthAttributes = new OAuthAttributes();
		oAuthAttributes.setFirstName(attributes.get("name").toString().substring(1));
		oAuthAttributes.setLastName(attributes.get("name").toString().substring(0,1));
		oAuthAttributes.setEmail((String)attributes.get("email"));
		oAuthAttributes.setFbId(Long.parseLong((String)attributes.get("id")));
		oAuthAttributes.setPicture("http://graph.facebook.com/"+String.valueOf(Long.parseLong((String)attributes.get("id")))+"/picture?type=square");
		oAuthAttributes.setAttributes(attributes);
		oAuthAttributes.setNameAttributeKey(userNameAttributeName);
		return oAuthAttributes;
	}
	
	public User toEntity() {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setFbId(fbId);
		user.setProfilePhoto(picture);
		user.setRole(Role.PASSENGER);
		return user;
	}
}
