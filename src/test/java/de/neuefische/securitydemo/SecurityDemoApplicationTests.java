package de.neuefische.securitydemo;

import de.neuefische.securitydemo.user.LoginData;
import de.neuefische.securitydemo.user.UserDocument;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityDemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		ResponseEntity<UserDocument> createUserResponse = restTemplate.postForEntity("/api/users", new UserDocument(null, "test@email.de", "123456", "USER", null), UserDocument.class);
		assertThat(createUserResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createUserResponse.getBody().getId()).isNotNull();

		ResponseEntity<String> loginResponse = restTemplate.postForEntity("/api/auth/login", new LoginData("test@email.de", "123456"), String.class);
		assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(loginResponse.getBody()).isNotBlank();

		ResponseEntity<String> greetResponse = restTemplate.exchange(
				"/api/greet",
				HttpMethod.GET,
				new HttpEntity<>(createHeaders(loginResponse.getBody())),
				String.class
		);
		assertThat(greetResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(greetResponse.getBody()).isEqualTo("Moin test@email.de");

		ResponseEntity<String> adminGreetResponse = restTemplate.exchange(
				"/api/admingreet",
				HttpMethod.GET,
				new HttpEntity<>(createHeaders(loginResponse.getBody())),
				String.class
		);
		assertThat(adminGreetResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	private HttpHeaders createHeaders(String token){
		String authHeader = "Bearer " + token;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);

		return headers;
	}
}
