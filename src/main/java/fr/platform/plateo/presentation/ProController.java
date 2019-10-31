package fr.platform.plateo.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.persistence.ProRepository;

@Controller
public class ProController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ProController.class);
	@Autowired
	private ProRepository proRep;
	
	@GetMapping("/public/proForm")
	public String proForm(Pro pro) {
		return "public/proForm";
	}
	
	@PostMapping("/pro/proDashboard")
	public String index() {
		LOGGER.info("La page \"proDashboard\" est demandée");
		return "pro/proDashboard";
	}
	
	
}