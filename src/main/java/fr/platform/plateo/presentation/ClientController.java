package fr.platform.plateo.presentation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Role;
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

	/* A SUPPRIMER */
	@GetMapping("/public/clientDashboard")
	public String clientDashboard(Client client) {
		return "public/clientDashboard";
	}
	/*  Fin de A SUPPRIMER*/
	

	// bouton valider du formulaire nouveau client
	@PostMapping("public/new_client")
	public String save(@Valid Client client, BindingResult result,Model model,@RequestParam(value = "confirmPasswordInput") String confirmPasswordInput) {
			
			// verifie si l'adresse email est déja dans la BDD
			Client existing = this.clientService.findEmail(client.getClientEmailAddress());
			if (existing != null) {
				result.rejectValue("clientEmailAddress", null, "Cette adresse email est déja utilisée.");
				return "public/new_client";
			}

			if (result.hasErrors()) {
				return "public/new_client";
			}

			// verifie si les 2 mots de passe pareils
			if (!(client.getClientPassword().equals(confirmPasswordInput))) {
				result.rejectValue("clientPassword", null, "Les 2 mots de passes ne correspondent pas");
				return "public/new_client";
			}

			// si ok rajoute le client et redirect sur valid client
			BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(4);
			String password = crypt.encode(client.getClientPassword());
			client.setClientPassword(password);

			// enabled a true
			client.setEnabled(true);

			// id du role CLIENT
			Role role = new Role();
			role.setId(2);
			client.setRole(role);
			
			clientService.create(client);
			return "public/index";
		}

	@GetMapping("public/delete/{id}")
	public String delete(@PathVariable Integer id) {
		this.clientService.delete(id);
		return "public/index";
	}

	// Test d'affichage de la liste des Clients
	/*
	 * @GetMapping("public/test") public String test() { List<Client> list =
	 * this.clientService.listClients(); for(Client client: list)
	 * System.out.println(client.getClientEmailAddress()); return "public/index"; }
	 */

}
