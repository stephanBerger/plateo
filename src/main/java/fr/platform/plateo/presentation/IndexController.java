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

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private Logger LOGGER;

	@GetMapping("/")
	public String base() {
		this.LOGGER.info("Une requête sur '/' est faite, la page 'index' est envoyée");
		return "public/index";
	}

	/*
	 * @GetMapping( "/public/registration" ) public String registration() {
	 * LOGGER.info(
	 * "Une requête sur '/public/registration' est faite, la page 'registration' est envoyée"
	 * ); return "public/registration"; }
	 */
	@GetMapping("/public/clientRegistration")
	public String clientRegistration() {
		this.LOGGER.info(
				"Une requête sur '/public/clientRegistration' est faite, la page 'clientRegistration' est envoyée");
		return "public/clientRegistration";
	}

	@GetMapping("/public/proRegistration")
	public String proRegistration() {
		this.LOGGER.info(
				"Une requête sur '/public/proRegistration' est faite, la page 'proRegistration' est envoyée");
		return "public/proRegistration";
	}

	@GetMapping("/footer/qui_sommes_nous")
	public String aboutUs() {
		return "public/aboutUs";
	}

	/**
	 *
	 */
	private ProService proServ;

}