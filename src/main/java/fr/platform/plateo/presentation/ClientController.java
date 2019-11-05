package fr.platform.plateo.presentation;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.service.ClientService;

/**
 * 
 */
@Controller
public class ClientController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@GetMapping("/public/new_client")
	public String newClient(Client client) {
		LOGGER.info("La page \"new_client\" est demandée");
		return "public/new_client";
	}

	@PostMapping("public/new_client")
	public String save(@Valid Client client, BindingResult result) {
		if (result.hasErrors()) {
			return "form";
		} else {
			clientService.create(client);
			return "public/index";
		}
	}
	
	@GetMapping("public/delete/{id}")
	public String delete(@PathVariable Integer id) {
		this.clientService.delete(id);
		return "public/index";
	}
	
	//Test d'affichage de la liste des Clients
	/*@GetMapping("public/test")
	public String test() {
		List<Client> list = this.clientService.listClients();
		for(Client client: list) System.out.println(client.getClientEmailAddress());
		return "public/index";
	}*/
	
}
