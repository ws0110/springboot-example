package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Override
	public void configure(WebSecurity web) throws Exception{
		// Security 설정 무시
		web.ignoring().antMatchers("/webjars/**", "/css/**", "/api/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		// 인가 설정
		http.authorizeRequests()
			.antMatchers("/loginForm").permitAll()
			.anyRequest().authenticated();
		
		// 로그인 설정
		http.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/loginForm")
			.failureUrl("/loginForm?error")
			.defaultSuccessUrl("/customers", true)
			.usernameParameter("username").passwordParameter("password")
			.and();
		
		// 로그아웃 설정
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
			.logoutSuccessUrl("/loginForm");
	}
	
	
	/**
	 * 인증처리 사항 설정 클래스
	 */
	@Configuration
	static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter{
		
		@Autowired
		UserDetailsService userDetailsService;
		
		@Bean
		PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
		
		// UserDetailService 설정, 패스워드 암호화 방법 설정
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception{
			auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
		}
	}
}

