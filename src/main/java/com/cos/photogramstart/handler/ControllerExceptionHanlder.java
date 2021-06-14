package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

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
		
		return Script.back(e.getErrorMap().toString());
	}
	
	
	
}
