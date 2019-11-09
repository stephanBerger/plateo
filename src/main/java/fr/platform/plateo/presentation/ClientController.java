package fr.platform.plateo.presentation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	// login client method get
	@GetMapping("/clients/clientLogin")
	public String pageLoginClientGet() {
		ClientController.LOGGER.info("La page login client est demandée");
		return "/clients/clientLogin";
	}

	// dashboard client
	@GetMapping("/clients/clientDashboard")
	public String clientDashboard() {
		ClientController.LOGGER.info(
				"Authentification ok - redirect sur clientDashboard");
		return "/clients/clientDashboard";
	}

	// nouveau client method get
	@GetMapping("/public/clientForm")
	public String clientForm(Client client) {
		ClientController.LOGGER
				.info("La page \"clientForm\" est demandée");
		return "/public/clientForm";
	}

	// nouveau client method post - bouton valider du formulaire nouveau client
	@PostMapping("/public/clientForm")
	public String save(@Valid Client client, BindingResult result,
			Model model, @RequestParam(
					value = "confirmPasswordInput") String confirmPasswordInput) {

		// verifie si l'adresse email est déja dans la BDD
		Client existing = this.clientService
				.findEmail(client.getClientEmailAddress());
		if (existing != null) {
			result.rejectValue("clientEmailAddress", null,
					"Cette adresse email est déja utilisée.");
			ClientController.LOGGER.info("Email existe déjà dans la BDD");
			return "/public/clientForm";
		}

		// verifie si les 2 mots de passe pareils
		if (!client.getClientPassword().equals(confirmPasswordInput)) {
			result.rejectValue("clientPassword", null,
					"Les 2 mots de passes ne correspondent pas");
			ClientController.LOGGER
					.info("Les 2 mots de passe sont différents");
			return "/public/clientForm";
		}

		if (result.hasErrors()) {
			ClientController.LOGGER.info(
					"Erreur sur la page new_client" + result.toString());
			return "/public/clientForm";
		}

		// si tout est ok on rajoute le client et redirect sur valid client
		BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(4);
		String password = crypt.encode(client.getClientPassword());
		client.setClientPassword(password);
		ClientController.LOGGER.info("Cryptage du mot de passe OK");

		// enabled a true
		client.setEnabled(true);

		// id du role CLIENT
		Role role = new Role();
		role.setId(2);
		client.setRole(role);

		this.clientService.create(client);
		ClientController.LOGGER.info("Le client "
				+ client.getClientFirstname() + " "
				+ client.getClientLastname()
				+ " a été rajouté avec succés - redirect sur la page valid_client");
		return "/clients/clientValid";
	}

	@GetMapping("public/delete/{id}")
	public String delete(@PathVariable Integer id) {
		this.clientService.delete(id);
		return "/public/index";
	}

	// modifier client
	@GetMapping("/clients/clientEdit")
	public String editClient(@PathVariable Integer id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String email = authentication.getName();
		Client client = this.clientService.findEmail(email);

		Integer Id = client.getId();
		Client client2 = this.clientService.findId(client.getId())
				.orElseThrow(() -> new IllegalArgumentException(
						"L' Id du particulier est invalide"));
		model.addAttribute("client", client2);
		System.out.println(client2.getId());
		System.out.println(Id);
		return "clients/clientEdit";
	}

	/*
	 * // Test d'affichage de la liste des Clients
	 *
	 * @GetMapping("public/test") public String test() { List<Client> list =
	 * this.clientService.listClients(); for(Client client: list)
	 * System.out.println(client.getClientEmailAddress()); return "public/index"; }
	 */

}
