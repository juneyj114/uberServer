package com.juney.webservice.web.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PlaceResponseDto {

	private Long id;
	private String name;
	private String address;
	private boolean isFav;
	
	private float lat;
	private float lng;
	
}
