package com.juney.webservice.web.dto;

import com.juney.webservice.domain.place.Place;

import lombok.Getter;

@Getter
public class PlaceUpdateRequestDto {

	private Long id;
	private String name;
	private boolean isFav;
	
	public Place toEntity() {
		return Place.builder().id(id).name(name).isFav(isFav).build();
	};
	
}
