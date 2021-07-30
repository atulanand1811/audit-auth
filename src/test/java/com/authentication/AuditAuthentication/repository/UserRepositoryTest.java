package com.authentication.AuditAuthentication.repository;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.authentication.AuditAuthentication.model.UserCredentials;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;

	@Test
	void saveTest() {
		try {
			UserCredentials user = new UserCredentials("admin", "admin123");
			userRepository.save(user);
		} catch (Exception e) {
			fail(e);
		}

	}

	@Test
	void findByIdCorrectTest() {
		Optional<UserCredentials> user = userRepository.findById("admin");
		if (!user.isPresent())
			fail("User present, but was not found by repository");
	}

	@Test
	void findByIdIncorrectTest() {
		Optional<UserCredentials> user = userRepository.findById("add min");
		if (user.isPresent())
			fail("User not present, but was found by repository");

	}

}
