package com.juney.webservice.domain.ride;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.juney.webservice.domain.BaseTimeEntity;
import com.juney.webservice.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@Entity
public class Ride extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.REQUESTING;
	
	@Column(nullable = false)
	private String pickUpAddress;
	
	@Column(nullable = false)
	private float pickUpLat = 0;
	
	@Column(nullable = false)
	private float pickUpLng = 0;
	
	@Column(nullable = false)
	private String dropOffAddress;
	
	@Column(nullable = false)
	private float dropOffLat = 0;
	
	@Column(nullable = false)
	private float dropOffLng = 0;
	
	@Column(nullable = false)
	private int price = 0;
	
	@Column(nullable = false)
	private String distance;
	
	@Column(nullable = false)
	private String duration;
	
	@ManyToOne
	@JoinColumn(name = "passengerId", nullable = false)
	private User passenger;
	
	@ManyToOne
	@JoinColumn(name = "driverId")
	private User driver;
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void setDriver(User driver) {
		this.driver = driver;
	}
}
