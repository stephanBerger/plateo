package fr.platform.plateo.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.persistence.ProfessionRepository;
import fr.platform.plateo.persistence.ServiceRepository;

@Service
public class BusinessProcessModelService {

	private static final String PROCESS_NAME_PREFIX = "makeEstimate-";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BusinessProcessModelService.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private ProfessionRepository professionRepo;

	@Autowired
	private ServiceRepository serviceRepo;

	public Task startProcess(Integer assigneeId, Integer professionId,
			Integer serviceId) {
		String professionCode = this.professionRepo.getOne(professionId)
				.getCode();
		String serviceCode = this.serviceRepo.getOne(serviceId).getCode();
		BusinessProcessModelService.LOGGER.info(
				"STARTING PROCESS {} {} {} !", assigneeId, professionCode,
				serviceCode);
		Map<String, Object> vars = new HashMap<>();
		vars.put("account", this.clientService.findId(assigneeId).get());
		ProcessInstance pi = this.runtimeService
				.startProcessInstanceByKey(
						BusinessProcessModelService.PROCESS_NAME_PREFIX
								+ professionCode + "-" + serviceCode,
						vars);
		// TODO: Créer le devis avec EstimateHasService et data contenant le process
		// instance Id.
		Task task = this.getTaskByProcessInstanceIdAndAssigneeId(
				assigneeId, pi.getProcessInstanceId());
		return task;
	}

	public Task getTaskByProcessInstanceIdAndAssigneeId(Integer assigneeId,
			String processInstanceId) {
		return this.taskService.createTaskQuery()
				.processInstanceId(processInstanceId)
				.taskAssignee(assigneeId.toString()).singleResult();
	}

	public void save(String taskId, Map<String, Object> params) {
		BusinessProcessModelService.LOGGER.info("PARAMS: {}", params);
		this.taskService.complete(taskId, params);
	}

	public List<FormProperty> getFormData(String taskId) {
		return this.processEngine.getFormService().getTaskFormData(taskId)
				.getFormProperties();
	}
}
