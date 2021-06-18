package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDtails;
import com.cos.photogramstart.service.SubscibeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SubscibeApiController {

	private final SubscibeService subscibeService;
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDtails principalDtails ,@PathVariable Long toUserId){

		subscibeService.구독하기(principalDtails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공",null),HttpStatus.OK);
	}
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDtails principalDtails ,@PathVariable Long toUserId){
		subscibeService.구독취소하기(principalDtails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공",null),HttpStatus.OK);
	}
}
