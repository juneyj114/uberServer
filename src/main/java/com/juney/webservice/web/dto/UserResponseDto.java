package com.juney.webservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserResponseDto {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String profilePhoto;
	
	private String fullName;
	
	private boolean isDriving;
	
}
