package fr.platform.plateo.business.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 */
@Entity
public class Estimate implements Serializable {

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate request_date;

	/**
	 *
	 */
	@Enumerated (EnumType.STRING)
	@Column(name="estimate_status")
	private EstimateStatus estimatestatus;

	/**
	 *
	 */
	private String work_address;

	/**
	 *
	 */
	private String work_postcode;

	/**
	 *
	 */
	private String work_city;

	/**
	 *
	 */
	private String access_description;

	/**
	 *
	 */
	private String usable_parking_space;

	/**
	 *
	 */
	private String work_comment;

	/**
	 *
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate work_deadline;

	/**
	 *
	 */
	private Double advance_payment;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "pro_id")
	private Pro pro;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getRequest_date() {
		return this.request_date;
	}

	public void setRequest_date(LocalDate request_date) {
		this.request_date = request_date;
	}

	public EstimateStatus getEstimatestatus() {
		return estimatestatus;
	}

	public void setEstimatestatus(EstimateStatus estimatestatus) {
		this.estimatestatus = estimatestatus;
	}

	public String getWork_address() {
		return this.work_address;
	}

	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}

	public String getWork_postcode() {
		return this.work_postcode;
	}

	public void setWork_postcode(String work_postcode) {
		this.work_postcode = work_postcode;
	}

	public String getWork_city() {
		return this.work_city;
	}

	public void setWork_city(String work_city) {
		this.work_city = work_city;
	}

	public String getAccess_description() {
		return this.access_description;
	}

	public void setAccess_description(String access_description) {
		this.access_description = access_description;
	}

	public String getUsable_parking_space() {
		return this.usable_parking_space;
	}

	public void setUsable_parking_space(String usable_parking_space) {
		this.usable_parking_space = usable_parking_space;
	}

	public String getWork_comment() {
		return this.work_comment;
	}

	public void setWork_comment(String work_comment) {
		this.work_comment = work_comment;
	}

	public LocalDate getWork_deadline() {
		return work_deadline;
	}

	public void setWork_deadline(LocalDate work_deadline) {
		this.work_deadline = work_deadline;
	}

	public Double getAdvance_payment() {
		return this.advance_payment;
	}

	public void setAdvance_payment(Double advance_payment) {
		this.advance_payment = advance_payment;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Pro getPro() {
		return this.pro;
	}

	public void setPro(Pro pro) {
		this.pro = pro;
	}

}