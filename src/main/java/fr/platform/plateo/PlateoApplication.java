package fr.platform.plateo;

import java.io.IOException;

import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@ComponentScan({ "fr.platform.plateo.presentation",
		"fr.platform.plateo.business.entity",
		"fr.platform.plateo.business.service",
		"fr.platform.plateo.persistence", "fr.platform.plateo.config" })

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PlateoApplication {

	private static final String BPMN_PATH = "processes/";

	public static void main(String[] args) {
		SpringApplication.run(PlateoApplication.class, args);
	}

	@Autowired
	private DataSource dataSource;

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(this.dataSource);
	}

	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() {
		SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
		try {
			config.setDeploymentResources(this.getBpmnFiles());
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.setDataSource(this.dataSource);
		config.setTransactionManager(this.transactionManager());
		config.setDatabaseSchemaUpdate("true");
		config.setHistory("audit");
		return config;
	}

	private Resource[] getBpmnFiles() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		return resourcePatternResolver.getResources(
				"classpath*:" + PlateoApplication.BPMN_PATH + "**/*.bpmn");
	}

	@Bean
	public ProcessEngineFactoryBean processEngine() {
		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(
				this.processEngineConfiguration());
		return factoryBean;
	}

	@Bean
	public RepositoryService repositoryService(
			ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	@Bean
	public IdentityService identityService(ProcessEngine processEngine) {
		return processEngine.getIdentityService();
	}

	@Bean
	public FormService formService(ProcessEngine processEngine) {
		return processEngine.getFormService();
	}

	@Bean
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	@Bean
	public TaskService taskService(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	@Bean
	public ManagementService managementService(
			ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}

	@Bean
	public HistoryService historyService(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}
}
