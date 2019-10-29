package fr.platform.plateo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.platform.plateo.entity.Pro;
import fr.platform.plateo.service.ProService;

@Controller
@RequestMapping("/pro")
public class proController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(proController.class);
	
	@Autowired
	private ProService proService;

	@PostMapping("/create")
	public String addPro(@Valid Pro pro, BindingResult result, Model model) throws IOException {

		if (result.hasErrors()) {
			return "pro/proForm";
		}
		BCryptPasswordEncoder decrypt = new BCryptPasswordEncoder(4);
		String password = decrypt.encode(pro.getMot_passe_pro());
		pro.setMot_passe_pro(password);

		this.proService.create(pro);
		LOGGER.debug(pro.getNom_pro()+" "+pro.getEmail_pro());
		return "index";

	}

	@Bean
	public BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@GetMapping("/proForm")
	public String proForm(Pro pro) {
		return "pro/proForm";
	}

	@GetMapping("/dashboardPro")
	public String dashboardPro() {

		return "pro/dashboardPro";
	}

}
