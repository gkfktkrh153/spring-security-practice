package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}

	@GetMapping("/common")
	public @ResponseBody String common() {
		return "common";
	}
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	//@Secured("ROLE_ADMIN")
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	//@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	@PostMapping("/login")
	public @ResponseBody String login() {
		System.out.println("login");
		return "login";
	}


	@GetMapping("/login-form")
	public String loginForm() {
		System.out.println("login-form");
		return "login-form";
	}


	@PostMapping("/join")
	public @ResponseBody String join(User user) {
		System.out.println(user);
		System.out.println("join");
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "join";
	}


	@GetMapping("/join-form")
	public String joinForm() {
		System.out.println("join-form");
		return "join-form";
	}
	@GetMapping("/main")
	public String main() {
		return "main";
	}
}
