package fr.platform.plateo.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Pro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank(message = "Nom société obligatoire")
	@Column(name = "nom_societe", length = 100, nullable = false)
	private String nom_societe;

	@NotBlank(message = "Nom gérant obligatoire")
	@Column(name = "nom_pro", length = 50, nullable = false)
	private String nom_pro;

	@NotBlank(message = "Prénom gérant obligatoire")
	@Column(name = "prenom_pro", length = 100, nullable = false)
	private String prenom_pro;

	private String telephone_pro;

	@NotBlank(message = "Siren obligatoire")
	@Column(name = "siren", length = 9, nullable = false)
	private String siren;

	@NotBlank(message = "Email obligatoire")
	@Email(message = "Email au mauvais format")
	@Column(name = "email_pro", length = 50, nullable = false)
	private String email_pro;

	private String mot_passe_pro;

	private String adresse_pro;

	private Integer code_postal;

	private String ville_pro;

	private Blob kbis;

	private Blob logo_pro;

	private String description_activite_pro;

	private String effectif_pro;

	private Boolean statut_insc_pro;

	/*
	 * private Integer role_id;
	 * 
	 * private Integer grade_id;
	 */
	public Pro() {
	}

	public Pro(String nom_societe,
			String nom_pro,
			String prenom_pro,
			String email_pro,
			String siren,
			String mot_passe_pro, Integer code_postal) {
		this.nom_societe = nom_societe;
		this.nom_pro = nom_pro;
		this.prenom_pro = prenom_pro;
		this.siren = siren;
		this.email_pro = email_pro;
		this.mot_passe_pro = mot_passe_pro;
		this.code_postal = code_postal;
	}

	public String getNom_societe() {
		return nom_societe;
	}

	public void setNom_societe(String nom_societe) {
		this.nom_societe = nom_societe;
	}

	public String getNom_pro() {
		return nom_pro;
	}

	public void setNom_pro(String nom_pro) {
		this.nom_pro = nom_pro;
	}

	public String getPrenom_pro() {
		return prenom_pro;
	}

	public void setPrenom_pro(String prenom_pro) {
		this.prenom_pro = prenom_pro;
	}

	public String getTelephone_pro() {
		return telephone_pro;
	}

	public void setTelephone_pro(String telephone_pro) {
		this.telephone_pro = telephone_pro;
	}

	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}

	public String getEmail_pro() {
		return email_pro;
	}

	public void setEmail_pro(String email_pro) {
		this.email_pro = email_pro;
	}

	public String getMot_passe_pro() {
		return mot_passe_pro;
	}

	public void setMot_passe_pro(String mot_passe_pro) {
		this.mot_passe_pro = mot_passe_pro;
	}

	public String getAdresse_pro() {
		return adresse_pro;
	}

	public void setAdresse_pro(String adresse_pro) {
		this.adresse_pro = adresse_pro;
	}

	public Integer getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(Integer code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille_pro() {
		return ville_pro;
	}

	public void setVille_pro(String ville_pro) {
		this.ville_pro = ville_pro;
	}

	public Blob getKbis() {
		return kbis;
	}

	public void setKbis(Blob kbis) {
		this.kbis = kbis;
	}

	public Blob getLogo_pro() {
		return logo_pro;
	}

	public void setLogo_pro(Blob logo_pro) {
		this.logo_pro = logo_pro;
	}

	public String getDescription_activite_pro() {
		return description_activite_pro;
	}

	public void setDescription_activite_pro(String description_activite_pro) {
		this.description_activite_pro = description_activite_pro;
	}

	public String getEffectif_pro() {
		return effectif_pro;
	}

	public void setEffectif_pro(String effectif_pro) {
		this.effectif_pro = effectif_pro;
	}

	public Boolean getStatut_insc_pro() {
		return statut_insc_pro;
	}

	public void setStatut_insc_pro(Boolean statut_insc_pro) {
		this.statut_insc_pro = statut_insc_pro;
	}

	public Integer getId() {
		return id;
	}

}
