package fr.platform.plateo.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ProPhotos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	String description;

	@ManyToOne
	@JoinColumn(name = "pro_id")
	private Pro pro;

	@Column(name = "pro_photo")
	@Lob
	private byte[] proPhoto;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Pro getPro() {
		return this.pro;
	}

	public void setPro(Pro pro) {
		this.pro = pro;
	}

	public byte[] getProPhoto() {
		return this.proPhoto;
	}

	public void setProPhoto(byte[] proPhoto) {
		this.proPhoto = proPhoto;
	}

	public Integer getId() {
		return this.id;
	}

}
