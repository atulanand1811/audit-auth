package com.authentication.AuditAuthentication.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.AuditAuthentication.exception.CustomLoginException;
import com.authentication.AuditAuthentication.exception.CustomRegistrationException;
import com.authentication.AuditAuthentication.model.LoginModel;
import com.authentication.AuditAuthentication.model.UserCredentials;
import com.authentication.AuditAuthentication.repository.UserRepository;

/**
 * Class to support operations with {@link UserCredentials} implements {@link UserService}
 */
@Service
public class IUserService implements UserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtil jwtUtilObject;

	/**
	 * Inserts a given user credentials to the database
	 */
	@Override
	public void addUser(UserCredentials userCredentials) throws CustomRegistrationException {
		try {
			Optional<UserCredentials> existingUser = userRepository.findById(userCredentials.getUsername());
			if (!existingUser.isPresent())
				userRepository.save(userCredentials);
			else
				throw new CustomRegistrationException("User already exists");
		}
		catch(Exception e) {
			throw new CustomRegistrationException(e);
		}

	}

	/**
	 * Selects the user which with a given username
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, InvalidDataAccessApiUsageException {
		UserCredentials userObject = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
		return new User(userObject.getUsername(), userObject.getPassword(), new ArrayList<>());
	}

	/**
	 * Validates a user's credentials in accordance to the given user's credentials,
	 * if valid then it returns a JWT with {@link LoginModel}
	 */
	@Override
	public LoginModel validateCredentialsUser(UserCredentials userLoginCredentials) throws CustomLoginException {
		final UserDetails userDetails = loadUserByUsername(userLoginCredentials.getUsername());

		if (userDetails.getPassword().equals(userLoginCredentials.getPassword())) {
			String token = jwtUtilObject.generateToken(userDetails);
			LoginModel loginModel = new LoginModel();
			loginModel.setToken(token);
			loginModel.setUsername(userDetails.getUsername());
			return loginModel;
		} else {

			throw new CustomLoginException("Invalid details");

		}

	}

	/**
	 * Validates a given login model's JWT Token
	 */
	@Override
	public boolean validateJwtToken(LoginModel loginModel) {
		return (loginModel.getToken() != null && jwtUtilObject.validateToken(loginModel));
	}
	@Override
	public boolean validateJwtToken(String token) {
		return (token != null && jwtUtilObject.validateToken(token));
	}

	@Override
	public String getUsernameFromToken(String token) {
		
		return jwtUtilObject.extractUsername(token);
	}

}
