package fr.platform.plateo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"fr.platform.plateo.controller"})
public class PlateoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateoApplication.class, args);
	}

}
