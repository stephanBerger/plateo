package fr.platform.plateo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class proController {
	@GetMapping("/pro/proForm")
	public String proForm() {
		return "/pro/proForm";
	}
	
	@GetMapping("/pro/dashboardPro")
	public String dashboardPro() {
		
		return "/pro/dashboardPro";
	}

}
