package com.juney.webservice.web.dto;

import com.juney.webservice.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
	
//	private Long id;
	private String firstName;
	private String lastName;
	private String email;
//	private String password;
	private String profilePhoto;
//	private int age;
	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
//	public void setId(Long id) {
//		this.id = id;
//	}
	
	public User toEntity() {
		return User.builder()
					.firstName(firstName)
					.lastName(lastName)
					.email(email)
//					.password(password)
					.profilePhoto(profilePhoto)
					.build();
	}
	
	
	
}
