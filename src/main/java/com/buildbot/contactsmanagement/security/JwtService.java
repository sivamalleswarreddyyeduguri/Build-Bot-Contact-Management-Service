package com.buildbot.contactsmanagement.security;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtService {
	
	private final String SECRET = "mqKcywWjlQRXQI5d9R1ddHP2hZCX81BERR10w/T1aZk=";
	
	
	
	public String extractUsername(String token) {
		System.err.println("Inside extractUsername() ");
		
		return extractClaims(token,Claims::getSubject);
	}


	private <T> T extractClaims(String token, Function<Claims , T> claimsResolver) {
		System.err.println("inside extractClaim()");
		Claims claims = extractAllClaims(token);
		
		return claimsResolver.apply(claims);
	}


	private Claims extractAllClaims(String token) {
		System.err.println("inside claimsAll()");
		
		return Jwts
				.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();			
				
	}
	
	
	public boolean isValid(String token, UserDetails details) {
		String userName = extractUsername(token);
		
		if(details.getUsername().equals(userName) && !isTokenExpired(token))
			return true;
		return false;
	}
	
	

	private boolean isTokenExpired(String token) {
		Date claims = extractClaims(token, Claims::getExpiration);
		
		return claims.before(new Date());
	}


	public String getToken(UserDetails user)
	{
		
		return createToken(user);
		
	}

	
	private String createToken( UserDetails user) {
	

            return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()))
				.signWith(getSignKey()).claim("roles", user.getAuthorities())
				.compact();
				

}

	private SecretKey getSignKey() {
		byte[] decode = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(decode);
		
	}


	

	
}	