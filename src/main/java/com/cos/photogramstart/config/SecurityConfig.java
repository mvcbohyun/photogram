package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity//해당 파일로 시큐리티를 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	//	super.configure(http);
	// super 삭제  기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**") // 권한이 없을때는 .loginPage("/auth/signin") 이쪽으로 자동으로
			.authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin") //get
			.loginProcessingUrl("/auth/signin") // post ->스프링 시큐리티가 로그인 프로세스 진행
			.defaultSuccessUrl("/"); // 권한이 있다면 여기로 
	}
}
