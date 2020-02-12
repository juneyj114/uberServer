package com.juney.webservice.web.dto;

import com.juney.webservice.domain.ride.Ride;
import com.juney.webservice.domain.ride.Status;
import com.juney.webservice.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RideResponseDto {

	private Status status;

	private String pickUpAddress;

	private float pickUpLat;

	private float pickUpLng;

	private String dropOffAddress;

	private float dropOffLat;

	private float dropOffLng;
	
	private int price;

	private String distance;

	private String duration;
	
	private User passenger;
	
	public RideResponseDto(Ride ride) {
		this.status = ride.getStatus();
		this.pickUpAddress = ride.getPickUpAddress();
		this.pickUpLat = ride.getPickUpLat();
		this.pickUpLng = ride.getPickUpLng();
		this.dropOffAddress = ride.getDropOffAddress();
		this.dropOffLat = ride.getDropOffLat();
		this.dropOffLng = ride.getDropOffLng();
		this.price = ride.getPrice();
		this.distance = ride.getDistance();
		this.duration = ride.getDuration();
		this.passenger = ride.getPassenger();
	}
	
}
