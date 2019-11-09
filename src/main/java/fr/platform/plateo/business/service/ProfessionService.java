package fr.platform.plateo.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.platform.plateo.business.entity.Profession;
import fr.platform.plateo.persistence.ProfessionRepository;

@Service
public class ProfessionService {

	@Autowired
	private ProfessionRepository repo;

	public List<Profession> getAll() {
		return this.repo.findAll();
	}

	public Profession read(Integer id) {
		return this.repo.getOne(id);
	}
}
