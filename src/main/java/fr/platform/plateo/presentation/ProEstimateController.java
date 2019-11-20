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

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.service.EstimateService;
import fr.platform.plateo.business.service.ProService;

@Controller
public class ProEstimateController {

    @Autowired
    private Logger          LOGGER;

    @Autowired
    private ProService      proService;

    @Autowired
    private EstimateService estimateService;

    // all estimates list
    @GetMapping( "/pro/estimatesAllList" )
    public String EstimatesAllListPro( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesAllList\" pour Pro est demandée" );
        Pro pro = this.proService.findEmail( principal.getName() );
        model.addAttribute( "pro", pro );
        List<Estimate> estimatesStatusList = this.estimateService.readAll();
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "all" );

        return "pro/proEstimatesList";
    }

    // request estimates list
    @GetMapping( "/pro/estimatesRequestList" )
    public String EstimatesRequestListPro( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesRequestList\" pour Pro est demandée" );
        Pro pro = this.proService.findEmail( principal.getName() );
        model.addAttribute( "pro", pro );
        List<Estimate> estimatesStatusList = this.estimateService.readByStatus( EstimateStatus.REQUEST_CLIENT );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "request" );

        return "pro/proEstimatesList";
    }

    // awaiting estimates list
    @GetMapping( "/pro/estimatesAwaitingList" )
    public String EstimatesAwaitingListPro( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesAwaitingList\" pour Pro est demandée" );
        Pro pro = this.proService.findEmail( principal.getName() );
        model.addAttribute( "pro", pro );
        List<Estimate> estimatesStatusList = this.estimateService
                .readByStatus( EstimateStatus.AWAITING_APPROVAL_CLIENT );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "awaiting" );

        return "pro/proEstimatesList";
    }

    // accepted estimates list
    @GetMapping( "/pro/estimatesAcceptedList" )
    public String EstimatesAcceptedListPro( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesAcceptedList\" pour Pro est demandée" );
        Pro pro = this.proService.findEmail( principal.getName() );
        model.addAttribute( "pro", pro );
        List<Estimate> estimatesStatusList = this.estimateService
                .readByStatus( EstimateStatus.ACCEPTED );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "accepted" );

        return "pro/proEstimatesList";
    }

    // accepted estimates list
    @GetMapping( "/pro/estimatesConvertedList" )
    public String EstimatesConvertedListPro( Model model, Principal principal ) {
        this.LOGGER.info( "La page \"estimatesConvertedList\" pour Pro est demandée" );
        Pro pro = this.proService.findEmail( principal.getName() );
        model.addAttribute( "pro", pro );
        List<Estimate> estimatesStatusList = this.estimateService
                .readByStatus( EstimateStatus.CONVERTED );
        model.addAttribute( "estimatesStatusList", estimatesStatusList );

        model.addAttribute( "mode", "converted" );

        return "pro/proEstimatesList";
    }

    // delete estimate
    @GetMapping( "/pro/EstimateDelete/{id}" )
    public String EstimateDeletePro( @PathVariable Integer id, Model model, Principal principal,
            HttpServletRequest req ) {
        this.LOGGER.info( "Liste devis Pro: Suppression du devis numéro " + id );
        this.estimateService.delete( id );
        Pro pro = this.proService.findEmail( principal.getName() );
        model.addAttribute( "pro", pro );
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

        return "pro/proEstimatesList";
    }

}