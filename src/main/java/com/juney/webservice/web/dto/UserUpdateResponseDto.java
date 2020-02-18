package com.juney.webservice.web.dto;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserUpdateResponseDto {

	private String firstName;
	
	private String lastName;
	
	private String email;
}
