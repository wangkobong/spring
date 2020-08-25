package com.kh.spring.common.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)	// advice 실행 순서를 지정. 값이 클수록 먼저 수행
			// @Order 어노테이션이 없으면 우선순위가 가장 낮음
			// @Around가 Aspect의 @Order값이 가장 큰 경우
			// Before Advice가 가장 먼저, After Advice 가장 마지막에 수행됨.
public class AroundAdviceAspect {
	
	private Logger logger = LoggerFactory.getLogger(AroundAdviceAspect.class);
	
	// @Around
	// - 전처리(@Before), 후처리(@After)를 한번에 처리할 수 있는 Advice
	// ** @Around로 지정된 Advice는 매개변수로 JoinPoint를 가질 수 없음
	// -> JoinPoint 하위 타입인 ProceedingJoinPoint 사용 가능
						// Proceed : 발화 , 발하다 , 발생하다 / Proceeding : 진행, 처리
	// ProceedingJoinPoint.proceed() 메소드
	// proceed() 메소드 호출 전 : @Before advice에 해당 되는 코드
	// proceed() 메소드 호출 후 : @After advice에 해당 되는 코드
	// 메소드 마지막에 proceed()의 반환값을 return 해야함.
	
	@Around("CommonPointcut.implPc()")
	public Object arroundLogs(ProceedingJoinPoint pp) throws Throwable {
		// @Before
		// 클래스명
		String className = pp.getTarget().getClass().getSimpleName();
		
		// 메소드명
		String methodName = pp.getSignature().getName();
		
		if(logger.isDebugEnabled()) {
			logger.debug("---------------------------------------------------------------------");
			
			logger.debug("[start]  : " + className + " = " + methodName + "()");
			logger.debug("[Parameter] : " + Arrays.toString(pp.getArgs()));
				// jp.getArgs() : 대상 메소드의 매개변수를 배열로 반환
		}
		
		long startMs = System.currentTimeMillis(); // 서비스 시작 시 ms 값
		//System.currentTimeMillis() : 기준시간(한국 OS경우 1970년 1월 1일 오전 9시)을 부터
		//								현재 시간까지의 시간을 ms단위(1/1000초) 단위로 계산한 값을 반환
		
		
		// proceed() 메소드 호출
		Object obj = pp.proceed();
		
		long endMs = System.currentTimeMillis();
		
		// @After
		if(logger.isDebugEnabled()) {
			
			logger.debug("[Running Time] : " + (endMs - startMs) + "ms");
			logger.debug("[End] : " + className + " - " + methodName + "()");
			
			logger.debug("---------------------------------------------------------------------");
			
		}
		
		
		return obj;
	}

	
}
