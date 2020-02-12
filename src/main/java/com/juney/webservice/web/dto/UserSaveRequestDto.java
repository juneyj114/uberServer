package com.juney.webservice.web.dto;

import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class UserSaveRequestDto {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String profilePhoto;
	private String phoneNumber;
	private int age;
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User toEntity() {
		return User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.profilePhoto(profilePhoto)
				.phoneNumber(phoneNumber)
				.age(age)
				.build();
	}
	
}
