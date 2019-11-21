package fr.platform.plateo.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Client;
import fr.platform.plateo.business.entity.Estimate;
import fr.platform.plateo.business.entity.EstimateStatus;
import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.persistence.EstimateRepository;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepo;

    public void create( Estimate estimate ) {
        this.estimateRepo.save( estimate );
    }

    public Estimate readOne( Integer id ) {
        return this.estimateRepo.getOne( id );
    }

    public List<Estimate> readAll() {
        return this.estimateRepo.findAll();
    }

    public Estimate update( Integer id ) {
        return this.estimateRepo.getOne( id );
    }

    public void delete( Integer id ) {
        this.estimateRepo.deleteById( id );
    }

    public List<Estimate> readByStatus( EstimateStatus estimateStatus ) {
        return this.estimateRepo.findByEstimateStatus( estimateStatus );
    }

    public List<Estimate> readByStatusClient( EstimateStatus estimateStatus, Client client ) {
        return this.estimateRepo.findByEstimateStatusAndClient( estimateStatus, client );
    }

    public List<Estimate> readByStatusPro( EstimateStatus estimateStatus, Pro pro ) {
        return this.estimateRepo.findByEstimateStatusAndPro( estimateStatus, pro );
    }

}
