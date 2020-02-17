package com.juney.webservice.web;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.service.UserService;
import com.juney.webservice.service.VerificationService;
import com.juney.webservice.web.dto.FirebaseTokenRequestDto;
import com.juney.webservice.web.dto.UserMenuResponseDto;
import com.juney.webservice.web.dto.UserMovementRequestDto;
import com.juney.webservice.web.dto.UserSaveRequestDto;
import com.juney.webservice.web.dto.UserUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;
	private final VerificationService verificationService;
	
	@GetMapping("/user")
	public ResponseEntity<UserMenuResponseDto> getUserMenu(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		UserMenuResponseDto dto = userService.findUserMenu(userPrincipal.getId());
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserMenuResponseDto>(dto, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/user")
	public ResponseEntity<String> EmailSignUp(@RequestBody UserSaveRequestDto userSaveRequestDto) throws IOException {
		int saveResult = userService.save(userSaveRequestDto);
		switch (saveResult) {
		case 0:
			return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);			
		case 1:
			int emailVerificationResult = verificationService.startEmailVerification(userSaveRequestDto);
			
			switch (emailVerificationResult) {
			case 1:
				return new ResponseEntity<>("가입완료. 인증용 이메일이 전송되었습니다.", HttpStatus.ACCEPTED); 
			case 2:
				return new ResponseEntity<>("폰이 인증되지 않았습니다.", HttpStatus.BAD_REQUEST); 
			}
			return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
			
		case 2:
			return new ResponseEntity<>("폰이 인증되지 않았습니다.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/user")
	public ResponseEntity<String> updateMyProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
													@RequestBody UserUpdateRequestDto userUpdateRequestDto){
		int result = userService.updateMyProfile(userPrincipal.getId(), userUpdateRequestDto);
		switch (result) {
		case 1:
			return new ResponseEntity<>("업데이트 완료", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@PutMapping("/driveMode")
	public ResponseEntity<String> toggleDriveMode(@AuthenticationPrincipal UserPrincipal userPrincipal){
		int result = userService.toggleDriveMode(userPrincipal.getId());
		switch (result) {
		case 1:
			return new ResponseEntity<>("변경", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/movement")
	public ResponseEntity<String> reportMovement(@AuthenticationPrincipal UserPrincipal userPrincipal, 
													@RequestBody UserMovementRequestDto userMovementRequestDto){
		int result = userService.reportMovement(userPrincipal.getId(), userMovementRequestDto);
		switch (result) {
		case 1:
			return new ResponseEntity<>("변경", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/nearbyDrivers")
	public ResponseEntity<List<User>> getNearbyDrivers(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		List<User> drivers = userService.getNearbyDrivers(userPrincipal.getId());
		if(drivers == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(drivers, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/firebaseToken")
	public ResponseEntity<String> saveFirebaseToken(@AuthenticationPrincipal UserPrincipal userPrincipal,
														@RequestBody FirebaseTokenRequestDto firebaseTokenRequestDto) throws FirebaseMessagingException, IOException {
		int result = userService.saveFirebaseToken(userPrincipal.getId(), firebaseTokenRequestDto.getToken());
		switch (result) {
		case 1:
			return new ResponseEntity<>("토큰저장", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	// 근처 운전자 조회 누르면 주변의 운전자들을 구독하게 됨
	// 해당 운전자들의 위치가 바뀌면 메시지 발송
}
