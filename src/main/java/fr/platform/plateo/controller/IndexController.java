package fr.platform.plateo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {

		return "index";
	}

//	@GetMapping("/pro/proForm")
//	public String proForm() {
//		
//		return "/pro/proForm";
//	}

}
