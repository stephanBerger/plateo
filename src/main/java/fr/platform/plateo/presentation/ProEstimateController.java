package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.service.BusinessProcessModelService;
import fr.platform.plateo.business.service.EstimateHasServService;
import fr.platform.plateo.business.service.EstimateService;
import fr.platform.plateo.business.service.ProService;

@Controller
public class ProEstimateController {

	@Autowired
	private Logger LOGGER;

	@Autowired
	private ProService proService;

	@Autowired
	private EstimateService estimateService;

	@Autowired
	private EstimateHasServService estimateHasService;

	@Autowired
	private BusinessProcessModelService bpmService;

	// all estimates list
	@GetMapping("/pro/estimatesAllList")
	public String EstimatesAllListPro(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesAllList\" pour Pro est demandée");
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);
		List<Estimate> estimatesStatusList = this.estimateService.readAll();
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "all");

		return "pro/proEstimatesList";
	}

	// professions estimates list
	@GetMapping("/pro/estimatesProfessionList")
	public String EstimatesProfessionListPro(Model model, Principal principal) {

		this.LOGGER.info("La page \"estimatesProfessionList\" pour Pro est demandée");

		Pro pro = this.proService.findEmail(principal.getName());

		HashSet<Estimate> estimatesStatusList = this.estimateHasService.readAllEstimatesReqProProfessions(pro);
		model.addAttribute("pro", pro);
		model.addAttribute("estimatesStatusList", estimatesStatusList);
		model.addAttribute("mode", "profession");

		return "pro/proEstimatesList";
	}

	// request estimates list
	@GetMapping("/pro/estimatesDirectList")
	public String EstimatesDirectListPro(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesDirectList\" pour Pro est demandée");
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);
		List<Estimate> estimatesStatusList = this.estimateService.readByStatusPro(EstimateStatus.DEMANDE, pro);

		for (Estimate estimate : estimatesStatusList) {
			System.out.println("la liste sur pro direct : " + estimate);
		}

		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "direct");

		return "pro/proEstimatesList";
	}

	// request estimates list
	@GetMapping("/pro/estimatesRequestList")
	public String EstimatesRequestListPro(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesRequestList\" pour Pro est demandée");
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);

		List<Estimate> estimatesStatusList = this.estimateService.readByStatus(EstimateStatus.DEMANDE);
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "request");

		return "pro/proEstimatesList";
	}

	// awaiting estimates list
	@GetMapping("/pro/estimatesAwaitingList")
	public String EstimatesAwaitingListPro(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesAwaitingList\" pour Pro est demandée");
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);
		List<Estimate> estimatesStatusList = this.estimateService
				.readByStatusPro(EstimateStatus.ATTENTE_ACCEPTATION_CLIENT, pro);
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "awaiting");

		return "pro/proEstimatesList";
	}

	// awaiting and request estimates list
	@GetMapping("/pro/estimatesRequestAwaitingList")
	public String EstimatesAcceptedListPro(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesRequestAwaitingList\" pour Pro est demandée");
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);
		List<Estimate> estimatesStatusList = this.estimateService.readByStatus(EstimateStatus.DEMANDE);
		List<Estimate> estimatesStatusList2 = this.estimateService
				.readByStatusPro(EstimateStatus.ATTENTE_ACCEPTATION_CLIENT, pro);
		estimatesStatusList.addAll(estimatesStatusList2);
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "requestAwaiting");

		return "pro/proEstimatesList";
	}

	// accepted estimates list
	@GetMapping("/pro/estimatesConvertedList")
	public String EstimatesConvertedListPro(Model model, Principal principal) {
		this.LOGGER.info("La page \"estimatesConvertedList\" pour Pro est demandée");
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);
		List<Estimate> estimatesStatusList = this.estimateService.readByStatusPro(EstimateStatus.FACTURE, pro);
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		model.addAttribute("mode", "converted");

		return "pro/proEstimatesList";
	}

	// delete estimate
	@GetMapping("/pro/EstimateDelete/{id}")
	public String EstimateDeletePro(@PathVariable Integer id, Model model, Principal principal,
			HttpServletRequest req) {
		this.LOGGER.info("Liste devis Pro: Suppression du devis numéro " + id);
		this.estimateService.delete(id);
		Pro pro = this.proService.findEmail(principal.getName());
		model.addAttribute("pro", pro);

		String url = req.getHeader("referer");
		List<Estimate> estimatesStatusList;

		if (url.contains("Request")) {
			estimatesStatusList = this.estimateService.readByStatus(EstimateStatus.DEMANDE);
			model.addAttribute("mode", "request");
		} else if (url.contains("Awaiting")) {
			estimatesStatusList = this.estimateService.readByStatusPro(EstimateStatus.ATTENTE_ACCEPTATION_CLIENT, pro);
			model.addAttribute("mode", "awaiting");
		} else if (url.contains("Accepted")) {
			estimatesStatusList = this.estimateService.readByStatusPro(EstimateStatus.ACCEPTE, pro);
			model.addAttribute("mode", "accepted");
		} else if (url.contains("Converted")) {
			estimatesStatusList = this.estimateService.readByStatusPro(EstimateStatus.FACTURE, pro);
			model.addAttribute("mode", "converted");
		} else {
			estimatesStatusList = this.estimateService.readByStatus(EstimateStatus.DEMANDE);
			estimatesStatusList
					.addAll(this.estimateService.readByStatusPro(EstimateStatus.ATTENTE_ACCEPTATION_CLIENT, pro));

			model.addAttribute("mode", "requestAwaiting");
		}
		model.addAttribute("estimatesStatusList", estimatesStatusList);

		return "pro/proEstimatesList";
	}

	@GetMapping("/pro/EstimateDetails/{estimateId}")
	public String estimateDetails(@PathVariable Integer estimateId, Model model, Principal principal) {
		Pro pro = this.proService.findEmail(principal.getName());
		Estimate estimate = this.estimateService.readOne(estimateId);

		model.addAttribute("pro", pro);
		model.addAttribute("estimate", estimate);
		model.addAttribute("client", estimate.getClient());
		model.addAttribute("services", this.bpmService.getDataForAssigneeAndProcess(pro.getId(), estimateId));
		return "pro/proEstimateDetails";
	}

	@PostMapping("/pro/EstimateDetails/{estimateId}")
	public String estimateCreate(@PathVariable Integer estimateId, Model model, Principal principal,
			@RequestParam Map<String, String> params) {
		Pro pro = this.proService.findEmail(principal.getName());
		Estimate estimate = this.estimateService.readOne(estimateId);
		model.addAttribute("estimate", estimate);
		model.addAttribute("pro", pro);
		model.addAttribute("services", this.bpmService.getDataForAssigneeAndProcess(pro.getId(), estimateId));

		for (Map.Entry<String, String> param : params.entrySet()) {
			System.out.println("clé: " + param.getKey() + "," + " valeur: " + param.getValue());
		}

		return "pro/proDashboard";
	}

}
