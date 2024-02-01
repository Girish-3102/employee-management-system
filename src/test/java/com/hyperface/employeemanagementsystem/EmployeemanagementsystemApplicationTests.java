package com.hyperface.employeemanagementsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperface.employeemanagementsystem.models.Role;
import com.hyperface.employeemanagementsystem.models.UserAuth;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeResponse;
import com.hyperface.employeemanagementsystem.services.impl.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeemanagementsystemApplicationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private JwtService jwtService;
	private final ObjectMapper objectMapper = new ObjectMapper();
	String jwtToken;
	HttpHeaders headers;
	@BeforeEach
	void setUp(){
		headers = new HttpHeaders();
		jwtToken= jwtService.generateToken(new UserAuth(3L,"harish@gmail.com","harish@123", Role.USER));
		headers.set("Authorization", "Bearer " + jwtToken);
	}
	@Test
	public void TestGetEmployeeById_ReturnsEmployeeResponse() throws JsonProcessingException {
		String url = "http://localhost:" + port + "/employee/3";
		RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
		String response = restTemplate.exchange(requestEntity, String.class).getBody();
		JsonNode responseJson = objectMapper.readTree(response);
		Assertions.assertEquals("Harish", responseJson.get("firstName").asText());
	}
	@Test
	public void PostDepartmentWithUserRole_ReturnsForbidden() throws JsonProcessingException {
		String url = "http://localhost:" + port + "/department";
		RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.POST, URI.create(url));
		String response = restTemplate.exchange(requestEntity, String.class).getBody();
		JsonNode responseJson = objectMapper.readTree(response);
		Assertions.assertEquals("FORBIDDEN", responseJson.get("status").asText());
	}

}
