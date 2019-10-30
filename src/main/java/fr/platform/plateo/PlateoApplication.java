package fr.platform.plateo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@ComponentScan({ "fr.platform.plateo.presentation"})
@ComponentScan({ "fr.platform.plateo.business.entity"})
@ComponentScan({ "fr.platform.plateo.business.service"})
@ComponentScan({ "fr.platform.plateo.persistence"})
@EnableWebSecurity
public class PlateoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateoApplication.class, args);
	}

}
