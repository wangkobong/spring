package com.kh.spring.common.aop;

import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterThrowingAdviceAspect {
	
	private Logger logger = LoggerFactory.getLogger(AfterThrowingAdviceAspect.class);
	
	// @AfterThrowing : 
	// 타겟 메소드가 예외를 발생되면 호출되는 advice로
	// Exception을 참조할 수 있음.
	
	@AfterThrowing(pointcut="CommonPointcut.implPc()" , throwing = "exceptionObj")
	public void exceptionLog(JoinPoint jp, Exception exceptionObj) {
		
		// 비즈니스 로직 처리 과정에서 예외가 발생한 경우
		// 예외 메세지를 log로 출력하는 공통 코드 작성
		
		String exeptionMsg = null;
		
		if(exceptionObj instanceof IllegalArgumentException) {
			exeptionMsg = "부적합한 값 입력됨.";
		}else if(exceptionObj instanceof SQLException) {
			exeptionMsg = "DB 관련 예외 발생.";
		}else if(exceptionObj instanceof NullPointerException) {
			exeptionMsg = "null을 참조함.";
		}else {
			exeptionMsg = "기타 예외 발생.";
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("[Exception Message] : " + exeptionMsg);
			logger.debug("[Exception Trace [0] : " + exceptionObj.getStackTrace()[0]);
			
		}
	}
}
