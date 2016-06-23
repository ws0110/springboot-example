package com.example.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class LoginController {

	@RequestMapping("loginForm")
	String loginForm(){
		log.info("LOGINFORM!!!!");
		return "loginForm";
	}
}
