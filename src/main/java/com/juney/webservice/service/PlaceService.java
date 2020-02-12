package com.juney.webservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juney.webservice.domain.place.Place;
import com.juney.webservice.domain.place.PlaceRepository;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.web.dto.PlaceSaveRequestDto;
import com.juney.webservice.web.dto.PlaceUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private PlaceRepository placeRepository;
	private UserRepository userRepository;
	
	@Transactional
	public int save(Long id, PlaceSaveRequestDto dto) {
		try {
			User user = userRepository.findById(id).get();
			dto.setUser(user);
			placeRepository.save(dto.toEntity());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Transactional
	public int update(PlaceUpdateRequestDto dto) {
		try {
			Place place = placeRepository.findById(dto.getId()).get();
			place.update(dto);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Transactional
	public int delete(Long id) {
		try {
			Place place = placeRepository.findById(id).get();
			if(place == null) {
				return 2;
			}
			placeRepository.delete(place);
			return 1;	
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}

	public List<Place> findById(Long id) {
		try {
			User user = userRepository.findById(id).get();
			return user.getPlaces();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
