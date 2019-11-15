package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.ProPhotos;
import fr.platform.plateo.business.entity.Profession;
import fr.platform.plateo.business.entity.Role;
import fr.platform.plateo.business.service.ProService;
import fr.platform.plateo.business.service.ProfessionService;

@Controller
public class ProController {

	// private final static Logger LOGGER =
	// LoggerFactory.getLogger(ProController.class);

	@Autowired
	private Logger LOGGER;

	@Autowired
	private ProService proService;

	@GetMapping("/public/proProfile/{id}")
	public String proProfile(@PathVariable Integer id, Model model) {
		Pro pro = this.proService.read(id);
		model.addAttribute("pro", pro);
		model.addAttribute("fullAddress",
				pro.getProAddress() + ", " + pro.getProCity() + " " + pro.getProPostcode());
		Base64.Encoder encoder = Base64.getEncoder();

		model.addAttribute("logo",
				"data:image/png;base64," + encoder.encodeToString(pro.getLogo()));
		List<String> encodings = new ArrayList<>();
		for (ProPhotos photo : pro.getListProPhotos()) {
			String encoding = "data:image/png;base64,"
					+ encoder.encodeToString(photo.getProPhoto());
			encodings.add(encoding);
		}

		model.addAttribute("photos", encodings);
		return "/public/proProfile";
	}

	@PostMapping("/public/proAddPhoto/{id}")
	public String proAddPhoto(@PathVariable Integer id,
			@RequestParam("listProPhotos") List<MultipartFile> photos,
			RedirectAttributes redirectAttributes) {
		if (photos.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "plsPhot");
			return "redirect:/uploadStatus";
		}
		this.proService.addPhotos(id, photos);
		return "redirect:/";
	}

	@Autowired
	private ProfessionService professionService;

	/*
	 *
	 * @GetMapping( "/pro/proDashboard" ) public String proDashboard() {
	 * this.LOGGER.info( "La page \"proDashboard\" est demandée" ); return
	 * "/pro/proDashboard"; }
	 */

	// login pro method get
	@GetMapping("/pro/proLogin")
	public String pageLoginProGet() {
		this.LOGGER.info("La page \"proLogin\" est demandée");
		return "/pro/proLogin";
	}

	/*-----------MODIF GREG-----------*/
	@GetMapping("/pro/proDashboard")
	public String proDashboard(Model model, Principal principal) {
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);
		this.LOGGER.info("Authentification ok - redirect sur clientDashboard");
		return "/pro/proDashboard";
	}
	/*-----------FIN MODIF GREG-----------*/

	// list pro method get
	@GetMapping("/public/proList")
	public String listPro(Model model) {
		this.LOGGER.info("La page \"proList\" est demandée");
		List<Pro> listPro = this.proService.readAll();

		model.addAttribute("listPro", listPro);
		// model.addAttribute( "listProProfessions", listProfessions );
		return "public/proList";
	}

	// nouveau pro method get
	@GetMapping("/public/proForm")
	public String proForm(Pro pro, Model model) {
		this.LOGGER.info("La page \"proForm\" est demandée");
		List<Profession> listProfessions = this.professionService.getAll();
		model.addAttribute("listProfessions", listProfessions);
		model.addAttribute("listProfessions",
				this.professionService.getAll());
		return "/public/proForm";
	}

	@PostMapping("/public/proForm")
	public String save(@Valid Pro pro, BindingResult result,
			@RequestParam(value = "confirmProPassword") String confirmPasswordInput) {
		pro.setSiret(pro.getSiret().replaceAll("[^0-9]", ""));
		if (result.hasErrors()) {
			this.LOGGER.info("Erreur dans le formulaire" + pro.getCompanyName());
			System.out.println(result.toString());
			return null;

		} else if (this.proService.loadUserByUsername(pro.getProEmailAddress()) != null) {
			this.LOGGER.info("Utilisateur existe déjà ");
			result.rejectValue("proEmailAddress", null, "Cette adresse email est déjà utilisée.");
			return null;

		} else if (!confirmPasswordInput.equals(pro.getProPassword())) {
			this.LOGGER.info("Les 2 passwords ne sont pas identiques "
					+ confirmPasswordInput.toString() + " "
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
					if (somme >= 10) {
						somme = somme % 10 + somme / 10;
					}
				}
				resultat += somme;
			}

			if (resultat % 10 != 0) {
				this.LOGGER.info("Le Siret n'est pas valide");
				// Mon SIRET 82154303000026 devrait fonctionner mais ce n'est
				// pas le cas !
				result.rejectValue("siret", null, "Le Siret n'est pas valide.");
				return null;
			}

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

			this.LOGGER.info("Creation utlisateur PRO effectué");
			this.proService.create(pro);
			return "pro/proValid";

		}
		return null;

	}
}
