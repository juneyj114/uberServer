package com.juney.webservice.web.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.juney.webservice.domain.place.Place;
import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class PlaceSaveRequestDto {

	private String name;
	private float lat;
	private float lng;
	private String address;
	private boolean isFav;
	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Place toEntity() {
		return Place.builder()
					.name(name)
					.lat(lat)
					.lng(lng)
					.address(address)
					.isFav(isFav)
					.user(user)
					.build();
	}
}
