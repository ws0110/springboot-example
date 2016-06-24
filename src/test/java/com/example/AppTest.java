package com.example;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.example.domain.JpaCustomer;
import com.example.domain.User;
import com.example.repository.JpaCustomerRepository;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=App.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0",
					"spring.datasource.url:jdbc:log4jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"})

public class AppTest {

	@Autowired
	JpaCustomerRepository customerRepository;
	
	@Value("${local.server.port}")
	int port;
	
	String apiEndpoint;
	
	RestTemplate restTemplate = new TestRestTemplate();
	
	JpaCustomer customer1;
	JpaCustomer customer2;
	
	// Controller 응답용 Domain
	@Data
	static class Page<T>{
		private List<T> content;
		private int numberOfElements;
	}
	
	@Before
	public void setUp(){
		User user = new User();
		user.setUsername("user1");
		
		customerRepository.deleteAll();
		customer1 = new JpaCustomer();
		customer1.setFirstName("A");
		customer1.setLastName("AAA");
		customer1.setUser(user);
		
		customer2 = new JpaCustomer();
		customer2.setFirstName("B");
		customer2.setLastName("BBB");
		customer2.setUser(user);
		
		customerRepository.save(customer1);
		apiEndpoint = "http://localhost:" + port + "api/customers";
	}
	
	@Test
	public void testGetCustomers() throws Exception{
		ResponseEntity<Page<JpaCustomer>> response = restTemplate.exchange(
				apiEndpoint, HttpMethod.GET, null, new ParameterizedTypeReference<Page<JpaCustomer>>() {}  // Generic 타입 파라메타:ParameterizedTypeReference
				);
		
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().getNumberOfElements(), is(2));
	}
}
