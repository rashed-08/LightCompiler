package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/")
	public String showHome(final Model model) {
		model.addAttribute("title", "Spring Boot with Docker");
		model.addAttribute("message", "Welcome to Docker container!");
		return "index";
	}
	
	@RequestMapping("/welcome")
	public String showMessage() {
		return "This is a welcome message!!!";
	}
}
