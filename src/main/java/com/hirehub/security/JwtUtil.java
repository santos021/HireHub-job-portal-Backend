package com.hirehub.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expirationMs}")
	private long jwtExpirationMs;

	private Key signingKey;

	@PostConstruct
	public void init() {
		signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username, List<String> roles) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setSubject(username).claim("roles", roles) // optional: include roles in token
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + jwtExpirationMs))
				.signWith(signingKey, SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(signingKey)
			.build()
			.parseClaimsJws(token);
			
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
//		return false;
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRolesFromToken(String token){
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		Object roles = claims.get("roles");
		if (roles instanceof List) {
			return ((List<?>) roles).stream().map(Object::toString).collect(Collectors.toList());
		}
		return List.of();
	}
}
