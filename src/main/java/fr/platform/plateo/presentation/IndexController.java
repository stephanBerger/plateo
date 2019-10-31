package fr.platform.plateo.presentation;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.platform.plateo.business.service.ProService;
import fr.platform.plateo.persistence.ClientRepository;

/**
 * 
 */
@Controller
public class IndexController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private Logger LOGGER;

	@GetMapping("/")
	public String base() {
		LOGGER.info("Une requête sur '/' est faite");
		return "public/index";
	}

	@GetMapping("/index")
	public String index() {
		LOGGER.info("La page \"index\" est demandée");
		return "public/index";
	}

    @GetMapping( "/public/registration" )
    public String registration() {
        LOGGER.info( "La page \"registration\" est demandée" );
        return "public/registration";
    }

	/**
	 * 
	 */
	private ProService proServ;

}