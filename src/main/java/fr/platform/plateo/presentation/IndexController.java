package fr.platform.plateo.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.service.ProService;

/**
 * 
 */
@Controller
public class IndexController {

	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@GetMapping("/")
	public String index() {
		LOGGER.info("La page \"index\" est demandée");
		return "public/index";
	}

	@GetMapping("/public/registration")
	public String registration() {
		LOGGER.info("La page \"registration\" est demandée");
		return "public/registration";
	}
	
	
	/**
	 * 
	 */
	private ProService proServ;

}