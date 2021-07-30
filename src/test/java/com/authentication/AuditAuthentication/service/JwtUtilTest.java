package com.authentication.AuditAuthentication.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.authentication.AuditAuthentication.model.LoginModel;

@RunWith(SpringRunner.class)
@SpringBootTest
class JwtUtilTest {
	@Autowired
	JwtUtil jwtUtilService;
	@Autowired
	IUserService userService;

	@Test
	void extractUsernameTest() {
		UserDetails userDetails = userService.loadUserByUsername("admin");
		String token = jwtUtilService.generateToken(userDetails);
		String username = jwtUtilService.extractUsername(token);
		assertEquals("admin", username);
	}

	@Test
	void extractExpirationTest() {
		UserDetails userDetails = userService.loadUserByUsername("admin");
		String token = jwtUtilService.generateToken(userDetails);
		Date date = jwtUtilService.extractExpiration(token);
		assertNotNull(date);
	}

	@Test
	void extractAllClaimsTest() {
		UserDetails userDetails = userService.loadUserByUsername("admin");
		String token = jwtUtilService.generateToken(userDetails);
		assertNotNull(token);

	}

	@Test
	void validateCorrectTokenTest() {
		UserDetails userDetails = userService.loadUserByUsername("admin");
		String token = jwtUtilService.generateToken(userDetails);
		assertNotNull(token);
	}

	@Test
	void validateIncorrectTokenTest() {
		LoginModel loginModel = new LoginModel("admin",
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNjk0MTAxOCwiaWF0IjoxNjI2OTM5MjE4fQ.cROV5sjMXDcVY74zgWZsawx4MRAaW-Ai9_pjuX8woPA");
		try {
			jwtUtilService.validateToken(loginModel);
			fail("The jwt token is expired, but no error was thrown");
		} catch (Exception e) {

		}

	}

}
