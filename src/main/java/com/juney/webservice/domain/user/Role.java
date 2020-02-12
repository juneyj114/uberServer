package com.juney.webservice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	DRIVER("ROLE_DRIVER", "운전자"),
	PASSENGER("ROLE_PASSENGER", "승객");
	
	private final String key;
	private final String title;
}
