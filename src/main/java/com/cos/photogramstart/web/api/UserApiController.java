package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDtails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
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
}
