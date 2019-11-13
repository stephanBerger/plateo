package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.BooleanFormType;
import org.activiti.engine.task.Task;
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
			@ModelAttribute("assigneeId") Integer assigneeId,
			Principal principal) {
		Client client = this.clientService
				.loadUserByUsername(req.getUserPrincipal().getName());
		model.addAttribute("client", client);
		if (assigneeId == null) {
			model.addAttribute("assigneeId", client.getId());
		}
		model.addAttribute("professions", this.professionService.getAll());
		return "clients/estimateRequest";
	}

	@GetMapping("/professionRequest/{id}")
	public String professionRequest(@PathVariable Integer id, Model model,
			Principal principal) {
		Profession profession = this.professionService.read(id);
		Client client = this.clientService
				.loadUserByUsername(principal.getName());
		model.addAttribute("client", client);
		model.addAttribute("profession", profession);
		model.addAttribute("services", profession.getServices());
		return "clients/professionRequest";
	}

	@GetMapping("/serviceRequest/{professionId}/{serviceId}")
	public String serviceRequest(@PathVariable Integer professionId,
			@PathVariable Integer serviceId, Model model,
			@ModelAttribute("assigneeId") Integer assigneeId,
			Principal principal) {
		Task task = this.bpmService.startProcess(assigneeId, professionId,
				serviceId);
		Client client = this.clientService
				.loadUserByUsername(principal.getName());
		model.addAttribute("client", client);
		model.addAttribute("task", task);
		model.addAttribute("properties",
				this.bpmService.getFormData(task.getId()));
		return "clients/serviceRequest";
	}

	@PostMapping("/serviceProcess/{processInstanceId}/{taskId}")
	public String serviceProcess(@PathVariable String processInstanceId,
			@PathVariable String taskId,
			@RequestParam Map<String, Object> params) {
		for (FormProperty prop : this.bpmService.getFormData(taskId)) {
			if (prop.getType() instanceof BooleanFormType) {
				if (params.containsKey(prop.getId())) {
					params.put(prop.getId(), true);
				} else {
					params.put(prop.getId(), false);
				}
			}
		}
		this.bpmService.save(taskId, params);
		return "redirect:/clients/serviceTask/" + processInstanceId;
	}

	@GetMapping("/serviceTask/{processInstanceId}")
	public String serviceTask(@PathVariable String processInstanceId,
			@ModelAttribute("assigneeId") Integer assigneeId, Model model,
			Principal principal) {
		Task task = this.bpmService
				.getTaskByProcessInstanceIdAndAssigneeId(assigneeId,
						processInstanceId);
		if (task != null) {
			Client client = this.clientService
					.loadUserByUsername(principal.getName());
			model.addAttribute("client", client);
			model.addAttribute("task", task);
			model.addAttribute("properties",
					this.bpmService.getFormData(task.getId()));
			return "clients/serviceRequest";
		}
		return "redirect:/clients/clientDashboard";
	}
}
