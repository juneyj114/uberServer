package com.juney.webservice.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.domain.place.Place;
import com.juney.webservice.service.PlaceService;
import com.juney.webservice.web.dto.PlaceSaveRequestDto;
import com.juney.webservice.web.dto.PlaceUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PlaceController {

	private final PlaceService placeService;
	
	@PostMapping("/place")
	public ResponseEntity<String> save(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceSaveRequestDto placeSaveRequestDto){
		
		int result = placeService.save(userPrincipal.getId(), placeSaveRequestDto);
		switch (result) {
		case 1:
			return new ResponseEntity<>("저장", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/place")
	public ResponseEntity<String> update(@RequestBody PlaceUpdateRequestDto placeUpdateRequestDto){
		int result = placeService.update(placeUpdateRequestDto);
		switch (result) {
		case 1:
			return new ResponseEntity<>("변경", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/place/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		int result = placeService.delete(id);
		switch (result) {
		case 1:
			return new ResponseEntity<>("삭제", HttpStatus.ACCEPTED);
		case 2:
			return new ResponseEntity<>("해당 장소가 없음", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("place")
	public ResponseEntity<List<Place>> getPlaces(@AuthenticationPrincipal UserPrincipal userPrincipal){
		List<Place> places = placeService.findById(userPrincipal.getId()); 
		return new ResponseEntity<>(places, HttpStatus.ACCEPTED);
	}
}
