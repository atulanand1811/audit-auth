package com.authentication.AuditAuthentication.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.authentication.AuditAuthentication.model.LoginModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility Class used to support JWT operations
 */
@Service
public class JwtUtil {
	@Value("${jwt.secret}")
	String secretkey;
	@Value("${jwt.expireToken}")
	String tokenExp;

	/**
	 * This function extracts the username from a JWT Token
	 * 
	 * @param token
	 * @return
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * This function extracts the expiration date from a JWT Token
	 * 
	 * @param token
	 * @return
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extract claims of a JWT Token filtered by claimsResolver
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extract all claims of JWT Token
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {
		String token1 = token.trim().replace("\0xfffd", "");
		return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token1).getBody();
	}

	/**
	 * Checks to see if JWT token is expired
	 * 
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Generates a JWT Token given userDetails
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * Creates a JWT Token given claims and subject
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		int tokenExpNum = Integer.parseInt(tokenExp);
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * tokenExpNum))
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
	}

	/**
	 * Validates a token given the username and token
	 * 
	 * @param loginModel
	 * @return
	 */
	public Boolean validateToken(LoginModel loginModel) {
		final String username = extractUsername(loginModel.getToken());
		return (username.equals(loginModel.getUsername()) && !isTokenExpired(loginModel.getToken()));
	}

	/**
	 * Checks to see if given token is expired or not
	 * 
	 * @param token
	 * @return
	 */
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
