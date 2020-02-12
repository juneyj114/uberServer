package com.juney.webservice.domain.verification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.juney.webservice.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Verification extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Target target;
	
	@Column(nullable = false)
	private String payload;
	
	private String ki;
	
	private boolean verified = false;
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	@Builder
	public Verification(Target target, String payload) {
		this.target = target;
		this.payload = payload;
	}
	
	public String createKey() {
		double randomKey = Math.random();
		if(this.target.equals(Target.PHONE)) {
			randomKey = randomKey*10000;
			this.ki = String.valueOf((int)randomKey);
		} else {
			randomKey = randomKey*100000000;
			this.ki = String.valueOf((int)randomKey);
		}
		return this.ki;
	}

	
	
}
