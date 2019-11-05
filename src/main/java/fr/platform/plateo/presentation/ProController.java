package fr.platform.plateo.presentation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.service.ProService;

@Controller
public class ProController {
	
	@Autowired
	private Logger LOGGER;

	@Autowired
	private ProService proService;
	
	@GetMapping("/public/proForm")
	public String proForm(Pro pro) {
		return "public/proForm";
	}
	
	@PostMapping("/pro/proDashboard")
	public String index() {
		LOGGER.info("La page \"proDashboard\" est demandée");
		return "pro/proDashboard";
	}
	@PostMapping("/public/proForm")
	public String save(@Valid Pro pro, BindingResult result, Model model) {
		if (result.hasErrors()) {
			LOGGER.info("Erreur dans le formulaire");
			System.out.println(result.toString());
			return "public/proForm";
			
		} else if (this.proService.loadUserByUsername(pro.getProEmailAddress())!=null ){
			LOGGER.info("Utilisateur existe déjà");
			this.proService.create(pro);
			return "public/index";
			}
		else {
			LOGGER.info("Creation utlisateur PRO effectué");
			this.proService.create(pro);
			return "public/index";
			}
			
		}
	}
	
