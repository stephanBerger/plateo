package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.BooleanFormType;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Profession;
import fr.platform.plateo.business.service.BusinessProcessModelService;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EstimateHasServService;
import fr.platform.plateo.business.service.EstimateService;
import fr.platform.plateo.business.service.ProfessionService;

@Controller
@RequestMapping("/clients")
@Scope("session")
@SessionAttributes({ "assigneeId" })
public class RequestController {

	@Autowired
	private Logger LOGGER;

	@Autowired
	private ProfessionService professionService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private BusinessProcessModelService bpmService;

	@Autowired
	private EstimateService estimateService;

	@Autowired
	private EstimateHasServService estimateHasServService;

	@ModelAttribute("assigneeId")
	public Integer assigneeId() {
		return null;
	}

	// client a validé un devis avec 1 prestation, il continue en rajotant une 2eme
	// prestation
	@GetMapping("/estimateRequestNext/{estimateid}")
	public String estimateRequestNext(@PathVariable Integer estimateid, Model model, HttpServletRequest req,
			@ModelAttribute("assigneeId") Integer assigneeId, Principal principal) {
		this.LOGGER.info("Le client continue avec une autre prestation avec Estimate ID : " + estimateid);
		Client client = this.clientService.loadUserByUsername(req.getUserPrincipal().getName());
		model.addAttribute("client", client);
		if (assigneeId == null) {
			model.addAttribute("assigneeId", client.getId());
		}
		if (estimateid == null) {
			model.addAttribute("estimateid", estimateid);
		}
		model.addAttribute("professions", this.professionService.getAll());
		return "clients/estimateRequest";
	}

	@GetMapping("/estimateRequest")
	public String estimateRequest(Model model, HttpServletRequest req, @ModelAttribute("assigneeId") Integer assigneeId,
			Principal principal) {
		Client client = this.clientService.loadUserByUsername(req.getUserPrincipal().getName());
		model.addAttribute("client", client);
		if (assigneeId == null) {
			model.addAttribute("assigneeId", client.getId());
		}
		model.addAttribute("estimateid", 0);
		model.addAttribute("professions", this.professionService.getAll());
		return "clients/estimateRequest";
	}

	@GetMapping("/professionRequest/{id}/{estimateid}")
	public String professionRequest(@PathVariable Integer id, @PathVariable Integer estimateid, Model model,
			Principal principal) {
		Profession profession = this.professionService.read(id);
		Client client = this.clientService.loadUserByUsername(principal.getName());
		model.addAttribute("estimateid", estimateid);
		model.addAttribute("client", client);
		model.addAttribute("profession", profession);
		model.addAttribute("services", profession.getServices());
		return "clients/professionRequest";
	}

	@GetMapping("/serviceRequest/{professionId}/{serviceId}/{estimateid}")
	public String serviceRequest(@PathVariable Integer professionId, @PathVariable Integer serviceId,
			@PathVariable Integer estimateid, Model model, @ModelAttribute("assigneeId") Integer assigneeId,
			Principal principal) {
		Task task = this.bpmService.startProcess(assigneeId, professionId, serviceId);
		Client client = this.clientService.loadUserByUsername(principal.getName());
		model.addAttribute("estimateid", estimateid);
		model.addAttribute("client", client);
		model.addAttribute("task", task);
		model.addAttribute("properties", this.bpmService.getFormData(task.getId()));
		return "clients/serviceRequest";
	}

	@PostMapping("/serviceProcess/{processInstanceId}/{taskId}/{estimateid}")
	public String serviceProcess(Model model, @PathVariable String processInstanceId, @PathVariable String taskId,
			@PathVariable Integer estimateid, @RequestParam Map<String, Object> params) {
		for (FormProperty prop : this.bpmService.getFormData(taskId)) {
			if (prop.getType() instanceof BooleanFormType) {
				if (params.containsKey(prop.getId())) {
					params.put(prop.getId(), true);
				} else {
					params.put(prop.getId(), false);
				}
			}
		}
		model.addAttribute("estimateid", estimateid);
		this.bpmService.save(taskId, params);
		return "redirect:/clients/serviceTask/" + processInstanceId + "/" + estimateid;
	}

	@GetMapping("/serviceTask/{processInstanceId}/{estimateid}")
	public String serviceTask(@PathVariable String processInstanceId, @PathVariable Integer estimateid,
			@ModelAttribute("assigneeId") Integer assigneeId, Model model, Principal principal, Estimate estimate) {
		Task task = this.bpmService.getTaskByProcessInstanceIdAndAssigneeId(assigneeId, processInstanceId);
		if (task != null) {
			Client client = this.clientService.loadUserByUsername(principal.getName());
			model.addAttribute("client", client);
			model.addAttribute("task", task);
			model.addAttribute("properties", this.bpmService.getFormData(task.getId()));
			return "clients/serviceRequest";

		} else {

			Client client = this.clientService.findId(assigneeId)
					.orElseThrow(() -> new IllegalArgumentException("L' Id est invalide"));
			model.addAttribute("estimateid", estimateid);
			model.addAttribute("estimate", estimate);
			model.addAttribute("client", client);
			model.addAttribute("processInstanceId", processInstanceId);

			return "clients/estimate";
		}

	}

	// validation post du devis client
	@PostMapping("/valideEstimate/{processInstanceId}/{estimateid}")
	public String postEstimateSave(@PathVariable Integer processInstanceId, @PathVariable Integer estimateid,
			@ModelAttribute("assigneeId") Integer assigneeId, Model model, Principal principal, Estimate estimate,
			@RequestParam(value = "work_address") String work_address,
			@RequestParam(value = "work_postcode") String work_postcode,
			@RequestParam(value = "work_city") String work_city) {

		// recherche si process existe pour le terminer
		/*
		 * EstimateHasService ehs =
		 * this.estimateHasServService.findServiceId(processInstanceId) .orElseThrow(()
		 * -> new IllegalArgumentException("L' Id est invalide")); if (ehs != null) {
		 * this.LOGGER.info("Une instance de ce devis est deja en cours"); return
		 * "redirect:/clients/clientDashboard"; }
		 */

		if (estimateid == 0) {
			Client client = new Client();
			client.setId(assigneeId);
			estimate.setClient(client);
			estimate.setWork_address(work_address);
			estimate.setWork_postcode(work_postcode);
			estimate.setWork_city(work_city);
			estimate.setEstimatestatus(EstimateStatus.REQUEST_CLIENT);
			this.estimateService.create(estimate);
			
			EstimateHasService estimatehs = new EstimateHasService();
			estimatehs.setProcessid(processInstanceId);
			estimatehs.setEstimate(estimate);
			this.estimateHasServService.create(estimatehs);
			
			model.addAttribute("estimateid", estimate.getId());
			return "/clients/clientValidDevis";
		}
		return null;
		
	}
	
	// validation post du devis client
		@PostMapping("/valideEstimate2/{processInstanceId}/{estimateid}")
		public String postEstimateSave2(@PathVariable Integer processInstanceId, @PathVariable Integer estimateid,
				@ModelAttribute("assigneeId") Integer assigneeId, Model model, Principal principal, Estimate estimate) {

			if (estimateid != 0) {

				EstimateHasService estimatehs = new EstimateHasService();
				estimatehs.setProcessid(processInstanceId);
				Estimate estimate2 = new Estimate();
				estimate2.setId(estimateid);
				estimatehs.setEstimate(estimate2);
				
				this.estimateHasServService.create(estimatehs);
				model.addAttribute("estimateid", estimateid);
				return "/clients/clientValidDevis";
			}
			return "/clients/clientDashboard";
		}


}
