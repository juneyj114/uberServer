package com.juney.webservice.domain.place;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.juney.webservice.domain.BaseTimeEntity;
import com.juney.webservice.domain.user.User;
import com.juney.webservice.web.dto.PlaceUpdateRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@Entity
public class Place extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private float lat = 0;
	
	@Column(nullable = false)
	private float lng = 0;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private boolean isFav = false;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public void update(PlaceUpdateRequestDto dto) {
		this.name = dto.getName();
		this.isFav = dto.isFav();
	}
	
}
