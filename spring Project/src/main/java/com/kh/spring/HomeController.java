package com.kh.spring;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // 프레젠테이션 레이어, 웹 앱 View에서 전달받은 요청, 전달할 응답을 처리하는 클래스 라는 걸 명시 Bean 등록
public class HomeController {
	
	/*
	 * @RequestMapping : 요청을 매핑할 주소와 전달 방식을 작성하는 어노테이션
	 * - 클래스 레벨, 메소드 레벨 두가지로 작성할 수 있다.
	 * 
	 *  value : 매핑할 요청 주소
	 *  method : 요청방식을 지정
	 *  
	 *   @RequestMapping 주소로 최상위 경로("/")를 작성하는 경우
	 *   	-> 메인페이지에 응용 데이터가 필요한 경우에 사용함.
	 */
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		// Spring MVC Project의 컨트롤러 메소드는 반환형이 존재함.
		
		// 반환형이 String인 경우 반환값으로 forward할 파일명 또는 요청 주소를 작성함.
			// 반환되는 값을 View Name 이라고 함.
		
		// View Name 을 작성하는 방법
		// 1) forward 방법 : jsp 파일경로 작성
			// View Resolver에서 prefix / suffix 값이 앞 뒤로 붙어서
			// 경로의 형태를 띄게 됨.
		
		// 2) redirect 방법
		// View Name에 요청주소를 적되
		// 주소 앞에 redirect: 접두사를 붙임
		
		// return "redirect: /";
		
		// .. : 상위
		// .  : 현재
		// xxx/  : 하위
		
		return "../../main";
	}
	
}
