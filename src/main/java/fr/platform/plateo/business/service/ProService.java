package fr.platform.plateo.business.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.platform.plateo.business.entity.Pro;
import fr.platform.plateo.business.entity.ProPhotos;
import fr.platform.plateo.persistence.ProPhotosRepository;
import fr.platform.plateo.persistence.ProRepository;
import fr.platform.plateo.presentation.ClientController;

/**
 *
 */
@Service
public class ProService implements UserDetailsService {

	@Autowired
	private ProRepository proRepository;

	@Autowired
	private ProPhotosRepository proPhotosRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Pro pro = this.proRepository.findOneByProEmailAddress(email);
		return pro;
	}

	public void create(Pro pro) {
		this.proRepository.save(pro);
	}

	public Pro read(Integer id) {
		return this.proRepository.getOne(id);
	}

	public List<Pro> readAll() {
		return this.proRepository.findAll();
	}

	public void idupdate(Pro pro) {
		this.proRepository.save(pro);
	}

	public void delete(Integer id) {
		this.proRepository.deleteById(id);
	}

	public Pro findEmail(String email) {
		return this.proRepository.findOneByProEmailAddress(email);
	}

	public void addPhotos(Integer id, List<MultipartFile> photos) {
		Pro pro = this.proRepository.getOne(id);
		for (MultipartFile photo : photos) {
			ProPhotos pho = new ProPhotos();
			try {
				pho.setProPhoto(photo.getBytes());
				pho.setPro(pro);
			} catch (IOException e) {
				ProService.LOGGER.error("Can't turn photo into bytes.");
			}
			pro.addListProPhotos(pho);
		}
		this.proRepository.save(pro);
	}

	public void deletePhoto(Integer idPhoto) {
		this.proPhotosRepository.deleteById(idPhoto);
	}

	public Optional<Pro> findId(Integer id) {
		return this.proRepository.findById(id);
	}

}