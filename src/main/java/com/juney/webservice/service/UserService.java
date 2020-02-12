package com.juney.webservice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.juney.webservice.domain.user.Role;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.domain.verification.Target;
import com.juney.webservice.domain.verification.Verification;
import com.juney.webservice.domain.verification.VerificationRepository;
import com.juney.webservice.firebase.MyFirebase;
import com.juney.webservice.util.SendEmail;
import com.juney.webservice.web.dto.RideRequestMessageDto;
import com.juney.webservice.web.dto.UserMovementRequestDto;
import com.juney.webservice.web.dto.UserSaveRequestDto;
import com.juney.webservice.web.dto.UserUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	private final HttpSession httpSession;
	

	@Transactional
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Transactional
	public int save(UserSaveRequestDto userSaveRequestDto) {
		try {
			User existingUser = userRepository.findByEmail(userSaveRequestDto.getEmail()).get();
			if (existingUser != null) {
				return 0;
			} else {
				String encodedPassword = encoder.encode(userSaveRequestDto.getPassword());
				userSaveRequestDto.setPassword(encodedPassword);
				User user = userRepository.save(userSaveRequestDto.toEntity());
				httpSession.setAttribute("id", user.getId());
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Transactional
	public int updateMyProfile(Long id, UserUpdateRequestDto dto) {
		try {
			User user = userRepository.findById(id).get();
			if(dto.getPassword() != null) {
				String encodedPassword = encoder.encode(dto.getPassword());
				dto.setPassword(encodedPassword);
			}
			user.updateProfile(dto);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Transactional
	public int toggleDriveMode(Long id) {
		try {
			User user = userRepository.findById(id).get();
			if(user.isDriving()) {
				user.setDriving(false);
			} else {
				user.setDriving(true);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Transactional
	public int reportMovement(Long id, UserMovementRequestDto dto) {
		try {
			User user = userRepository.findById(id).get();
			user.updateMovement(dto);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Transactional
	public int saveFirebaseToken(Long id, String token) {
		try {
			User user = userRepository.findById(id).get();
			user.setFirebaseToken(token);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	@Transactional
	public List<User> getNearbyDrivers(Long id) {
		try {
			User user = userRepository.findById(id).get();
			List<User> drivers = userRepository.getNearbyDrivers(user.getLastLat(), user.getLastLng());
			return drivers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
