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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		ProPhotos other = (ProPhotos) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
