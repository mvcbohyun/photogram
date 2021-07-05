package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	
	private final UserRepository userRepository; 
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${file.path}")// yml 에 있는거 가져 오기 
	private String uploadFolder;
	
	public UserProfileDto 회원프로필(Long pageUserId,Long principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		//select * from image where userid =:userid;
		User userEntity = userRepository.findById(pageUserId);
		
		if(userEntity ==null){
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		}
		System.out.println("pageUserId :"+pageUserId);
		System.out.println("principalId :"+principalId);
		dto.setUser(userEntity);
		dto.setImageCount(userEntity.getImages().size());
		dto.setPageOwnerState(pageUserId==principalId );// 1은 페이지 주인 -1은 페이지 주인 아님
		
		int subscribeState =subscribeRepository.mSubsribeState(principalId, pageUserId);
		int subscribeCount =subscribeRepository.mSubscribeCount(pageUserId);
		int subscribeMeCount =subscribeRepository.mSubscribeMeCount(pageUserId);
		
		dto.setSubscribeState(subscribeState==1);
		dto.setSubscribeCount(subscribeCount);
		dto.setSubscribeMeCount(subscribeMeCount);
		
		// 좋아요 카운트
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
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
	@Transactional
	public User 회원프로필사진변경(Long principalId, MultipartFile profileImageFile) {
		
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_"+profileImageFile.getOriginalFilename();//1.jpg
		System.out.println("이미지 파일 이름 :"+imageFileName);
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		
		//통신,I/O -> 예외가 발생 할 수 있다.
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId);
		
		if (userEntity==null) {
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		}
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
		
	
	}
}
