package fr.platform.plateo.presentation;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.service.BusinessProcessModelService;
import fr.platform.plateo.business.service.ProService;
import fr.platform.plateo.persistence.ClientRepository;

/**
 *
 */
@Controller
public class IndexController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private Logger LOGGER;

	@Autowired
	private BusinessProcessModelService bpmService;

	@GetMapping("/")
	public String base() {
		this.LOGGER.info(
				"Une requête sur '/' est faite, la page 'index' est envoyée");
		this.LOGGER
				.info("Tâches en cours : " + this.bpmService.getTasks());
		return "public/index";
	}

	@GetMapping("/public/registration")
	public String registration() {
		this.LOGGER.info(
				"Une requête sur '/public/registration' est faite, la page 'registration' est envoyée");
		return "public/registration";
	}

	/**
	 *
	 */
	private ProService proServ;

}