package com.juney.webservice.web.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.juney.webservice.domain.ride.Ride;
import com.juney.webservice.domain.ride.Status;
import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class RideSaveRequestDto {

	private Status status;

	private String pickUpAddress;

	private float pickUpLat;

	private float pickUpLng ;

	private String dropOffAddress;

	private float dropOffLat;

	private float dropOffLng;
	
	private int price;

	private String distance;

	private String duration;
	
	private User passenger;
	
	public void setPassenger(User passenger) {
		this.passenger = passenger;
	}
	
	public Ride toEntity() {
		return Ride.builder()
				.pickUpAddress(pickUpAddress)
				.pickUpLat(pickUpLat)
				.pickUpLng(pickUpLng)
				.dropOffAddress(dropOffAddress)
				.dropOffLat(dropOffLat)
				.dropOffLng(dropOffLng)
				.price(price)
				.distance(distance)
				.duration(duration)
				.passenger(passenger)
				.build();
	}
}


