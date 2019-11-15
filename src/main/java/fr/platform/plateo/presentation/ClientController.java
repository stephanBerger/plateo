package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.Role;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EmailService;
import fr.platform.plateo.business.service.ProService;

/**
 *
 */
@Controller
public class ClientController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ProService    proService;
	
	@Autowired
	private ClientService clientService;

	@Autowired
	private EmailService emailService;

	// login client method get
	/*
	 * @GetMapping("/clients/clientLogin") public String pageLoginClientGet() {
	 * ClientController.LOGGER.info("La page login client est demandée"); return
	 * "/clients/clientLogin"; }
	 */

	@GetMapping("/clients/proList")
	public String listPro(Model model, Principal principal) {
		ClientController.LOGGER.info("La page \"proList\" est demandée");
		List<Pro> listPro = this.proService.readAll();
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);
		model.addAttribute("listPro", listPro);
		// model.addAttribute( "listProProfessions", listProfessions );
		return "clients/proList";
	}

	// changement email client deconnexion obligatoire
	@RequestMapping("/exit")
	public String exit(HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes redirectAttributes) {
		new SecurityContextLogoutHandler().logout(request, null, null);
		try {
			redirectAttributes.addFlashAttribute("msg", "ok");
			return "redirect:/clients/clientLogin";
		} catch (Exception e) {

		}
		return null;
	}

	// dashboard client
	@GetMapping("/clients/clientDashboard")
	public String clientDashboard(Model model, Principal principal) {
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);
		ClientController.LOGGER.info("Authentification ok - redirect sur clientDashboard");
		return "/clients/clientDashboard";
	}

	// nouveau client method get
	@GetMapping("/public/clientForm")
	public String clientForm(Client client) {
		ClientController.LOGGER.info("La page \"clientForm\" est demandée");
		return "/public/clientForm";
	}

	// nouveau client method post - bouton valider du formulaire nouveau client
	@PostMapping("/public/clientForm")
	public String save(@Valid Client client, BindingResult result, Model model,
			@RequestParam(value = "confirmPasswordInput") String confirmPasswordInput) {

		// verifie si l'adresse email est déja dans la BDD
		Client existing = this.clientService.findEmail(client.getClientEmailAddress());
		if (existing != null) {
			result.rejectValue("clientEmailAddress", null, "Cette adresse email est déja utilisée.");
			ClientController.LOGGER.info("Email existe déjà dans la BDD");
			return "/public/clientForm";
		}

		// verifie si les 2 mots de passe pareils
		if (!client.getClientPassword().equals(confirmPasswordInput)) {
			result.rejectValue("clientPassword", null, "Les 2 mots de passes ne correspondent pas");
			ClientController.LOGGER.info("Les 2 mots de passe sont différents");
			return "/public/clientForm";
		}

		if (result.hasErrors()) {
			ClientController.LOGGER.info("Erreur sur la page new_client" + result.toString());
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
		ClientController.LOGGER.info("Le client " + client.getClientFirstname() + " " + client.getClientLastname()
				+ " a été rajouté avec succés - redirect sur la page valid_client");

		// envoi email inscription
		String text = "Bonjour " + client.getClientFirstname() + " " + client.getClientLastname() + ","
				+ "\n\nVotre incription a bien été prise en compte." + "\n\nPLATEO vous remercie de votre confiance.";

		emailService.sendEmail(client.getClientEmailAddress(), "PLATEO - INSCRIPTION", text);
		ClientController.LOGGER.info("Email inscription envoyé");
		return "/clients/clientValid";
	}

	@GetMapping("public/delete/{id}")
	public String delete(@PathVariable Integer id) {
		this.clientService.delete(id);
		return "/public/index";
	}

	// modifier client
	@GetMapping("/clients/clientEdit/{id}")
	public String showUpdateClient(@PathVariable("id") Integer id, Model model) {
		Client client = this.clientService.findId(id)
				.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide"));
		model.addAttribute("client", client);
		ClientController.LOGGER.info("Le client " + client.getClientFirstname() + " " + client.getClientLastname()
				+ " a demander la modification des ses infos");
		return "/clients/clientEdit";
	}

	// bouton modifier du formulaire client
	@PostMapping("/clients/clientEdit/{id}")
	public String updateClient(@RequestParam(value = "OldEmail") String OldEmail, @PathVariable("id") Integer id,
			@Valid Client client, BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

		if (!OldEmail.equals(client.getClientEmailAddress())) {
			// verifie si l'adresse email est déja dans la BDD
			Client existing = this.clientService.findEmail(client.getClientEmailAddress());
			if (existing != null) {
				// result.rejectValue("clientEmailAddress", null, "Cette adresse
				// email est déja
				// utilisée.");
				ClientController.LOGGER.info("Email existe déjà dans la BDD");
				redirectAttributes.addFlashAttribute("msgfail", "fail");
				return "redirect:/clients/clientDashboard";
			}

		}
		if (result.hasErrors()) {
			client.setId(id);
			return "/clients/clientEdit";
		}

		if (client.getId() != null) {
			// enabled a true
			client.setEnabled(true);

			// id du role CLIENT
			Role role = new Role();
			role.setId(2);
			client.setRole(role);

			this.clientService.create(client);
			redirectAttributes.addFlashAttribute("msgok", "ok");
			ClientController.LOGGER.info("Le client " + client.getClientFirstname() + " " + client.getClientLastname()
					+ " a modifié sa fiche avec succés");

			if (!OldEmail.equals(client.getClientEmailAddress())) {
				ClientController.LOGGER.info("Le client " + client.getClientFirstname() + " "
						+ client.getClientLastname() + " a modifié son email - deconnexion obligatoire");

				return "redirect:/exit";
			}
		}
		return "redirect:/clients/clientDashboard";
	}

	// modification du mot de passe client method post
	@PostMapping("/clients/clientEditPassword")
	public String clientEditPasswordPost(Client client, BindingResult result, Model model,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "confirmpassword") String confirmpassword,
			final RedirectAttributes redirectAttributes) {

		Integer id = client.getId();

		if (!password.equals(confirmpassword)) {
			model.addAttribute("msg", "fail");
			model.addAttribute("id", id);
			return "/clients/clientEdit";
		}

		if (result.hasErrors()) {
			model.addAttribute("msg", "fail");
			model.addAttribute("id", id);
			return "/clients/clientEdit";
		}

		if (client.getId() != null) {
			Client client2 = this.clientService.findId(client.getId())
					.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide"));
			// si tout est ok on modifie le mot de passe
			BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(4);
			String cryptpassword = crypt.encode(password);
			client2.setClientPassword(cryptpassword);
			ClientController.LOGGER.info("Cryptage du mot de passe OK");

			this.clientService.create(client2);
			ClientController.LOGGER.info("Le client " + client2.getClientFirstname() + " " + client2.getClientLastname()
					+ " a modifié son mot de passe avec succés");
			model.addAttribute("client", client);
			redirectAttributes.addFlashAttribute("msgok", "ok");

		}
		return "redirect:/clients/clientEdit/" + id;

	}

	/*
	 * // Test d'affichage de la liste des Clients
	 *
	 * @GetMapping("public/test") public String test() { List<Client> list =
	 * this.clientService.listClients(); for(Client client: list)
	 * System.out.println(client.getClientEmailAddress()); return "public/index"; }
	 */

}
