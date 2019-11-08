package fr.platform.plateo.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Client;

@Service
public class BusinessProcessModelService {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	public ProcessInstance makeEstimate(Client assignee) {
		Map<String, Object> vars = new HashMap<>();
		vars.put("account", assignee);
		return this.runtimeService
				.startProcessInstanceByKey("makeEstimate", vars);
	}

	public List<Task> getTasks() {
		return this.taskService.createTaskQuery().list();
	}

	public void resetProcess() {

	}
}
