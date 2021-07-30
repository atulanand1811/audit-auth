package com.authentication.AuditAuthentication.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.authentication.AuditAuthentication.model.LoginModel;
import com.authentication.AuditAuthentication.model.UserCredentials;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
	@Autowired
	IUserService userService;
	@Autowired
	JwtUtil jwtUtilObject;

	@Test
	void addNewUserTest() {
		UserCredentials userCredentials = new UserCredentials("dummy2", "dummy2123");
		try {
			userService.addUser(userCredentials);
		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	void addExistingUserTest() {
		UserCredentials userCredentials = new UserCredentials("admin", "admin123");
		try {
			userService.addUser(userCredentials);
			fail("User already exists, but no exception was thrown!");

		} catch (Exception e) {
		}

	}

	@Test
	void loadUserByCorrectUsernameTest() {
		String username = "admin";
		try {
			UserDetails userDetails = userService.loadUserByUsername(username);
			assertNotNull(userDetails);

		} catch (Exception e) {
			fail(e);

		}
	}

	@Test
	void loadUserByIncorrectUsernameTest() {
		String username = "add min";
		try {
			UserDetails userDetails = userService.loadUserByUsername(username);
			fail("User does not exist, but no exception was thrown!");

		} catch (Exception e) {

		}
	}

	@Test
	void validateCorrectCredentialsUserTest() {
		UserCredentials userCredentials = new UserCredentials("admin", "admin123");
		try {
			LoginModel loginModel = userService.validateCredentialsUser(userCredentials);
			assertNotNull(loginModel);
		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	void validateIncorrectCredentialsUserTest() {
		UserCredentials userCredentials = new UserCredentials("add min", "admin123");
		try {
			LoginModel loginModel = userService.validateCredentialsUser(userCredentials);
			fail("User was invalid, but no exception was thrown!");
		} catch (Exception e) {
		}

	}

	@Test
	void validateCorrectJwtToken() {
		UserDetails userDetails = userService.loadUserByUsername("admin");
		LoginModel loginModel = new LoginModel("admin", jwtUtilObject.generateToken(userDetails));

		assertEquals(true, userService.validateJwtToken(loginModel));
	}

	@Test
	void validateIncorrectJwtToken() {
		LoginModel loginModel = new LoginModel("admin",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNjk0MTAxOCwiaWF0IjoxNjI2OTM5MjE4fQ.cROV5sjMXDcVY74zgWZsawx4MRAaW-Ai9_pjuX8woPA");
		try {
			userService.validateJwtToken(loginModel);
			fail("Token was invalid, but no exception was thrown!");
		} catch (Exception e) {

		}
	}

}
