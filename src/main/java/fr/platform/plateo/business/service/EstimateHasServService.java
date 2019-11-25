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
	private EstimateService estimateService;

	public Optional<EstimateHasService> findServiceId(Integer id) {
		return this.estimateHasServiceRepo.findById(id);
	}

	public void create(EstimateHasService estimatehs) {
		this.estimateHasServiceRepo.save(estimatehs);
	}

	public List<EstimateHasService> readAll() {
		return this.estimateHasServiceRepo.findAll();
	}

	public List<EstimateHasService> readByEstimateId(Integer estimateId) {
		return this.estimateHasServiceRepo.findAllByEstimateId(estimateId);
	}

	public List<EstimateHasService> readByEstimateIdServiceId(Integer estimateId, Integer serviceId) {
		return this.estimateHasServiceRepo.findByEstimateIdAndServiceId(estimateId, serviceId);
	}

	public HashSet<Estimate> readAllEstimatesReqProProfessions(Pro pro) {

		HashSet<Estimate> estimatesMatchesProProfList = new HashSet<>();
		List<EstimateHasService> estimateHasServList = new ArrayList<>();
		Profession professionHasServ = null;
		List<Profession> professionsEstimate;

		List<Estimate> estimatesRequestList = this.estimateService.readByStatus(EstimateStatus.DEMANDE);

		for (Estimate estimate : estimatesRequestList) {
			professionsEstimate = new ArrayList<>();
			estimateHasServList = this.readByEstimateId(estimate.getId());

			for (EstimateHasService estimateHasServ : estimateHasServList) {
				professionHasServ = estimateHasServ.getService().getProfession();
				professionsEstimate.add(professionHasServ);
			}

			List<Profession> proProfessionsList = pro.getListProProfessions();

			for (Profession professionPro : proProfessionsList) {
				for (Profession professionEstimate : professionsEstimate) {
					if (professionPro.equals(professionEstimate)) {
						estimatesMatchesProProfList.add(estimate);
					}
				}
			}
		}
		return estimatesMatchesProProfList;

	}
}
