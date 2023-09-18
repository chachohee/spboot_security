package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	//스프링시큐리티가 해당주소를 낚아 챔(스프링시큐리티 화면으로 보여줌) -> SecurityConfig 설정 후에는 아래 코드로 정상 작동.
	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨";
	}
	
}
