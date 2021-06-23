package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	
	private final UserRepository userRepository; 
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserProfileDto 회원프로필(Long pageUserId,Long principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		//select * from image where userid =:userid;
		User userEntity = userRepository.findById(pageUserId);
		
		if(userEntity ==null){
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		}
		dto.setUser(userEntity);
		dto.setImageCount(userEntity.getImages().size());
		dto.setPageOwnerState(pageUserId==principalId );// 1은 페이지 주인 -1은 페이지 주인 아님
		return dto;
	}
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
