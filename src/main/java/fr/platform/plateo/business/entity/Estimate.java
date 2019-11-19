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
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer           id;

    /**
     *
     */
    @Column( name = "request_date" )
    private LocalDate         requestDate;

    /**
     *
     */
    @Enumerated( EnumType.STRING )
    @Column( name = "estimate_status" )
    private EstimateStatus    estimateStatus;

    /**
     *
     */
    @Column( name = "work_address" )
    private String            workAddress;

    /**
     *
     */
    @Column( name = "work_postcode" )
    private String            workPostcode;

    /**
     *
     */
    @Column( name = "work_city" )
    private String            workCity;

    /**
     *
     */
    @Column( name = "access_description" )
    private String            accessDescription;

    /**
     *
     */
    @Column( name = "usable_parking_space" )
    private String            usableParkingSpace;

    /**
     *
     */
    @Column( name = "work_comment" )
    private String            workComment;

    /**
     *
     */
    @Column( name = "work_deadline" )
    private String            workDeadline;

    /**
     *
     */
    @Column( name = "advance_payment" )
    private Double            advancePayment;

    /**
     *
     */
    @ManyToOne
    @JoinColumn( name = "client_id" )
    private Client            client;

    /**
     *
     */
    @ManyToOne
    @JoinColumn( name = "pro_id" )
    private Pro               pro;

    public Integer getId() {
        return this.id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate( LocalDate requestDate ) {
        this.requestDate = requestDate;
    }

    public EstimateStatus getEstimateStatus() {
        return estimateStatus;
    }

    public void setEstimateStatus( EstimateStatus estimateStatus ) {
        this.estimateStatus = estimateStatus;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress( String workAddress ) {
        this.workAddress = workAddress;
    }

    public String getWorkPostcode() {
        return workPostcode;
    }

    public void setWorkPostcode( String workPostcode ) {
        this.workPostcode = workPostcode;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity( String workCity ) {
        this.workCity = workCity;
    }

    public String getAccessDescription() {
        return accessDescription;
    }

    public void setAccessDescription( String accessDescription ) {
        this.accessDescription = accessDescription;
    }

    public String getUsableParkingSpace() {
        return usableParkingSpace;
    }

    public void setUsableParkingSpace( String usableParkingSpace ) {
        this.usableParkingSpace = usableParkingSpace;
    }

    public String getWorkComment() {
        return workComment;
    }

    public void setWorkComment( String workComment ) {
        this.workComment = workComment;
    }

    public String getWorkDeadline() {
        return workDeadline;
    }

    public void setWorkDeadline( String workDeadline ) {
        this.workDeadline = workDeadline;
    }

    public Double getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment( Double advancePayment ) {
        this.advancePayment = advancePayment;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient( Client client ) {
        this.client = client;
    }

    public Pro getPro() {
        return this.pro;
    }

    public void setPro( Pro pro ) {
        this.pro = pro;
    }

}