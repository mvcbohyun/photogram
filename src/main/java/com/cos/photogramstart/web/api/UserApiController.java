package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDtails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> prifileImageUrlUpdate(@PathVariable Long principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDtails principalDtails){// input - name 값으로 받아야함 profileImageFile
		User userEntity = userService.회원프로필사진변경(principalId,profileImageFile);
		principalDtails.setUser(userEntity);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"프로필 사진변경 성공",null),HttpStatus.OK);
	}
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable Long id , 
			@Valid UserUpdateDto userUpdateDto,
			BindingResult result,// 꼭 @Valid가 적혀 있는 다음 파라메터에 적어야함
			@AuthenticationPrincipal PrincipalDtails principalDtails) {
		
	if(result.hasErrors()) {
			
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			
			throw new CustomValidationApiException("유효성 검사 실패", errorMap);
		}else {
	
		System.out.println(userUpdateDto);

		User userEntity =userService.회원수정(id, userUpdateDto.toEntity());
		principalDtails.setUser(userEntity);// 세션정보 변경
		return new  CMRespDto<>(1,"회원 수정 완료",userEntity);// 응답시에 userEntity 의 모든 getter 함수가 호출되고 JSON 으로 파싱하여 응답한다
		}
	
	}
	
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable Long pageUserId , @AuthenticationPrincipal PrincipalDtails principalDtails){
		List<SubscribeDto> subscribeDto = subscribeService.구독한리스트(principalDtails.getUser().getId() , pageUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독한 정보 불러오기 성공",subscribeDto),HttpStatus.OK);
	}
	
	@GetMapping("/api/user/{pageUserId}/subscribe2")
	public ResponseEntity<?> subscribe2List(@PathVariable Long pageUserId , @AuthenticationPrincipal PrincipalDtails principalDtails){
		List<SubscribeDto> subscribeDto = subscribeService.구독자리스트(principalDtails.getUser().getId() , pageUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 불러오기 성공",subscribeDto),HttpStatus.OK);
	}
}
