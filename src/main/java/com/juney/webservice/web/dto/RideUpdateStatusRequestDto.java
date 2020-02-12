package com.juney.webservice.web.dto;

import com.juney.webservice.domain.ride.Status;
import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class RideUpdateStatusRequestDto {

	private User passenger;
	
	private Status status;
	
}
