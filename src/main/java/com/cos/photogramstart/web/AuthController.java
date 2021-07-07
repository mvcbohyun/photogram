package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller //ioc , 파일을 리턴 하는 컨트롤러 
@Log4j2
@RequiredArgsConstructor//DI 할때 사용
public class AuthController {
	
	private final AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	// 회원가입버튼 - > /auth/signup -> /auth/signin
	@PostMapping("/auth/signup")
	public  String signup(@Valid SignupDto signupDto, BindingResult result) {
		
		
		
	
		
			log.info(signupDto.toString());
			User user = signupDto.toEntity();
			log.info(user.toString());
			
			User userEntity  =authService.회원가입(user);
			
			System.out.println(userEntity);
			return "auth/signin";
		
		
	
		
	}
	

}
