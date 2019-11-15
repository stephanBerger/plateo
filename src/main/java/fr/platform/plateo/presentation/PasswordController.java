package fr.platform.plateo.presentation;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.ResetPasswordClient;
import fr.platform.plateo.business.entity.ResetPasswordPro;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EmailService;
import fr.platform.plateo.business.service.ProService;
import fr.platform.plateo.persistence.ResetPasswordClientRepository;
import fr.platform.plateo.persistence.ResetPasswordProRepository;

@Controller
public class PasswordController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProService proService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ResetPasswordClientRepository resetRepository;

	@Autowired
	private ResetPasswordProRepository resetProRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// formulaire forgot password CLIENT method get
	@GetMapping("/password/clientForgotPassword")
	public String ForgotPassword(Model model) {
		model.addAttribute("condition1IsTrue", Boolean.TRUE);
		return "/password/clientForgotPassword";
	}

	// formulaire forgot password CLIENT method post
	@PostMapping("/password/clientForgotPassword")
	public String ValidForgotPassword(Model model, HttpServletRequest request, 
			@RequestParam(value = "email") String email, final RedirectAttributes redirectAttributes) {

		// verif si email existe dans BDD
		Client client = this.clientService.findEmail(email);
		if (client == null) {
			PasswordController.LOGGER.info("Adresse email inconnue");
			redirectAttributes.addFlashAttribute("msg", "fail");
			return "redirect:/password/clientForgotPassword";
		}

		// si email dans BDD save token
		ResetPasswordClient token = new ResetPasswordClient();
		token.setToken(UUID.randomUUID().toString());
		token.setClient(client);
		token.setExpiryDate(30);
		this.resetRepository.save(token);

		// creation de URL reset password
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String url2 = url + "/password/clientResetPassword?token=" + token.getToken();
		PasswordController.LOGGER.info("URL reset password : " + url2);

		// envoi email réinitialisation du password
		String text = "Bonjour " + client.getClientFirstname() + " " + client.getClientLastname() + ","
				+ "\n\nVeuiller cliquez sur le lien ci dessous pour la réinitialisation de votre mot de passe" + "\n\n"
				+ url2 + "\n\nPLATEO vous remercie de votre confiance.";

		this.emailService.sendEmail(client.getClientEmailAddress(), "PLATEO - REINITIALISATION DU MOT DE PASSE", text);

		PasswordController.LOGGER.info("Email envoyé au client pour réinitialisation du password");

		model.addAttribute("condition1IsTrue", Boolean.FALSE);
		return "/password/clientForgotPassword";

	}

	// reset du password CLIENT method get lorsquil click sur le lien recu par mail
	@GetMapping("/password/clientResetPassword")
	public String ResetPassword(@RequestParam(required = false) String token, Model model) {

		ResetPasswordClient resetToken = this.resetRepository.findByToken(token);

		// verifie si token existe
		if (resetToken == null) {
			model.addAttribute("msgnotoken", "ok");
			model.addAttribute("condition1IsTrue", Boolean.FALSE);
			return "/password/clientResetPassword";

			// verifie si token encore valide sur la durée de 30 minutes
		} else if (resetToken.isExpired()) {
			model.addAttribute("msgtokenexp", "ok");
			model.addAttribute("condition1IsTrue", Boolean.FALSE);
			return "/password/clientResetPassword";
		} else {

			// si token ok (existe et encore valide)
			Integer id = resetToken.getClient().getId();

			Client client = this.clientService.findId(id)
					.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide n° : " + id));
			PasswordController.LOGGER.info("Demande de réinitialisation du password par le client : "
					+ client.getClientFirstname() + " " + client.getClientLastname());
			model.addAttribute("client", client);
			model.addAttribute("condition1IsTrue", Boolean.TRUE);
		}

		return "/password/clientResetPassword";
	}

	// reset du password CLIENT method post lorsquil click sur le lien recu par mail
	@PostMapping("/password/clientvalidresetpassword")
	public String ValidResetPassword(Client client, BindingResult result,Model model, @RequestParam("id") Integer id,
			@RequestParam("clientPassword") String clientPassword,
			@RequestParam("confirmPasswordInput") String confirmPasswordInput) {

		// verifie si les 2 mots de passe pareils
		if (!clientPassword.equals(confirmPasswordInput)) {
			model.addAttribute("msg", "fail");
			model.addAttribute("id", id);
			model.addAttribute("condition1IsTrue", Boolean.TRUE);

			PasswordController.LOGGER.info("Les 2 mots de passe sont différents");
			return "/password/clientResetPassword";
		}

		Client client2 = this.clientService.findId(id)
				.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide n° : " + id));

		String cryptPassword = this.passwordEncoder.encode(clientPassword);
		client2.setClientPassword(cryptPassword);
		this.clientService.create(client2);
		PasswordController.LOGGER.info("Le mot de passe à bien été modifié");
		model.addAttribute("msg", "ok");
		return "/password/clientResetPassword";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	// formulaire forgot password PRO method get
	@GetMapping("/password/proForgotPassword")
	public String proForgotPassword(Model model) {
		model.addAttribute("condition1IsTrue", Boolean.TRUE);
		return "/password/proForgotPassword";
	}

	// formulaire forgot password PRO method post
	@PostMapping("/password/proForgotPassword")
	public String proValidForgotPassword(Model model, HttpServletRequest request, @RequestParam(value = "email") String email,
			final RedirectAttributes redirectAttributes) {

		// verif si email existe dans BDD
		Pro pro = this.proService.findEmail(email);

		if (pro == null) {
			PasswordController.LOGGER.info("Adresse email inconnue");
			redirectAttributes.addFlashAttribute("msg", "fail");
			return "redirect:/password/proForgotPassword";
		}

		// si email dans BDD save token
		ResetPasswordPro token = new ResetPasswordPro();
		token.setToken(UUID.randomUUID().toString());
		token.setPro(pro);
		token.setExpiryDate(30);
		this.resetProRepository.save(token);

		// creation de URL reset password
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String url2 = url + "/password/proResetPassword?token=" + token.getToken();
		PasswordController.LOGGER.info("URL reset password : " + url2);

		// envoi email réinitialisation du password
		String text = "Bonjour " + pro.getManagerFirstname() + " " + pro.getManagerLastname() + ","
				+ "\n\nVeuiller cliquez sur le lien ci dessous pour la réinitialisation de votre mot de passe" + "\n\n"
				+ url2 + "\n\nPLATEO vous remercie de votre confiance.";

		this.emailService.sendEmail(pro.getProEmailAddress(), "PLATEO - REINITIALISATION DU MOT DE PASSE", text);

		PasswordController.LOGGER.info("Email envoyé au pro pour réinitialisation du mot de passe");

		model.addAttribute("condition1IsTrue", Boolean.FALSE);
		return "/password/proForgotPassword";

	}

	// formulaire reset password PRO method get
	@GetMapping("/password/proResetPassword")
	public String proResetPassword(@RequestParam(required = false) String token, Model model) {
		System.out.println(token);
		ResetPasswordPro resetToken = this.resetProRepository.findByToken(token);
		
		// verifie si token existe
		if (resetToken == null) {
			model.addAttribute("msgnotoken", "ok");
			model.addAttribute("condition1IsTrue", Boolean.FALSE);
			return "/password/proResetPassword";

			// verifie si token encore valide sur la durée de 30 minutes
		} else if (resetToken.isExpired()) {
			model.addAttribute("msgtokenexp", "ok");
			model.addAttribute("condition1IsTrue", Boolean.FALSE);
			return "/password/proResetPassword";
		} else {

			// si token ok (existe et encore valide)
			Integer id = resetToken.getPro().getId();

			Pro pro = this.proService.findId(id)
					.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide n° : " + id));
			PasswordController.LOGGER.info("Demande de réinitialisation du password par le pro : "
					+ pro.getManagerFirstname() + " " + pro.getManagerLastname());
			model.addAttribute("pro", pro);
			model.addAttribute("condition1IsTrue", Boolean.TRUE);
		}

		return "/password/proResetPassword";
	}

	// formulaire reset password PRO method post
	@PostMapping("/password/proValidResetPassword")
	public String proValidResetPassword(Pro pro, BindingResult result,Model model,@RequestParam("id") Integer id, @RequestParam("proPassword") String proPassword,
			@RequestParam("confirmPasswordInput") String confirmPasswordInput) {

		// verifie si les 2 mots de passe pareils
		if (!proPassword.equals(confirmPasswordInput)) {
			model.addAttribute("msg", "fail");
			model.addAttribute("id", id);
			model.addAttribute("condition1IsTrue", Boolean.TRUE);

			PasswordController.LOGGER.info("Les 2 mots de passe sont différents");
			return "/password/proResetPassword";
		}

		Pro pro2 = this.proService.findId(id)
				.orElseThrow(() -> new IllegalArgumentException("L' Id du pro est invalide n° : " + id));

		String cryptPassword = this.passwordEncoder.encode(proPassword);
		pro2.setProPassword(cryptPassword);
		this.proService.create(pro2);
		PasswordController.LOGGER.info("Le mot de passe à bien été modifié");
		model.addAttribute("msg", "ok");
		return "/password/proResetPassword";
	}

}
