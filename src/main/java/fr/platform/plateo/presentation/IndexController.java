package fr.platform.plateo.presentation;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.service.ProService;
import fr.platform.plateo.persistence.ClientRepository;

/**
 * 
 */
@Controller
public class IndexController {

	/**
	 * 
	 */
	private ProService proServ;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private Logger LOGGER;

	@GetMapping("/")
	public String base() {
		LOGGER.info("Une requête sur '/' est faite, la page 'index' est envoyée");
		return "public/index";
	}

}