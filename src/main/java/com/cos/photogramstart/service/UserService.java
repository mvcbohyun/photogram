package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository; 
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Transactional
	public User 회원수정(Long id , User user) {
		System.out.println("111111111111111");
		// 영속화
		User findUser =userRepository.findById(id);
		
		if(findUser == null) {
			throw new CustomValidationApiException("찾을수 없는 아이디 입니다");
		}
		// 영속화도힌 오브젝트 수정 -- 더티체킹(업데이트 완료)
		System.out.println("2222222222222222222"+findUser);
		String rawPassword =user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		findUser.setName(user.getName());
		findUser.setPassword(encPassword);
		findUser.setBio(user.getBio());
		findUser.setWebsite(user.getWebsite());
		findUser.setPhone(user.getPhone());
		findUser.setGender(user.getGender());
		
		return findUser; // 이때 더티 체킹이 일어나서 업데이트됨 
		
	}
}
