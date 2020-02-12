package com.juney.webservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juney.webservice.config.auth.dto.UserPrincipal;
import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.service.ChatService;
import com.juney.webservice.web.dto.ChatResposneDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {

	private final ChatService chatService;
	
	@GetMapping("/chat/{id}")
	public ResponseEntity<ChatResposneDto> get(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		ChatResposneDto chatResponseDto = chatService.get(userPrincipal.getId());
		if(chatResponseDto != null) {
			return new ResponseEntity<>(chatResponseDto, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	};
}
