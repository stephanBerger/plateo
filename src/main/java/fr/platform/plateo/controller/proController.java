package fr.platform.plateo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pro")
public class proController {
	
	@GetMapping("/proForm")
	public String proForm() {
		return "pro/proForm";
	}
	
	@GetMapping("/dashboardPro")
	public String dashboardPro() {
		
		return "dashboardPro";
	}

}
