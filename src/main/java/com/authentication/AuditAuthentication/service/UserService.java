package com.authentication.AuditAuthentication.service;

import com.authentication.AuditAuthentication.exception.CustomLoginException;
import com.authentication.AuditAuthentication.model.LoginModel;
import com.authentication.AuditAuthentication.model.UserCredentials;

/**
 * Interface to support operations with {@link UserCredentials}
 *
 */
public interface UserService {
	public void addUser(UserCredentials userCredentials);

	boolean validateJwtToken(LoginModel loginModel);

	LoginModel validateCredentialsUser(UserCredentials userLoginCredentials) throws CustomLoginException;

	boolean validateJwtToken(String token);

	String getUsernameFromToken(String token);

}
