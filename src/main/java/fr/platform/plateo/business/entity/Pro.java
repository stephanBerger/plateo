package fr.platform.plateo.business.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pro")
public class Pro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Entrer la raison sociale")
	@Column(length = 45, nullable = false)
	private String company_name;

	@NotBlank(message = "Entrer le nom du gérant")
	@Column(length = 45, nullable = false)
	private String manager_lastname;

	@NotBlank(message = "Entrer le prénom du gérant")
	@Column(length = 45, nullable = false)
	private String manager_firstname;

	private String pro_phone_number;
	
	@NotBlank(message = "Entrer le Siret de la société")
	@Column(length = 14, nullable = false)
	private String siret;
	
	@NotBlank(message = "Entrer votre email")
	@Column(length = 45, nullable = false)
	private String pro_email_adress;

	@NotBlank(message = "Entrer votre mot de passe")
	@Column(length = 45, nullable = false)
	private String pro_password;
	
	@NotBlank(message = "Entrer votre mot de passe")
	@Column(length = 45, nullable = false)
	private String pro_adress;

	private String pro_postcode;

	private String pro_city;

	private Blob kbis;

	private Blob logo;

	private String activity_description;

	private Blob identity_card;

	private String staffing;

	private Role pro_role;

	public Pro() {
	}

	public Pro(String company_name, String manager_lastname, String manager_firstname,
			String siret, String pro_password, String pro_postcode) {
		this.company_name = company_name;
		this.manager_lastname = manager_lastname;
		this.manager_firstname = manager_firstname;
		this.siret = siret;
		this.pro_password = pro_password;
		this.pro_postcode = pro_postcode;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getManager_lastname() {
		return manager_lastname;
	}

	public void setManager_lastname(String manager_lastname) {
		this.manager_lastname = manager_lastname;
	}

	public String getManager_firstname() {
		return manager_firstname;
	}

	public void setManager_firstname(String manager_firstname) {
		this.manager_firstname = manager_firstname;
	}

	public String getPro_phone_number() {
		return pro_phone_number;
	}

	public void setPro_phone_number(String pro_phone_number) {
		this.pro_phone_number = pro_phone_number;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getPro_email_adress() {
		return pro_email_adress;
	}

	public void setPro_email_adress(String pro_email_adress) {
		this.pro_email_adress = pro_email_adress;
	}

	public String getPro_password() {
		return pro_password;
	}

	public void setPro_password(String pro_password) {
		this.pro_password = pro_password;
	}

	public String getPro_adress() {
		return pro_adress;
	}

	public void setPro_adress(String pro_adress) {
		this.pro_adress = pro_adress;
	}

	public String getPro_postcode() {
		return pro_postcode;
	}

	public void setPro_postcode(String pro_postcode) {
		this.pro_postcode = pro_postcode;
	}

	public String getPro_city() {
		return pro_city;
	}

	public void setPro_city(String pro_city) {
		this.pro_city = pro_city;
	}

	public Blob getKbis() {
		return kbis;
	}

	public void setKbis(Blob kbis) {
		this.kbis = kbis;
	}

	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

	public String getActivity_description() {
		return activity_description;
	}

	public void setActivity_description(String activity_description) {
		this.activity_description = activity_description;
	}

	public Blob getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(Blob identity_card) {
		this.identity_card = identity_card;
	}

	public String getStaffing() {
		return staffing;
	}

	public void setStaffing(String staffing) {
		this.staffing = staffing;
	}

	public Role getPro_role() {
		return pro_role;
	}

	public void setPro_role(Role pro_role) {
		this.pro_role = pro_role;
	}

	public Integer getId() {
		return id;
	}

}