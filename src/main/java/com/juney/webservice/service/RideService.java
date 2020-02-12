package com.juney.webservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.juney.webservice.domain.ride.Ride;
import com.juney.webservice.domain.ride.Status;
import com.juney.webservice.domain.ride.rideRepository;
import com.juney.webservice.domain.user.Role;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.firebase.MyFirebase;
import com.juney.webservice.web.dto.RideRequestMessageDto;
import com.juney.webservice.web.dto.RideSaveRequestDto;
import com.juney.webservice.web.dto.RideResponseDto;
import com.juney.webservice.web.dto.RideUpdateStatusRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RideService {

	private final UserRepository userRepository;
	private final rideRepository rideRepository;
	private final MyFirebase myFirebase;

	@Transactional
	public RideResponseDto save(Long id, RideSaveRequestDto rideSaveRequestDto) {
		try {
			User user = userRepository.findById(id).get();
			if(user.isRiding() || user.isDriving()) {
				return null;
						
			}
			rideSaveRequestDto.setPassenger(user);
			Ride ride = rideRepository.save(rideSaveRequestDto.toEntity());
			user.setRiding(true);
			return new RideResponseDto(ride);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<Ride> getNearbyRides() {
		try {
			List<Ride> rides = rideRepository.findNearbyRides();
			return rides;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean sendMessageNearbyDrivers(RideResponseDto dto) {
		try {
			List<User> drivers = userRepository.findByRole(Role.DRIVER);
			List<String> registrationTokens = drivers.stream().map(User::getFirebaseToken).collect(Collectors.toList());
			myFirebase.sendRideRequestMessage(registrationTokens, dto);
			return true;
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public int updateStatus(Long driverId, RideUpdateStatusRequestDto dto) {
		try {
			Ride ride = null;
			if(dto.getStatus().equals(Status.ACCEPTED)) {
				ride = rideRepository.findByPassengerIdAndStatus(dto.getPassenger().getId(), Status.REQUESTING);
				User driver = userRepository.findById(driverId).get();
				ride.setDriver(driver);
				driver.setTaken(true);
			} else {
				ride = rideRepository.findByPassengerIdAndDriverId(dto.getPassenger().getId(), driverId);
			}
			if(ride != null) {
				boolean res = sendStatusUpdateMessage(dto.getPassenger().getId(), driverId, dto.getStatus());
				if(res) {
					ride.setStatus(dto.getStatus());
					return 1;
				} else {
					return 3;
				}
			} else {
				return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	};
	
	@Transactional
	public boolean sendStatusUpdateMessage(Long passengerId, Long driverId, Status status) {
		try {
			User passenger = userRepository.findById(passengerId).get();
			myFirebase.sendStatusUpdateMessage(passenger.getFirebaseToken(), status);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	};
	
	@Transactional
	public RideResponseDto findById(Long userId, Long rideId) {
		try {
			Ride ride = rideRepository.findById(rideId).get();
			if(ride.getDriver().getId() != userId || ride.getPassenger().getId() != userId) {
				return null;
			}
			return new RideResponseDto(ride);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
