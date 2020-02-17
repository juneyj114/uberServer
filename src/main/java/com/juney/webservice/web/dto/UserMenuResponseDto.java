package com.juney.webservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserMenuResponseDto {

	private String profilePhoto;
	
	private String fullName;
	
	private boolean isDriving;
	
}
