package com.juney.webservice.web.dto;

import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class UserMovementRequestDto {

	private float lng;
	private float lat;
	private float orientation;
	
	public User toEntity() {
		return User.builder()
					.lastLng(lng)
					.lastLat(lat)
					.lastOrientation(orientation)
					.build();
	}
}
