package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // restcontroller, service 기타등등은 component를 상속해서 만들어졌다
@Aspect
public class ValidationAdvice {
	
	//com.cos.photogramstart.web.*Controller.*(..)) --api 패키지 안에 있는 모든 컨트롤러 모든 메소드 들 
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")// * -- 여기는 public private 이런게 들어가는부분
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web api 컨트롤러======================");
		//proceedingJoinPoint=> profile 함수의 모든 곳에 접근할 수 있는 변수
		// profile 함수보다 먼저 실행
		return proceedingJoinPoint.proceed();// profile 함수가 실행. 이부분은 null 넣으면 profile함수가 실행이 안됨!!!!
	}
	
	//com.cos.photogramstart.web.*Controller.*(..)) --web 패키지 안에 있는 모든 컨트롤러 모든 메소드 들 
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")// * -- 여기는 public private 이런게 들어가는부분
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		
		System.out.println("web  컨트롤러======================");
		return proceedingJoinPoint.proceed();
	}

}
