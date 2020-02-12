package com.juney.webservice.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.service.UserService;
import com.juney.webservice.service.VerificationService;
import com.juney.webservice.web.dto.PhoneVerificationRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin
@RestController
public class VerificationController {

	private final VerificationService verificationService; 
	private final UserService userService;
	
	// 폰 인증번호 요청할 때
	@PostMapping("/verification/phone")
	public ResponseEntity<Boolean> StartPhoneVerification(@RequestBody PhoneVerificationRequestDto phoneVerificationRequestDto) {
			System.out.println(phoneVerificationRequestDto.getPayload());
			boolean result = verificationService.startPhoneVerification(phoneVerificationRequestDto.getPayload());
			if(result) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
	}
	
	// 폰 인증번호 입력했을 때
	@PutMapping("/verification/phone")
	public ResponseEntity<String> CompletePhoneVerification(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("key") String key){
		int result = verificationService.completePhoneVerification(phoneNumber, key);
		switch (result) {
		case 0:
			return new ResponseEntity<>("비회원", HttpStatus.OK); 
		case 1:
			return new ResponseEntity<>("인증됨", HttpStatus.OK);
		case 2:
			return new ResponseEntity<>("키가 유효하지 않습니다.", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/verification/email")
	public ResponseEntity<String> CompleteEmailVerification(@RequestParam("email") String email, @RequestParam("key") String key) {
		int result = verificationService.completeEmailVerification(email, key);
		switch (result) {
		case 0:
			return new ResponseEntity<>("비회원", HttpStatus.OK);
		case 1:
			return new ResponseEntity<>("인증됨", HttpStatus.OK);
		case 2:
			return new ResponseEntity<>("키가 유효하지 않습니다.", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("내부오류", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
