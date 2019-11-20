package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EstimateService;

@Controller
public class ClientEstimateController {

    @Autowired
    private Logger          LOGGER;

    @Autowired
    private ClientService   clientService;

    @Autowired
    private EstimateService estimateService;

    // all estimates list
    @GetMapping( "/clients/estimatesAllList" )
    public String EstimatesAllListClient( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesAllList\" client est demandée" );
        Client client = this.clientService.findEmail( principal.getName() );
        model.addAttribute( "client", client );
        List<Estimate> estimatesStatusList = this.estimateService.readAll();
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "all" );

        return "clients/ClientEstimatesList";
    }

    // request estimates list
    @GetMapping( "/clients/estimatesRequestList" )
    public String EstimatesRequestListClient( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesRequestList\" client est demandée" );
        Client client = this.clientService.findEmail( principal.getName() );
        model.addAttribute( "client", client );
        List<Estimate> estimatesStatusList = this.estimateService.readByStatus( EstimateStatus.REQUEST_CLIENT );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "request" );

        return "clients/ClientEstimatesList";
    }

    // awaiting estimates list
    @GetMapping( "/clients/estimatesAwaitingList" )
    public String EstimatesAwaitingListClient( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesAwaitingList\" client est demandée" );
        Client client = this.clientService.findEmail( principal.getName() );
        model.addAttribute( "client", client );
        List<Estimate> estimatesStatusList = this.estimateService
                .readByStatus( EstimateStatus.AWAITING_APPROVAL_CLIENT );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "awaiting" );

        return "clients/ClientEstimatesList";
    }

    // accepted estimates list
    @GetMapping( "/clients/estimatesAcceptedList" )
    public String EstimatesAcceptedListClient( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesAcceptedList\" client est demandée" );
        Client client = this.clientService.findEmail( principal.getName() );
        model.addAttribute( "client", client );
        List<Estimate> estimatesStatusList = this.estimateService
                .readByStatus( EstimateStatus.ACCEPTED );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "accepted" );

        return "clients/ClientEstimatesList";
    }

    // accepted estimates list
    @GetMapping( "/clients/estimatesConvertedList" )
    public String EstimatesConvertedListClients( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesConvertedList\" client est demandée" );
        Client client = this.clientService.findEmail( principal.getName() );
        model.addAttribute( "client", client );
        List<Estimate> estimatesStatusList = this.estimateService
                .readByStatus( EstimateStatus.CONVERTED );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "converted" );

        return "clients/ClientEstimatesList";
    }

    // delete estimate
    @GetMapping( "/clients/EstimateDelete/{id}" )
    public String EstimateDeleteClient( @PathVariable Integer id, Model model, Principal principal,
            HttpServletRequest req ) {
        this.LOGGER.info( "Liste devis client: Suppression du devis numéro " + id );
        this.estimateService.delete( id );
        Client client = this.clientService.findEmail( principal.getName() );
        model.addAttribute( "client", client );
        String url = req.getHeader( "referer" );
        List<Estimate> estimatesStatusList;

        if ( url.contains( "Request" ) ) {
            estimatesStatusList = this.estimateService.readByStatus( EstimateStatus.REQUEST_CLIENT );
            model.addAttribute( "mode", "request" );
        } else if ( url.contains( "Awaiting" ) ) {
            estimatesStatusList = this.estimateService
                    .readByStatus( EstimateStatus.AWAITING_APPROVAL_CLIENT );
            model.addAttribute( "mode", "awaiting" );
        } else if ( url.contains( "Accepted" ) ) {
            estimatesStatusList = this.estimateService.readByStatus( EstimateStatus.ACCEPTED );
            model.addAttribute( "mode", "accepted" );
        } else if ( url.contains( "Converted" ) ) {
            estimatesStatusList = this.estimateService.readByStatus( EstimateStatus.CONVERTED );
            model.addAttribute( "mode", "converted" );
        } else {
            estimatesStatusList = this.estimateService.readAll();
            model.addAttribute( "mode", "all" );
        }
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        return "clients/ClientEstimatesList";
    }

}
