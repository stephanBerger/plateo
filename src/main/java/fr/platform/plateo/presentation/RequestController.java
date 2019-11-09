package fr.platform.plateo.presentation;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Profession;
import fr.platform.plateo.business.service.BusinessProcessModelService;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.ProfessionService;

@Controller
@RequestMapping("/clients")
@Scope("session")
@SessionAttributes({ "assigneeId" })
public class RequestController {

	@Autowired
	private ProfessionService professionService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private BusinessProcessModelService bpmService;

	@ModelAttribute("assigneeId")
	public Integer assigneeId() {
		return null;
	}

	@GetMapping("/estimateRequest")
	public String estimateRequest(Model model, HttpServletRequest req,
			@ModelAttribute("assigneeId") Integer assigneeId) {
		if (assigneeId == null) {
			Client client = this.clientService
					.loadUserByUsername(req.getUserPrincipal().getName());
			model.addAttribute("assigneeId", client.getId());
		}
		model.addAttribute("professions", this.professionService.getAll());
		return "clients/estimateRequest";
	}

	@GetMapping("/professionRequest/{id}")
	public String professionRequest(@PathVariable Integer id,
			Model model) {
		Profession profession = this.professionService.read(id);
		model.addAttribute("profession", profession);
		model.addAttribute("services", profession.getServices());
		return "clients/professionRequest";
	}

	@GetMapping("/serviceRequest/{professionId}/{serviceId}")
	public String serviceRequest(@PathVariable Integer professionId,
			@PathVariable Integer serviceId, Model model,
			@ModelAttribute("assigneeId") Integer assigneeId) {
		Task task = this.bpmService.startProcess(assigneeId, professionId,
				serviceId);

		model.addAttribute("task", task);
		return "clients/serviceRequest";
	}
}
