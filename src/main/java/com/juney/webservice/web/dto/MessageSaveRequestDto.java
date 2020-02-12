package com.juney.webservice.web.dto;

import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class MessageSaveRequestDto {

	private Chat chat;
	private User user;
	private String text;
	
}
