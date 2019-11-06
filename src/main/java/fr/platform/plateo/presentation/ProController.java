package fr.platform.plateo.presentation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.Role;
import fr.platform.plateo.business.service.ProService;

@Controller
public class ProController {

	@Autowired
	private Logger LOGGER;

	@Autowired
	private ProService proService;

	// login pro method get
	@GetMapping("/pro/proLogin")
	public String pageLoginProGet() {
		LOGGER.info("La page \"proLogin\" est demandée");
		return "/pro/proLogin";
	}

	// dashboard pro
	@GetMapping("/pro/proDashboard")
	public String proDashboard() {
		LOGGER.info("La page \"proDashboard\" est demandée");
		return "/pro/proDashboard";
	}

	// nouveau pro method get
	@GetMapping("/public/proForm")
	public String proForm(Pro pro) {
		LOGGER.info("La page \"proForm\" est demandée");
		return "/public/proForm";
	}

	@PostMapping("/public/proForm")
	public String save(@Valid Pro pro, BindingResult result,
			@RequestParam(value = "confirmProPassword") String confirmPasswordInput) {
		if (result.hasErrors()) {
			LOGGER.info("Erreur dans le formulaire" + pro.getCompanyName());
			System.out.println(result.toString());
			return null;

		} else if (this.proService.loadUserByUsername(pro.getProEmailAddress()) != null) {
			LOGGER.info("Utilisateur existe déjà ");
			result.rejectValue("proEmailAddress", null, "Cette adresse email est déjà utilisée.");
			return null;

		} else if (!confirmPasswordInput.equals(pro.getProPassword())) {
			LOGGER.info("Les 2 passwords ne sont pas identiques " + confirmPasswordInput.toString() + " "
					+ pro.getProPassword());
			result.rejectValue("proPassword", null, "Les passwords ne sont pas identiques");
			return null;
			// Test de la longueur du SIRET et test sur la validité du siren
		} else if (pro.getSiret().length() != 14) {
			result.rejectValue("siret", null, "Le Siret doit contenir 14 chiffres.");
			return null;

		} else if (pro.getSiret() != null) {

			String[] siren = pro.getSiret().substring(0, 9).split("");
			int somme = 0;
			int resultat = 0;
			

			for (int i = 1; i <= siren.length; i++) {
				if (i % 2 != 0) {
					somme = Integer.parseInt(siren[i - 1]);
				} else {
					somme = 2 * Integer.parseInt(siren[i - 1]);
					if (somme > 10) {
						somme = somme % 10 + somme / 10;
					}
				}
				resultat += somme;
			}

			if (resultat % 10 != 0) {
				LOGGER.info("Le Siret n'est pas valide");
				result.rejectValue("siret", null, "Le Siret n'est pas valide.");
				return null;
			} else {

				// si ok rajoute le client et redirect sur valid client
				BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(4);
				String password = crypt.encode(pro.getProPassword());
				pro.setProPassword(password);

				// enabled a true
				pro.setEnabled(true);

				// id du role PRO
				Role role = new Role();
				role.setId(1);
				pro.setRole(role);

				LOGGER.info("Creation utlisateur PRO effectué");
				this.proService.create(pro);
				return "public/index";
			}

		}
		return null;

	}

}
