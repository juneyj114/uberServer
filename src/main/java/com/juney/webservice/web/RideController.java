package com.juney.webservice.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.domain.ride.Ride;
import com.juney.webservice.domain.ride.Status;
import com.juney.webservice.domain.ride.rideRepository;
import com.juney.webservice.service.ChatService;
import com.juney.webservice.service.RideService;
import com.juney.webservice.web.dto.RideSaveRequestDto;
import com.juney.webservice.web.dto.RideResponseDto;
import com.juney.webservice.web.dto.RideUpdateStatusRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RideController {

	private final RideService rideService;
	private final ChatService chatService;
	
	@PostMapping("/ride")
	public ResponseEntity<RideResponseDto> save(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody RideSaveRequestDto rideSaveRequestDto) {
		RideResponseDto rideSaveResponseDto = rideService.save(userPrincipal.getId(), rideSaveRequestDto);
		if(rideSaveResponseDto != null) {
			rideService.sendMessageNearbyDrivers(rideSaveResponseDto);
			return new ResponseEntity<>(rideSaveResponseDto ,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/nearbyRides")
	public ResponseEntity<List<Ride>> getNearbyRides() {
		List<Ride> rides = rideService.getNearbyRides();
		if(rides != null) {
			return new ResponseEntity<>(rides ,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/rideStatus")
	public ResponseEntity<String> updateStatus(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody RideUpdateStatusRequestDto rideUpdateStatusRequestDto) {
		int result = rideService.updateStatus(userPrincipal.getId(), rideUpdateStatusRequestDto);
		switch (result) {
		case 1:
			if(rideUpdateStatusRequestDto.getStatus().equals(Status.ACCEPTED)) {
				chatService.save(userPrincipal.getId(), rideUpdateStatusRequestDto.getPassenger().getId());
			}
			return new ResponseEntity<>("변경완료", HttpStatus.ACCEPTED);
		case 2:
			return new ResponseEntity<>("승객이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		case 3:
			return new ResponseEntity<>("승객이 메시지를 받지 못했습니다.", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/ride/{rideId}")
	public ResponseEntity<RideResponseDto> get(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long rideId){
		RideResponseDto ride = rideService.findById(userPrincipal.getId(), rideId);
		if(ride != null) {
			return new ResponseEntity<>(ride, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	}
}
