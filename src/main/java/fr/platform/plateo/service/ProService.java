package fr.platform.plateo.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import fr.platform.plateo.entity.Pro;

@Service("proService")
public class ProService {
	
	
	
	private final ProRepository proRepository;

	public ProService(ProRepository proRep) {
		this.proRepository = proRep;
	}
	
	public boolean create(Pro pro) {
		boolean created = false;
		if(pro.getId() == null) {
			try {
				this.proRepository.save(pro);
				created = true;
			} catch (DataAccessException e) {
				System.out.println(pro.toString());
			}
		}
		return created;
	}

}
