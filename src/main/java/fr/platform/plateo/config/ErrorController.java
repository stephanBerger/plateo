package fr.platform.plateo.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorController {
	
	@Autowired
	private Logger LOGGER;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(Exception e) {
        LOGGER.error("Erreur non gérée dans l'application : ", e);
    	return "error";
    }
}