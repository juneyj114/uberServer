package com.juney.webservice.domain.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.juney.webservice.domain.BaseTimeEntity;
import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.domain.message.Message;
import com.juney.webservice.domain.place.Place;
import com.juney.webservice.domain.ride.Ride;
import com.juney.webservice.web.dto.UserMovementRequestDto;
import com.juney.webservice.web.dto.UserUpdateRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private boolean verifiedEmail = false;
	
	
	private String firstName;
	
	
	private String lastName;
	
	private Long fbId;
	
	private int age;
	
	private String password;
	
	private String phoneNumber;
	
	@Column(nullable = false)
	private boolean verifiedPhoneNumber = false;
	
	private String profilePhoto;
	
	@Column(nullable = false)
	private boolean isDriving = false;
	
	@Column(nullable = false)
	private boolean isRiding = false;
	
	@Column(nullable = false)
	private boolean isTaken = false;
	
	private float lastLng;
	
	private float lastLat;
	
	private float lastOrientation;
	
	private String firebaseToken;
	
	@OneToMany(mappedBy = "user")
	private List<Message> messages;
	
	@OneToOne
	@JoinColumn(name = "chatId")
	private Chat chat;
	
	@OneToMany(mappedBy = "passenger")
	private List<Ride> ridesAsPassenger;
	
	@OneToMany(mappedBy = "driver")
	private List<Ride> ridesAsDriver;
	
	@OneToMany(mappedBy = "user")
	private List<Place> places;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.PASSENGER;
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public User() {}
	
//	@Builder
//	public User(String firstName, String lastName, String email, String password, String profilePhoto, String phoneNumber,int age) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//		this.profilePhoto = profilePhoto;
//		this.phoneNumber = phoneNumber;
//		this.age = age;
//	}

	public void updateProfile(UserUpdateRequestDto dto) {
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.email = dto.getEmail();
//		if(dto.getPassword() != null) {
//			this.password = dto.getPassword();
//		}
		this.profilePhoto = dto.getProfilePhoto();
//		this.age = dto.getAge();
	}

	public void updateMovement(UserMovementRequestDto dto) {
		this.lastLat = dto.getLat();
		this.lastLng = dto.getLng();
		this.lastOrientation = dto.getOrientation();
	}
}
