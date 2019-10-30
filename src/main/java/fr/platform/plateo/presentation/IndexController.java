package fr.platform.plateo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.service.ProService;

/**
 * 
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {

		return "index";
	}

	/**
	 * 
	 */
	private ProService proServ;

	/**
	 * 
	 */
	public String proForm() {
		return null;
		// TODO implement here
	}

}