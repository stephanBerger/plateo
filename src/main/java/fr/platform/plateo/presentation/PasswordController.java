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
import fr.platform.plateo.business.entity.ResetPassword;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EmailService;
import fr.platform.plateo.persistence.ResetPasswordRepository;

@Controller
public class PasswordController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ResetPasswordRepository resetRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// formulaire forgot password method get
	@GetMapping("/password/clientForgotPassword")
	public String ForgotPassword(Model model) {
		return "/password/clientForgotPassword";
	}

	// formulaire forgot password method post
	@PostMapping("/password/clientForgotPassword")
	public String ValidForgotPassword(HttpServletRequest request, @RequestParam(value = "email") String email,
			final RedirectAttributes redirectAttributes) {

		// verif si email existe dans BDD
		Client client = clientService.findEmail(email);
		if (client == null) {
			PasswordController.LOGGER.info("Adresse email inconnu");
			redirectAttributes.addFlashAttribute("msg", "fail");
			return "redirect:/password/clientForgotPassword";
		}

		// si email dans BDD save token
		ResetPassword token = new ResetPassword();
		token.setToken(UUID.randomUUID().toString());
		token.setParticulier(client);
		token.setExpiryDate(30);
		resetRepository.save(token);

		// creation de URL reset password
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String url2 = url + "/password/clientResetPassword?token=" + token.getToken();
		PasswordController.LOGGER.info("URL reset password : " + url2);

		// envoi email réinitialisation du password
		String text = "Bonjour " + client.getClientFirstname() + " " + client.getClientLastname() + ","
				+ "\n\nVeuiller cliquez sur le lien ci dessous pour la réinitialisation de votre mot de passe" + "\n\n"
				+ url2 + "\n\nPLATEO vous remercie de votre confiance.";

		emailService.sendEmail(client.getClientEmailAddress(), "PLATEO - REINITIALISATION DU MOT DE PASSE", text);

		PasswordController.LOGGER.info("Email envoyé au client pour réinitialisation du password");

		redirectAttributes.addFlashAttribute("msg", "ok");
		return "redirect:/password/clientForgotPassword";

	}

	// reset du password method get lorsquil click sur le lien recu par mail
	@GetMapping("/password/clientResetPassword")
	public String ResetPassword(@RequestParam(required = false) String token, Model model) {

		ResetPassword resetToken = resetRepository.findByToken(token);

		// verifie si token existe
		if (resetToken == null) {
			System.out.println("Impossible de trouvez le TOKEN ...");

			// verifie si token encore valide sur la durée de 30 minutes
		} else if (resetToken.isExpired()) {
			System.out.println("TOKEN expiré, refaite  un reset du mot de passe");
		} else {

			// si token ok (existe et encore valide)
			Integer id = resetToken.getClient().getId();

			Client client = clientService.findId(id)
					.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide n° : " + id));
			PasswordController.LOGGER.info("Demande de réinitialisation du password par le client : "
					+ client.getClientFirstname() + " " + client.getClientLastname());
			model.addAttribute("client", client);
		}

		return "/password/clientResetPassword";
	}

	@PostMapping("/password/clientvalidresetpassword")
	public String ValidResetPassword(@RequestParam("id") Integer id,
			@RequestParam("clientPassword") String clientPassword,
			@RequestParam("confirmPasswordInput") String confirmPasswordInput, 
			final RedirectAttributes redirectAttributes) {

		// verifie si les 2 mots de passe pareils
		if (!clientPassword.equals(confirmPasswordInput)) {
			redirectAttributes.addFlashAttribute("msg", "fail");
			PasswordController.LOGGER.info("Les 2 mots de passe sont différents");
			return "/password/clientResetPassword";
		}

		Client client = clientService.findId(id)
				.orElseThrow(() -> new IllegalArgumentException("L' Id du particulier est invalide n° : " + id));

		String cryptPassword = passwordEncoder.encode(clientPassword);
		client.setClientPassword(cryptPassword);
		clientService.create(client);

		return "redirect:/";
	}

}
