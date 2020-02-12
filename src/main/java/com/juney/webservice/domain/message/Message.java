package com.juney.webservice.domain.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.juney.webservice.domain.BaseTimeEntity;
import com.juney.webservice.domain.chat.Chat;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.web.dto.MessageSaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Message extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 500, nullable = false)
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "chatId", nullable = false)
	private Chat chat;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	public Message(MessageSaveRequestDto dto) {
		this.text = dto.getText();
		this.chat = dto.getChat();
		this.user = dto.getUser();
	}
	
}
