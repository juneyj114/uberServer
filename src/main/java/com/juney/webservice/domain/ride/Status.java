package com.juney.webservice.domain.ride;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

	REQUESTING("Requesting"),
	ACCEPTED("Accepted"),
	ONROUTE("Onroute"),
	FINISHED("Finished"),
	CANCELED("Canceled");
	
	private final String key;
}
