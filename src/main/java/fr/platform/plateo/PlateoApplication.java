package fr.platform.plateo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "fr.platform.plateo.presentation",
		"fr.platform.plateo.business.entity",
		"fr.platform.plateo.business.service",
		"fr.platform.plateo.persistence", "fr.platform.plateo.config" })

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PlateoApplication {
	public static void main(String[] args) {
		SpringApplication.run(PlateoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner init(
//			final BusinessProcessModelService myService,
//			final ClientRepository repo) {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... strings) throws Exception {
//				ProcessInstance pi = myService.makeEstimate(repo
//						.findOneByClientEmailAddress("elvynia@gmail.com"));
//				Logger logger = LoggerFactory
//						.getLogger("COMMAND_LINE_RUNNER");
//				logger.info("Making new estimate : " + pi);
//				logger.info("Liste des tâches : ");
//				for (Task t : myService.getTasks()) {
//					logger.info("\t" + t);
//				}
//
//			}
//		};
//
//	}
}
