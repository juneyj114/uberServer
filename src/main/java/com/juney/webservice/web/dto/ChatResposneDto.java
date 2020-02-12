package com.juney.webservice.web.dto;

import java.util.List;

import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.domain.message.Message;
import com.juney.webservice.domain.user.User;

import lombok.Getter;

@Getter
public class ChatResposneDto {

	private List<Message> messages;

	private User passenger;

	private User driver;
	
	public ChatResposneDto(Chat chat) {
		this.messages = chat.getMessages();
		this.passenger = chat.getPassenger();
		this.driver = chat.getDriver();
	}
}
