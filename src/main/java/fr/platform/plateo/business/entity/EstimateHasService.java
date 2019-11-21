package fr.platform.plateo.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "estimate_has_service")
public class EstimateHasService implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2301537539869858022L;

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 *
	 */
	private Integer processid;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "estimate_id")
	private Estimate estimate;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Estimate getEstimate() {
		return this.estimate;
	}

	public void setEstimate(Estimate estimate) {
		this.estimate = estimate;
	}

	public Integer getProcessid() {
		return this.processid;
	}

	public void setProcessid(Integer processid) {
		this.processid = processid;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}