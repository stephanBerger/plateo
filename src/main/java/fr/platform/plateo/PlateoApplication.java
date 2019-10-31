package fr.platform.plateo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "fr.platform.plateo.presentation", "fr.platform.plateo.business.entity",
		"fr.platform.plateo.business.service", "fr.platform.plateo.persistence", "fr.platform.plateo.config" })

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PlateoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateoApplication.class, args);
	}

}
