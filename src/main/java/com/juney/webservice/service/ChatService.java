package com.juney.webservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.domain.chat.ChatRepository;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.web.dto.ChatResposneDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatService {

	private final ChatRepository chatRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public boolean save(Long driverId, Long passengerId) {
		try {
			User driver = userRepository.getOne(driverId);
			User passenger = userRepository.getOne(passengerId);
			Chat chat = chatRepository.save(Chat.builder().driver(driver).passenger(passenger).build());
			driver.setChat(chat);
			passenger.setChat(chat);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public ChatResposneDto get(Long id) {
		try {
			Chat chat = chatRepository.findByDriverIdOrPassengerId(id, id);
			return new ChatResposneDto(chat);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
