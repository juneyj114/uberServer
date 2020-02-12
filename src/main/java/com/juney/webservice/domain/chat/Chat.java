package com.juney.webservice.domain.chat;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.juney.webservice.domain.BaseTimeEntity;
import com.juney.webservice.domain.message.Message;
import com.juney.webservice.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Chat extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "chat")
	private List<Message> messages;
	
	@OneToOne
	@JoinColumn(name = "passengerId")
	private User passenger;
	
	@OneToOne
	@JoinColumn(name = "driverId")
	private User driver;
	
	@Builder
	public Chat(User driver, User passenger) {
		this.driver = driver;
		this.passenger = passenger;
	}
}
