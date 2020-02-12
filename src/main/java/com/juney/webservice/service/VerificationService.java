package com.juney.webservice.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.domain.verification.Target;
import com.juney.webservice.domain.verification.Verification;
import com.juney.webservice.domain.verification.VerificationRepository;
import com.juney.webservice.util.SendEmail;
import com.juney.webservice.web.dto.UserSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VerificationService {

	private final VerificationRepository verificationRepository;
	private final UserRepository userRepository;
	private final HttpSession httpSession;
	private final SendEmail sendEmail;

	@Transactional
	public boolean startPhoneVerification(String phoneNumber) {
		try {
			Verification existingVerification = verificationRepository.findByPayload(phoneNumber);
			if (existingVerification != null) {
				System.out.println(existingVerification.getId());
				verificationRepository.delete(existingVerification);
			}
			String key = verificationRepository.save(Verification.builder()
														.payload(phoneNumber)
														.target(Target.PHONE)
														.build())
														.createKey();
			System.out.println(phoneNumber + "로 "+key+"를 전송했습니다.");
			// TODO 폰으로 key 전송
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public int completePhoneVerification(String phoneNumber, String key) {
		try {
			Verification verification = verificationRepository.findByPayloadAndKi(phoneNumber, key);
			if (verification != null) {
				verification.setVerified(true);
			} else {
				return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		try {
			User user = userRepository.findByPhoneNumber(phoneNumber);
			if (user != null) {
				user.setVerifiedPhoneNumber(true);
				httpSession.setAttribute("id", user.getId());
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}
	
	@Transactional
	public int startEmailVerification(UserSaveRequestDto userSaveRequestDto) throws IOException {
		try {
			boolean result = verificationRepository.existsByPayloadAndVerifiedTrue(userSaveRequestDto.getPhoneNumber());
			if (result) {
				Verification verification = verificationRepository
						.save(Verification.builder().target(Target.EMAIL).payload(userSaveRequestDto.getEmail()).build());
				sendEmail.sendEmail(verification.createKey());
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Transactional
	public int completeEmailVerification(String email, String key) {
		try {
			Verification verification = verificationRepository.findByPayloadAndKi(email, key);
			if (verification != null) {
				verification.setVerified(true);
			} else {
				return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		try {
			User user = userRepository.findByEmail(email).get();
			if (user != null) {
				user.setVerifiedEmail(true);
				httpSession.setAttribute("id", user.getId());
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
