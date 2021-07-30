package com.authentication.AuditAuthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.AuditAuthentication.exception.CustomLoginException;
import com.authentication.AuditAuthentication.exception.CustomRegistrationException;
import com.authentication.AuditAuthentication.model.AuthResponse;
import com.authentication.AuditAuthentication.model.LoginModel;
import com.authentication.AuditAuthentication.model.UserCredentials;
import com.authentication.AuditAuthentication.service.IUserService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthenticationController {
	@Autowired
	IUserService userService;
	@Value("${messages.logging.userCreated}")
	String userCreatedMessage;
	@Value("${messages.logging.loginSuccessful}")
	String loginSuccessful;
	@Value("${messages.logging.validJwtToken}")
	String validTokenMessage;
	@Value("${messages.logging.invalidJwtToken}")
	String invalidTokenMessage;

	/**
	 * Users can register credentials using this API
	 * 
	 * @param userRegistrationCredentials
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@RequestBody UserCredentials userRegistrationCredentials)
			throws CustomRegistrationException {
		userService.addUser(userRegistrationCredentials);
		AuthResponse authResponse = new AuthResponse(userRegistrationCredentials.getUsername(), true);
		log.info(userCreatedMessage);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	/**
	 * Users can login with their credentials, after successful login a JWT token is
	 * generated as response which can be used for further API calls
	 * 
	 * @param userLoginCredentials
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<LoginModel> loginUser(@RequestBody UserCredentials userLoginCredentials)
			throws CustomLoginException, UsernameNotFoundException, ExpiredJwtException,
			InvalidDataAccessApiUsageException {
		LoginModel loginResponse = userService.validateCredentialsUser(userLoginCredentials);
		log.info(loginSuccessful);
		return new ResponseEntity<LoginModel>(loginResponse, HttpStatus.OK);

	}

	/**
	 * Microservices may use this API to validate a JWT token's validity
	 * 
	 * @param loginModel
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/validate")
	public ResponseEntity<AuthResponse> validateUser(@RequestHeader("Authorization") String token)
			throws ExpiredJwtException {
		log.debug("Token:" + token);
		AuthResponse authResponse = new AuthResponse(null, false);
		if (userService.validateJwtToken(token)) {
			log.info(validTokenMessage);
			authResponse.setUid(userService.getUsernameFromToken(token));
			authResponse.setValid(true);
			return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		} else {
			log.info(invalidTokenMessage);

			return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.UNAUTHORIZED);
		}

	}
}
