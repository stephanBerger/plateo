package fr.platform.plateo.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_detail_price")
public class ServiceDetailPrice implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3063642547333902839L;

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "estimate_has_service_id")
	private EstimateHasService estimateHasService;

	/**
	 *
	 */
	@Column(name = "name_detail_service")
	private Double nameDetailService;

	/**
	 *
	 */
	@Column(name = "datail_price")
	private Double detailPrice;

	/**
	 *
	 */
	@Column(name = "travel_charge_price")
	private Double travelChargePrice;

	/**
	 *
	 */
	@Column(name = "workforce_price")
	private Double workforcePrice;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstimateHasService getEstimateHasService() {
		return this.estimateHasService;
	}

	public void setEstimateHasService(EstimateHasService estimateHasService) {
		this.estimateHasService = estimateHasService;
	}

	public Double getDetailPrice() {
		return this.detailPrice;
	}

	public void setDetailPrice(Double detailPrice) {
		this.detailPrice = detailPrice;
	}

	public Double getTravelChargePrice() {
		return this.travelChargePrice;
	}

	public void setTravelChargePrice(Double travelChargePrice) {
		this.travelChargePrice = travelChargePrice;
	}

	public Double getWorkforcePrice() {
		return this.workforcePrice;
	}

	public void setWorkforcePrice(Double workforcePrice) {
		this.workforcePrice = workforcePrice;
	}

}
