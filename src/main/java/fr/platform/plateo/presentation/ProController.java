package fr.platform.plateo.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.persistence.ProRepository;

@Controller
public class ProController {
	
	@Autowired
	private ProRepository proRep;
	
	@GetMapping("/public/proForm")
	public String proForm(Pro pro) {
		return "public/proForm";
	}
	
	
}