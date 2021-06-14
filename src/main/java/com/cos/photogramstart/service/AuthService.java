package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service//ioc , 트랜젝션 관리
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Transactional // write(insert,update,delete)
	public User 회원가입(User user) {
		//회원가입 진행
		String rowPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rowPassword);
		user.setPassword(encPassword)
		;
		user.setRole("ROLE_USER"); // 관리자는 ROLE_ADMIN
		User userEntity =  userRepository.save(user);
		
		return userEntity;
	}
}
