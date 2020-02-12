package com.juney.webservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.domain.chat.ChatRepository;
import com.juney.webservice.domain.message.Message;
import com.juney.webservice.domain.message.MessageRepository;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.domain.user.UserRepository;
import com.juney.webservice.firebase.MyFirebase;
import com.juney.webservice.web.dto.MessageSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final ChatRepository chatRepository;
	private final UserRepository userRepository;
	private final MyFirebase myFirebase;

	@Transactional
	public boolean save(Long id, MessageSaveRequestDto dto) {
		try {
			User user = userRepository.getOne(id);
			Chat chat = chatRepository.getOne(dto.getChat().getId());
			if(chat != null && (chat.getPassenger().getId() == user.getId() || chat.getDriver().getId() == user.getId())) {
				messageRepository.save(new Message(dto));
				sendMessageTo(chat, id, dto.getText());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean sendMessageTo(Chat chat, Long userId, String text) {
		if(chat.getDriver().getId() == userId) {
			myFirebase.sendMessageToChat(chat.getPassenger().getFirebaseToken(), text);
			return true;
		} else if(chat.getPassenger().getId() == userId) {
			myFirebase.sendMessageToChat(chat.getDriver().getFirebaseToken(), text);
			return true;
		} else {
			return false;
		}
		
		
		
	}
}
