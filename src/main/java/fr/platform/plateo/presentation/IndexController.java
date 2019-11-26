package fr.platform.plateo.presentation;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.restfb.types.User;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Role;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EmailService;
import fr.platform.plateo.business.service.FacebookService;

/**
 *
 */
@Controller
@Scope("session")
@SessionAttributes({ "proId" })
public class IndexController {
	/**
	 *
	 */
	@Autowired
	private FacebookService facebookService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private Logger LOGGER;

	@ModelAttribute("proId")
	public Integer proId() {
		return null;
	}

	// facebook login
	@RequestMapping("/login-facebook")
	public String loginFacebook(@ModelAttribute("proId") Integer proId, HttpServletRequest request,
			Model model) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			return "/errors/403";
		}

		String accessToken = this.facebookService.getToken(code);
		User user = this.facebookService.getUserInfo(accessToken);

		Client existing = this.clientService.findEmail(user.getEmail());
		if (existing == null) {

			Client client = new Client();

			client.setClientFirstname(user.getFirstName());
			client.setClientLastname(user.getLastName());
			client.setClientEmailAddress(user.getEmail());
			BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(4);
			String password = crypt.encode(user.getId());
			client.setClientPassword(password);

			Role role = new Role();
			role.setId(2);
			client.setRole(role);

			client.setEnabled(true);

			this.clientService.create(client);
			this.LOGGER.info("Le client FACEBOOK " + client.getClientFirstname() + " "
					+ client.getClientLastname()
					+ " a été rajouté avec succés - redirect sur la page valid_client");

			String text = "Bonjour " + user.getFirstName() + " " + user.getLastName() + ","
					+ "\n\nVotre incription a bien été prise en compte."
					+ "\n\nPLATEO vous remercie de votre confiance.";

			this.emailService.sendEmail(user.getEmail(), "PLATEO - INSCRIPTION", text);
			this.LOGGER.info("Email inscription envoyé");

		}

		UserDetails userDetail = this.facebookService.buildUser(user);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (proId == null) {
			return "redirect:/clients/clientDashboard";
		} else {
			return "redirect:clients/estimateRequest";
		}
	}

	@GetMapping({ "/", "/clients/login", "/pro/login" })
	public String base() {
		this.LOGGER.info("Une requête sur '/' est faite, la page 'index' est envoyée");
		return "public/index";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "/errors/403";
	}

	@GetMapping("/footer/qui_sommes_nous")
	public String aboutUs() {
		return "public/aboutUs";
	}

}