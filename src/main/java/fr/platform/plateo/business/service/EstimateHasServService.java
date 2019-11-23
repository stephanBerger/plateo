package fr.platform.plateo.business.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateHasService;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.Profession;
import fr.platform.plateo.persistence.EstimateHasServiceRepository;

@Service
public class EstimateHasServService {

    @Autowired
    private EstimateHasServiceRepository estimateHasServiceRepo;

    @Autowired
    private EstimateService              estimateService;

    public Optional<EstimateHasService> findServiceId( Integer id ) {
        return this.estimateHasServiceRepo.findById( id );
    }

    public void create( EstimateHasService estimatehs ) {
        this.estimateHasServiceRepo.save( estimatehs );
    }

    public List<EstimateHasService> readAll() {
        return this.estimateHasServiceRepo.findAll();
    }

    public List<EstimateHasService> readByEstimateId( Integer estimateId ) {
        return this.estimateHasServiceRepo.findAllByEstimateId( estimateId );
    }

    public List<EstimateHasService> readByEstimateIdServiceId( Integer estimateId, Integer serviceId ) {
        return this.estimateHasServiceRepo.findByEstimateIdAndServiceId( estimateId, serviceId );
    }

    public HashSet<Estimate> readAllEstimatesReqProProfessions( Pro pro ) {

        HashSet<Estimate> estimatesMatchesProProfList = new HashSet<>();
        List<EstimateHasService> estimateHasServList = new ArrayList<EstimateHasService>();
        Profession professionHasServ = null;
        List<Profession> professionsEstimate = new ArrayList<Profession>();

        List<Estimate> estimatesRequestList = this.estimateService.readByStatus( EstimateStatus.REQUEST_CLIENT );

        for ( Estimate est : estimatesRequestList ) {
            System.out.println( "liste des devis : " + est.getEstimateStatus() );
        }

        for ( Estimate estimate : estimatesRequestList ) {

            estimateHasServList = this.readByEstimateId( estimate.getId() );
            System.out.println( "liste des services par id devis : " + estimate.getId() );

            for ( EstimateHasService ehs : estimateHasServList ) {
                System.out.println( ehs.getService().getName() );
            }

            for ( EstimateHasService estimateHasServ : estimateHasServList ) {
                professionHasServ = estimateHasServ.getService().getProfession();
                System.out.println( "le corp état est : " + professionHasServ.getName() );
                professionsEstimate.add( professionHasServ );
                System.out.println( "la liste de corps d'état pour cet id " + estimate.getId()
                        + " est : " );
                for ( Profession prof : professionsEstimate ) {
                    System.out.println( prof.getName() );
                }
            }

            List<Profession> proProfessionsList = pro.getListProProfessions();

            System.out.println( "list de corps état du pro : " + pro.getId() + " " + pro.getManagerLastname() );

            for ( Profession p : proProfessionsList ) {
                System.out.println( p.getName() );
            }

            System.out.println( "rappel: la liste des profession de l'id : " + estimate.getId() );

            for ( Profession pr : professionsEstimate ) {
                System.out.println( pr.getName() );
            }

            for ( Profession professionPro : proProfessionsList ) {
                for ( Profession professionEstimate : professionsEstimate ) {
                    if ( professionPro.equals( professionEstimate ) ) {
                        System.out.println( estimate.getEstimateStatus() + " " + estimate.getId() );
                        estimatesMatchesProProfList.add( estimate );

                        System.out.println( "si correspond avec professions pro: " + estimatesMatchesProProfList );
                    }
                }
            }
        }
        return estimatesMatchesProProfList;

    }

}
