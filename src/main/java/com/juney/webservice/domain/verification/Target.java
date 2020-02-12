package com.juney.webservice.domain.verification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Target {

	EMAIL("Email"),
	PHONE("Phone");
	
	private final String key;
	
}
