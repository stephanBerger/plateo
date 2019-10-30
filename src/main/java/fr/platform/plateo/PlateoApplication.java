package fr.platform.plateo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@ComponentScan({ "fr.platform.plateo.presentation", 
				"fr.platform.plateo.business.entity",
				"fr.platform.plateo.business.service",
				"fr.platform.plateo.persistence",
				"fr.platform.plateo.config"})
@EnableWebSecurity
public class PlateoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateoApplication.class, args);
	}

}
