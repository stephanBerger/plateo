package fr.platform.plateo.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.service.ProService;
import fr.platform.plateo.persistence.ClientRepository;

/**
 * 
 */
@Controller
public class IndexController {
	
	@Autowired
	private ClientRepository clientRepository;

	@GetMapping("/")
	public String index() {

		return "public/index";
	}

	@GetMapping("/registration")
	public String registration() {
		return "public/registration";
	}
	
	@GetMapping("/new_client")
	public String newClient(Client client) {
		
		return "public/new_client";
	}
	
	@GetMapping("/proForm")
	public String proForm() {
		return "public/proForm";
	}


	private ProService proServ;

	


}