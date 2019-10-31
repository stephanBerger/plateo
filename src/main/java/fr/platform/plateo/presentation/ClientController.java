package fr.platform.plateo.presentation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.persistence.ClientRepository;

/**
 * 
 */
@Controller
public class ClientController {

    private final static Logger LOGGER = LoggerFactory.getLogger( ClientController.class );

    @Autowired
    private ClientRepository    clientRepo;

    @GetMapping( "/public/new_client" )
    public String newClient( Client client ) {
        LOGGER.info( "La page \"new_client\" est demandée" );
        return "public/new_client";
    }

    @PostMapping( "public/new_client" )
    public String save( @Valid Client client, BindingResult result ) {
        if ( result.hasErrors() ) {
            return "form";
        } else {
            clientRepo.save( client );
            return "redirect:/";
        }
    }
}
