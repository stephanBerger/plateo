package fr.platform.plateo.presentation;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.BooleanFormType;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.Profession;
import fr.platform.plateo.business.service.BusinessProcessModelService;
import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.EstimateHasServService;
import fr.platform.plateo.business.service.EstimateService;
import fr.platform.plateo.business.service.ProfessionService;

@Controller
@RequestMapping( "/clients" )
@Scope( "session" )
@SessionAttributes( { "assigneeId" } )
public class RequestController {

    @Autowired
    private Logger                      LOGGER;

    @Autowired
    private ProfessionService           professionService;

    @Autowired
    private ClientService               clientService;

    @Autowired
    private BusinessProcessModelService bpmService;

    @Autowired
    private EstimateService             estimateService;

    @Autowired
    private EstimateHasServService      estimateHasServService;

    @ModelAttribute( "assigneeId" )
    public Integer assigneeId() {
        return null;
    }

    // client a validé un devis avec 1 prestation, il continue en rajoutant une
    // 2eme
    // prestation
    @GetMapping( "/estimateRequestNext/{estimateid}/{proid}" )
    public String estimateRequestNext( @PathVariable Integer estimateid, @PathVariable Integer proid, Model model,
            HttpServletRequest req, @ModelAttribute( "assigneeId" ) Integer assigneeId, Principal principal ) {

        this.LOGGER.info( "Le client continue avec une autre prestation avec Estimate ID : " + estimateid );
        Client client = this.clientService.loadUserByUsername( req.getUserPrincipal().getName() );
        model.addAttribute( "client", client );
        if ( assigneeId == null ) {
            model.addAttribute( "assigneeId", client.getId() );
        }
        if ( estimateid == null ) {
            model.addAttribute( "estimateid", estimateid );
        }

        model.addAttribute( "professions", this.professionService.getAll() );
        return "clients/estimateRequest";
    }

    // new estimate
    @GetMapping( "/estimateRequest/{estimateid}/{proid}" )
    public String estimateRequest( @PathVariable Integer estimateid, @PathVariable Integer proid, Model model,
            HttpServletRequest req, @ModelAttribute( "assigneeId" ) Integer assigneeId,
            Principal principal ) {
        Client client = this.clientService.loadUserByUsername( req.getUserPrincipal().getName() );

        if ( assigneeId == null ) {
            model.addAttribute( "assigneeId", client.getId() );
        }

        Estimate estimate = new Estimate();
        estimate.setWorkAddress( client.getClientAddress() );
        estimate.setWorkPostcode( client.getClientPostcode() );
        estimate.setWorkCity( client.getClientCity() );
        estimate.setEstimateStatus( EstimateStatus.DRAFT_REQUEST_CLIENT );

        Client client2 = new Client();
        client2.setId( client.getId() );
        estimate.setClient( client2 );

        if ( proid != 0 ) {
            Pro pro = new Pro();
            pro.setId( proid );
            estimate.setPro( pro );
        }
        this.estimateService.create( estimate );

        model.addAttribute( "client", client );
        model.addAttribute( "estimateid", estimate.getId() );
        model.addAttribute( "proid", proid );
        model.addAttribute( "professions", this.professionService.getAll() );

        return "clients/estimateRequest";
    }

    @GetMapping( "/professionRequest/{id}/{estimateid}/{proid}" )
    public String professionRequest( @PathVariable Integer id, @PathVariable Integer estimateid,
            @PathVariable Integer proid, Model model, Principal principal ) {

        Profession profession = this.professionService.read( id );
        Client client = this.clientService.loadUserByUsername( principal.getName() );
        model.addAttribute( "estimateid", estimateid );
        model.addAttribute( "proid", proid );
        model.addAttribute( "client", client );
        model.addAttribute( "profession", profession );
        model.addAttribute( "services", profession.getServices() );
        return "clients/professionRequest";
    }

    @GetMapping( "/serviceRequest/{professionId}/{serviceId}/{estimateid}/{proid}" )
    public String serviceRequest( @PathVariable Integer professionId, @PathVariable Integer serviceId,
            @PathVariable Integer estimateid, @PathVariable Integer proid, Model model,
            @ModelAttribute( "assigneeId" ) Integer assigneeId, Principal principal ) {

        Task task = this.bpmService.startProcess( assigneeId, professionId, serviceId );
        Client client = this.clientService.loadUserByUsername( principal.getName() );
        model.addAttribute( "estimateid", estimateid );
        model.addAttribute( "proid", proid );
        model.addAttribute( "client", client );
        model.addAttribute( "task", task );
        model.addAttribute( "properties", this.bpmService.getFormData( task.getId() ) );
        return "clients/serviceRequest";
    }

    @PostMapping( "/serviceProcess/{processInstanceId}/{taskId}/{estimateid}/{proid}" )
    public String serviceProcess( Model model, @PathVariable String processInstanceId, @PathVariable String taskId,
            @PathVariable Integer estimateid, @PathVariable Integer proid, @RequestParam Map<String, Object> params ) {
        for ( FormProperty prop : this.bpmService.getFormData( taskId ) ) {
            if ( prop.getType() instanceof BooleanFormType ) {
                if ( params.containsKey( prop.getId() ) ) {
                    params.put( prop.getId(), true );
                } else {
                    params.put( prop.getId(), false );
                }
            }
        }
        model.addAttribute( "estimateid", estimateid );
        model.addAttribute( "proid", proid );
        this.bpmService.save( taskId, params );
        return "redirect:/clients/serviceTask/" + processInstanceId + "/" + estimateid + "/" + proid;
    }

    @GetMapping( "/serviceTask/{processInstanceId}/{estimateid}/{proid}" )
    public String serviceTask( @PathVariable String processInstanceId, @PathVariable Integer estimateid,
            @PathVariable Integer proid, @ModelAttribute( "assigneeId" ) Integer assigneeId, Model model,
            Principal principal, Estimate estimate ) {

        Task task = this.bpmService.getTaskByProcessInstanceIdAndAssigneeId( assigneeId, processInstanceId );
        if ( task != null ) {
            Client client = this.clientService.loadUserByUsername( principal.getName() );
            model.addAttribute( "client", client );
            model.addAttribute( "task", task );
            model.addAttribute( "properties", this.bpmService.getFormData( task.getId() ) );
            return "clients/serviceRequest";

        } else {

            Client client = this.clientService.findId( assigneeId )
                    .orElseThrow( () -> new IllegalArgumentException( "L' Id est invalide" ) );

            Estimate estimate2 = this.estimateService.readOne( estimateid );
            estimate2.setWorkAddress( client.getClientAddress() );

            this.LOGGER.info( "riri pro " + proid );
            model.addAttribute( "estimateid", estimateid );
            model.addAttribute( "proid", proid );
            model.addAttribute( "estimate", estimate2 );
            model.addAttribute( "client", client );
            model.addAttribute( "processInstanceId", processInstanceId );

        }
        return "clients/estimate";

    }

    // validation post du devis client
    @PostMapping( "/valideEstimate/{processInstanceId}/{estimateid}/{proid}" )
    public String postEstimateSave( @Valid Estimate estimate, BindingResult result, Model model, Client client,
            @PathVariable Integer processInstanceId, @PathVariable Integer estimateid, @PathVariable Integer proid,
            @ModelAttribute( "assigneeId" ) Integer assigneeId, Principal principal,
            @RequestParam( value = "prestation" ) String prestation, final RedirectAttributes redirectAttributes ) {

        if ( estimate.getRequestDate().compareTo( estimate.getWorkDeadline() ) > 0 ) {
            model.addAttribute( "msg", "datesup" );
            model.addAttribute( "estimateid", estimateid );
            model.addAttribute( "proid", proid );
            model.addAttribute( "estimate", estimate );
            model.addAttribute( "client", client );
            model.addAttribute( "processInstanceId", processInstanceId );
            return "clients/estimate";
        }

        if ( prestation.contentEquals( "otherprestation" ) ) {
            estimate.setEstimateStatus( EstimateStatus.DRAFT_REQUEST_CLIENT );
        } else if ( prestation.contentEquals( "endprestation" ) ) {
            estimate.setEstimateStatus( EstimateStatus.REQUEST_CLIENT );
        }
        this.estimateService.create( estimate );

        EstimateHasService estimatehs = new EstimateHasService();
        estimatehs.setProcessid( processInstanceId );
        Estimate estimate2 = new Estimate();
        estimate2.setId( estimateid );
        estimatehs.setEstimate( estimate2 );
        this.estimateHasServService.create( estimatehs );

        model.addAttribute( "estimateid", estimateid );
        model.addAttribute( "proid", proid );
        model.addAttribute( "estimate", estimate );
        model.addAttribute( "client", client );
        model.addAttribute( "processInstanceId", processInstanceId );

        if ( prestation.contentEquals( "otherprestation" ) ) {
            return "redirect:/clients/estimateRequestNext/" + estimateid + "/" + proid;
        } else if ( prestation.contentEquals( "endprestation" ) ) {
            return "/clients/clientValidDevis";
        }

        return null;

    }

}
