package com.juney.webservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.service.MessageService;
import com.juney.webservice.web.dto.MessageSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {

	private final MessageService messageService;
	
	@PostMapping("/message")
	public ResponseEntity<String> save(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody MessageSaveRequestDto messageSaveRequestDto) {
		boolean result = messageService.save(userPrincipal.getId(), messageSaveRequestDto);
		if(result) {
			return new ResponseEntity<>("저장", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("오류", HttpStatus.BAD_REQUEST);
		}
		
	}
}
