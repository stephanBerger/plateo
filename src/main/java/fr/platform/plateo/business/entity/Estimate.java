package fr.platform.plateo.business.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
	@DateTimeFormat
	@Column(name = "request_date")
	private LocalDateTime requestDate;

	/**
	 *
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "estimate_date")
	private LocalDate estimateDate;

	/**
	 *
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "validity_date")
	private LocalDate validityDate;

	/**
	*
	*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "begin_date")
	private LocalDate beginDate;

	/**
	 *
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "work_deadline")
	private LocalDate workDeadline;

	/**
	 *
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "estimate_status")
	private EstimateStatus estimateStatus;

	/**
	 *
	 */
	@Column(name = "work_address")
	private String workAddress;

	/**
	 *
	 */
	@Column(name = "work_postcode")
	private String workPostcode;

	/**
	 *
	 */
	@Column(name = "work_city")
	private String workCity;

	/**
	 *
	 */
	@Column(name = "access_description")
	private String accessDescription;

	/**
	 *
	 */
	@Column(name = "usable_parking_space")
	private String usableParkingSpace;

	/**
	 *
	 */
	@Column(name = "work_comment")
	private String workComment;

	/**
	 *
	 */
	@Column(name = "advance_payment")
	private Double advancePayment;

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

	public LocalDateTime getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public EstimateStatus getEstimateStatus() {
		return this.estimateStatus;
	}

	public void setEstimateStatus(EstimateStatus estimateStatus) {
		this.estimateStatus = estimateStatus;
	}

	public String getWorkAddress() {
		return this.workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getWorkPostcode() {
		return this.workPostcode;
	}

	public void setWorkPostcode(String workPostcode) {
		this.workPostcode = workPostcode;
	}

	public String getWorkCity() {
		return this.workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getAccessDescription() {
		return this.accessDescription;
	}

	public void setAccessDescription(String accessDescription) {
		this.accessDescription = accessDescription;
	}

	public String getUsableParkingSpace() {
		return this.usableParkingSpace;
	}

	public void setUsableParkingSpace(String usableParkingSpace) {
		this.usableParkingSpace = usableParkingSpace;
	}

	public String getWorkComment() {
		return this.workComment;
	}

	public void setWorkComment(String workComment) {
		this.workComment = workComment;
	}

	public LocalDate getWorkDeadline() {
		return this.workDeadline;
	}

	public void setWorkDeadline(LocalDate workDeadline) {
		this.workDeadline = workDeadline;
	}

	public Double getAdvancePayment() {
		return this.advancePayment;
	}

	public void setAdvancePayment(Double advancePayment) {
		this.advancePayment = advancePayment;
	}

	public LocalDate getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
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

	public LocalDate getEstimateDate() {
		return this.estimateDate;
	}

	public void setEstimateDate(LocalDate estimateDate) {
		this.estimateDate = estimateDate;
	}

	public LocalDate getValidityDate() {
		return this.validityDate;
	}

	public void setValidityDate(LocalDate validityDate) {
		this.validityDate = validityDate;
	}

}