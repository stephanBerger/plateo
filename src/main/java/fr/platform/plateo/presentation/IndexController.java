package fr.platform.plateo.presentation;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
public class IndexController {

	/**
	 *
	 */

	@Autowired
	private Logger LOGGER;

	@GetMapping({ "/", "/clients/login", "/pro/login" })
	public String base() {
		this.LOGGER.info(
				"Une requête sur '/' est faite, la page 'index' est envoyée");
		return "public/index";
	}

	@RequestMapping("/403")
	public String accessDenied() {
	    return "/errors/403";
	}

	@GetMapping("/footer/qui_sommes_nous")
	public String aboutUs() {
		return "public/aboutUs";
	}

}