package fr.platform.plateo.business.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
		Task task = this.taskService.createTaskQuery()
				.processInstanceId(pi.getId())
				.taskAssignee(assigneeId.toString()).singleResult();
		System.out.println(task.getFormKey());
		System.out.println(task.getProcessVariables());
		System.out.println(task.getTaskLocalVariables());
		return task;
	}
}
