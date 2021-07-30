package com.authentication.AuditAuthentication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.authentication.AuditAuthentication.model.LoginModel;
import com.authentication.AuditAuthentication.model.UserCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuditAuthenticationControllerTest {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	AuditAuthenticationControllerTest applicationService;
	@Autowired
	ObjectMapper mapper;

	// Tests for register route
	@Test
	void testNewRegister() throws Exception {

		UserCredentials userCredentials = new UserCredentials("dummy", "dummypassword123");
		MvcResult result = mockMvc
				.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(201, result.getResponse().getStatus());

	}

//	@Test
//	void testExistingRegister() throws Exception {
//
//		UserCredentials userCredentials = new UserCredentials("admin", "admin123");
//		MvcResult result = mockMvc
//				.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
//						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
//				.andReturn();
//		assertEquals(406, result.getResponse().getStatus());
//	}

	@Test
	void testNullPasswordRegister() throws Exception {

		UserCredentials userCredentials = new UserCredentials("dummy1", null);
		MvcResult result = mockMvc
				.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(406, result.getResponse().getStatus());
	}

	@Test
	void testNullUsernameRegister() throws Exception {
		UserCredentials userCredentials = new UserCredentials(null, "dummypassword123");
		MvcResult result = mockMvc
				.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(406, result.getResponse().getStatus());
	}

	@Test
	void testIncorrectPasswordValidationRegister() throws Exception {
		UserCredentials userCredentials = new UserCredentials("dummy2", "dumm");
		MvcResult result = mockMvc
				.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(406, result.getResponse().getStatus());
	}

	// Tests for login route
//	@Test
//	void testCorrectLogin() throws Exception {
//
//		UserCredentials userCredentials = new UserCredentials("admin", "admin123");
//		MvcResult result = mockMvc
//				.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
//						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
//				.andReturn();
//		assertEquals(200, result.getResponse().getStatus());
//	}

	@Test
	void testInorrectLogin() throws Exception {

		UserCredentials userCredentials = new UserCredentials("dummy", "wrongpassword");
		MvcResult result = mockMvc
				.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(401, result.getResponse().getStatus());
	}

	@Test
	void testNullUsernameLogin() throws Exception {

		UserCredentials userCredentials = new UserCredentials(null, "dummypassword123");
		MvcResult result = mockMvc
				.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(401, result.getResponse().getStatus());
	}

	@Test
	void testNullPasswordLogin() throws Exception {

		UserCredentials userCredentials = new UserCredentials("dummy", "");
		MvcResult result = mockMvc
				.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(401, result.getResponse().getStatus());
	}

	// Tests for validate route
//	@Test
//	void testCorrectValidate() throws Exception {
//
//		UserCredentials userCredentials = new UserCredentials("admin", "admin123");
//		MvcResult loginResult = mockMvc
//				.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
//						.content(mapper.writeValueAsString(userCredentials)).accept(MediaType.APPLICATION_JSON))
//				.andReturn();
//
//		LoginModel loginModel = new ObjectMapper().readValue(loginResult.getResponse().getContentAsString(),
//				LoginModel.class);
//
//		MvcResult result = mockMvc.perform(get("/validate").header("Authorization", loginModel.getToken())).andReturn();
//		assertEquals(200, result.getResponse().getStatus());
//	}

	@Test
	void testIncorrectValidate() throws Exception {
		MvcResult result = mockMvc.perform(post("/validate").contentType(MediaType.APPLICATION_JSON).content(
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNjk0MTAxOCwiaWF0IjoxNjI2OTM5MjE4fQ.cROV5sjMXDcVY74zgWZsawx4MRAaW-Ai9_pjuX8woPA")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(405, result.getResponse().getStatus());
	}
}
