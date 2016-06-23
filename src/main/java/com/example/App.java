package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.service.CustomerService;

@SpringBootApplication
public class App implements CommandLineRunner{

	@Autowired
	CustomerService customerService;
	
	@Override
	public void run(String... args) throws Exception {
		
	} 

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}
}
