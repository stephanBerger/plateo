package fr.platform.plateo.business.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class EstimateTemp implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7666265143942698618L;

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
	@Column(name = "estimate_date")
	private LocalDate estimateTempDate;

	/**
	 *
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "validity_date")
	private LocalDate validityTempDate;

	/**
	 *
	 */
	@Column(name = "advance_payment")
	private Double advanceTempPayment;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "pro_id")
	private Pro pro;

	/**
	 *
	 */
	@ManyToOne
	@JoinColumn(name = "estimate_id")
	private Estimate estimate;

}