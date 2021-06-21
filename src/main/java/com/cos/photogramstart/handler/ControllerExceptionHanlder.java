package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
@RestController
@ControllerAdvice // 얘가 있으면 모든 입셉션을 감지
public class ControllerExceptionHanlder {

	@ExceptionHandler(CustomValidationException.class)//  CMRespDto<?> -- 여기서 ?를 넣으면 추론해서 진행해줌
	public String vlidationException(CustomValidationException e) {
		//CMRespDto , Script 비교
		//1. 클라이언트에게 응답할 때는 Script가 좋음
		//2. Ajax통신 - CMRespDto
		//3. Android 통신 - CMRespDto
		if(e.getErrorMap()==null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
		
		
	}
	
	@ExceptionHandler(CustomValidationApiException.class)//  CMRespDto<?> -- 여기서 ?를 넣으면 추론해서 진행해줌
	public ResponseEntity<?>  vlidationApiException(CustomValidationApiException e) {
		
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler(CustomApiException.class)//  CMRespDto<?> -- 여기서 ?를 넣으면 추론해서 진행해줌
	public ResponseEntity<?>  apiException(CustomApiException e) {
		
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
	
	
	
}
