package com.juney.webservice.util;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class Jws {
	
	private static String secretKey = "dw5Shc36CLcqjas2asaPbSqpSzPSQGChpB5JaxPDvpBANngGCX";
	
	public static String createJws(Long id) {
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
		String jws = Jwts.builder()
				.setId(id.toString())
				.signWith(key)
				.compact();
		return jws;
	}
	
	public static String parseJws(String requestJws) {
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		SecretKey key = Keys.hmacShaKeyFor(apiKeySecretBytes);
		io.jsonwebtoken.Jws<Claims> jws;
		jws = Jwts.parser().setSigningKey(key).parseClaimsJws(requestJws);
		return jws.getBody().getId();
	}
	
}
