package io.webBack.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.webBack.ppmtool.domain.User;
import static io.webBack.ppmtool.security.SecurityConstant.*;

@Component
public class JwtTokenProvider {

	//Generate the token
//	public String generateToken(Authentication auth) {
//		User user = (User)auth.getPrincipal();
//		System.out.println(user.getUsername());
//		System.out.println(user.getPassword());
//		Date now = new Date(System.currentTimeMillis());
//		Date expiryDate =  new Date(now.getTime() + EXPIRATION_TIME);
//		
//		String userId = Long.toString(user.getId());
//		
//		Map<String , Object>  claims = new HashMap<>();
//		claims.put("id", userId);
//		claims.put("username", user.getUsername());
//		claims.put("fullname", user.getFullname());
//		
//		
//		return Jwts.builder().setSubject(userId).
//				setClaims(claims).
//				setIssuedAt(now).
//				setExpiration(expiryDate)
//				.signWith(SignatureAlgorithm.HS512	, SECRET)
//				.compact();
//		
//	}
	public String generateToken(UserDetails user) {
		User userD=(User)user;
		System.out.println(userD.getUsername());
		System.out.println(userD.getPassword());
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate =  new Date(now.getTime() + EXPIRATION_TIME);
		
		String userId = Long.toString(userD.getId());
		
		Map<String , Object>  claims = new HashMap<>();
		claims.put("id", userId);
		claims.put("username", userD.getUsername());
		claims.put("fullname", userD.getFullName());
		
		
		return Jwts.builder().setSubject(userId).
				setClaims(claims).
				setIssuedAt(now).
				setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512	, SECRET)
				.compact();
		
	}
	
	//Validate the token
	
	public boolean validateToken(String token) {
		try{
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
			return true;
		}catch(SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		}catch(MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");
		}catch(ExpiredJwtException ex) {
			System.out.println("Expired JWT Token");
		}catch(UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT Token");
		}catch(IllegalArgumentException ex) {
			System.out.println("JWT Claims String is empty");
		}
		return false;
	}
	
	//Get User Id from token
	public Long getUserIdfromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		
		return Long.parseLong(id);
	}
	
	
}
