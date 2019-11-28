package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EstimateService;
import fr.platform.plateo.business.service.ProService;

@Controller
public class ClientEstimateController {

	@Autowired
	private Logger LOGGER;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProService proService;

	@Autowired
	private EstimateService estimateService;

	// all estimates draft list
	@GetMapping("/clients/estimatesDraftList")
	public String EstimatesDraftListClient(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesDraftList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);
		List<Estimate> estimatesStatusList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.BROUILLON, client);
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "draft");

		return "clients/ClientEstimatesList";
	}

	// all estimates list
	@GetMapping("/clients/estimatesAllList")
	public String EstimatesAllListClient(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesAllList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);
		List<Estimate> estimatesStatusList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readAll();
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "all");

		return "clients/ClientEstimatesList";
	}

	// request estimates list
	@GetMapping("/clients/estimatesRequestList")
	public String EstimatesRequestListClient(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesRequestList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);

		List<Estimate> estimatesStatusList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.DEMANDE, client);

		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "request");

		return "clients/ClientEstimatesList";
	}

	// request + draft estimates list
	@GetMapping("/clients/estimatesAllRequestList")

	public String EstimatesAllRequestListClient(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesRequestList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		System.out.println(client.getId());
		model.addAttribute("client", client);

		List<Estimate> estimatesStatusList = new ArrayList<>();
		List<Estimate> estimatesStatusDraftList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.DEMANDE, client);
		estimatesStatusDraftList = this.estimateService.readByStatusClient(EstimateStatus.BROUILLON, client);

		estimatesStatusList.addAll(estimatesStatusDraftList);

		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "allRequest");

		return "clients/ClientEstimatesList";
	}

	// awaiting and accepted estimates list
	@GetMapping("/clients/estimatesAwaitingAcceptedList")
	public String EstimatesAwaitingListClient(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesAwaitingAcceptedList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);

		List<Estimate> estimatesStatusList = new ArrayList<>();
		List<Estimate> estimatesStatusAcceptedList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.ATTENTE_ACCEPTATION_CLIENT,
				client);
		estimatesStatusAcceptedList = this.estimateService.readByStatusClient(EstimateStatus.ACCEPTE, client);

		estimatesStatusList.addAll(estimatesStatusAcceptedList);

		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "awaitingAccepted");

		return "clients/ClientEstimatesList";
	}

	// accepted estimates list
	@GetMapping("/clients/estimatesAcceptedList")
	public String EstimatesAcceptedListClient(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesAcceptedList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);

		List<Estimate> estimatesStatusList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.ACCEPTE, client);

		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "accepted");

		return "clients/ClientEstimatesList";
	}

	// accepted estimates list
	@GetMapping("/clients/estimatesConvertedList")
	public String EstimatesConvertedListClients(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesConvertedList\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);

		List<Estimate> estimatesStatusList = new ArrayList<>();
		estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.FACTURE, client);
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "converted");

		return "clients/ClientEstimatesList";
	}

	// delete estimate
	@GetMapping("/clients/EstimateDelete/{id}")
	public String EstimateDeleteClient(@PathVariable Integer id, Model model, Principal principal,
			HttpServletRequest req) {
		this.LOGGER.info("Liste devis client: Suppression du devis numéro " + id);
		this.estimateService.delete(id);
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);
		String url = req.getHeader("referer");
		List<Estimate> estimatesStatusList = new ArrayList<>();

		if (url.contains("AllRequest")) {
			estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.DEMANDE, client);
			List<Estimate> estimatesStatusDraftList = new ArrayList<>();
			estimatesStatusDraftList = this.estimateService.readByStatusClient(EstimateStatus.BROUILLON, client);
			estimatesStatusList.addAll(estimatesStatusDraftList);
			model.addAttribute("mode", "allRequest");
		} else if (url.contains("AwaitingAccepted")) {
			estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.ATTENTE_ACCEPTATION_CLIENT,
					client);
			List<Estimate> estimatesStatusAcceptedList = new ArrayList<>();
			estimatesStatusAcceptedList = this.estimateService.readByStatusClient(EstimateStatus.ACCEPTE, client);
			estimatesStatusList.addAll(estimatesStatusAcceptedList);
			model.addAttribute("mode", "awaitingAccepted");
		} else if (url.contains("Converted")) {
			estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.FACTURE, client);
			model.addAttribute("mode", "converted");
		} else {
			estimatesStatusList = this.estimateService.readByStatusClient(EstimateStatus.DEMANDE, client);
			List<Estimate> estimatesStatusDraftList = new ArrayList<>();
			estimatesStatusDraftList = this.estimateService.readByStatusClient(EstimateStatus.BROUILLON, client);
			estimatesStatusList.addAll(estimatesStatusDraftList);
			model.addAttribute("mode", "allRequest");
		}
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		return "clients/ClientEstimatesList";
	}

	// delete estimate à partir du dashboard
	@GetMapping("/clients/EstimateDeleteFromDashboard/{id}")
	public String EstimateDeleteFromDashboard(@PathVariable Integer id, Model model, Principal principal,
			HttpServletRequest req) {
		this.LOGGER.info("Liste devis client: Suppression du devis numéro " + id);
		this.estimateService.delete(id);
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);
		// AJOUT POUR LISTER LES DEMANDES DE DEVIS EN COURS
		model.addAttribute("client", client);
		List<Estimate> estimatesStatusList1 = new ArrayList<>();
		List<Estimate> estimatesStatusList2 = new ArrayList<>();
		estimatesStatusList2 = this.estimateService.readByStatusClient(EstimateStatus.DEMANDE, client);
		estimatesStatusList1 = this.estimateService.readByStatusClient(EstimateStatus.BROUILLON, client);
		estimatesStatusList2.addAll(estimatesStatusList1);

		model.addAttribute("MesDemandesDevis", estimatesStatusList2);
		model.addAttribute("mode", "allRequest");
		// AJOUT DES DEVIS ACCEPTEES
		List<Estimate> estimatesStatusList3 = this.estimateService.readByStatusClient(EstimateStatus.ACCEPTE, client);
		model.addAttribute("MesDevisAcceptes", estimatesStatusList3);
		model.addAttribute("mode", "accepted");
		// AJOUT POUR PROLIST
		List<Pro> proList = this.proService.readAll();
		model.addAttribute("proList", proList);

		return "/clients/clientDashboard";

	}

	// estimate validation
	@GetMapping("clients/clientEstimateValid")
	public String clientEstimateValid(Principal principal, Model model) {
		this.LOGGER.info("La page \"clientEstimateValid\" client est demandée");
		Client client = this.clientService.findEmail(principal.getName());
		model.addAttribute("client", client);

		return "clients/clientEstimateValid";

	}

}
