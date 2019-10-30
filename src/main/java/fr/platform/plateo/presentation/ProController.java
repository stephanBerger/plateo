package fr.platform.plateo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProController {
	
	
	
	@GetMapping("/public/proForm")
	public String proForm()	{
		return "public/proForm";
	}
	
	@GetMapping("/pro/dashboard")
	public String dahsbord() {
		
		return "/pro/dashboard";
	}
	
}