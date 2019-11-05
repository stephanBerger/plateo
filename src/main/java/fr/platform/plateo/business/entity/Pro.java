package fr.platform.plateo.business.entity;

import java.sql.Blob;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Entity
@Table(name = "pro")
public class Pro implements UserDetailsService, UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Entrez la raison sociale")
	@Column(name = "company_name", length = 45, nullable = false)
	private String companyName;

	@NotBlank(message = "Entrez le nom du gérant")
	@Column(name = "manager_lastname", length = 45, nullable = false)
	private String managerLastname;

	@NotBlank(message = "Entrez le prénom du gérant")
	@Column(name = "manager_firstname", length = 45, nullable = false)
	private String managerFirstname;

	@Column(name = "pro_phone_number", length = 20)
	private String proPhoneNumber;

	@NotBlank(message = "Entrez le Siret de la société")
	@Column(name = "siret", length = 14, nullable = false)
	private String siret;

	@NotBlank(message = "Entrez votre email")
	@Column(name = "pro_email_address", length = 45, nullable = false)
	private String proEmailAddress;

	@NotBlank(message = "Entrez votre mot de passe")
	@Column(name = "pro_password", length = 45, nullable = false)
	private String proPassword;

	@Column(name = "pro_address")
	private String proAddress;

	@NotBlank(message = "Entrez votre code postal")
	@Column(name = "pro_postcode", length = 10, nullable = false)
	private String proPostcode;

	@Column(name = "pro_city")
	private String proCity;

	@Column(name = "kbis")
	private Blob kbis;

	@Column(name = "logo")
	private Blob logo;

	@Column(name = "activity_description")
	private String activityDescription;

	@Column(name = "identity_card")
	private Blob identityCard;

	@Column(name = "staffing")
	private String staffing;

	@Column(name = "pro_role")
	private Role proRole;

	public Pro() {
	}

//	public Pro(String company_name, String manager_lastname, String manager_firstname, String siret,
//			String pro_password, String pro_postcode) {
//		this.companyName = company_name;
//		this.managerLastname = manager_lastname;
//		this.managerFirstname = manager_firstname;
//		this.siret = siret;
//		this.proPassword = pro_password;
//		this.proPostcode = pro_postcode;
//	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getManagerLastname() {
		return managerLastname;
	}

	public void setManagerLastname(String managerLastname) {
		this.managerLastname = managerLastname;
	}

	public String getManagerFirstname() {
		return managerFirstname;
	}

	public void setManagerFirstname(String managerFirstname) {
		this.managerFirstname = managerFirstname;
	}

	public String getProPhoneNumber() {
		return proPhoneNumber;
	}

	public void setProPhoneNumber(String proPhoneNumber) {
		this.proPhoneNumber = proPhoneNumber;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getProEmailAddress() {
		return proEmailAddress;
	}

	public void setProEmailAddress(String proEmailAddress) {
		this.proEmailAddress = proEmailAddress.toLowerCase();
	}

	public String getProPassword() {
		return proPassword;
	}

	public void setProPassword(String proPassword) {
		this.proPassword = proPassword;
	}

	public String getProAddress() {
		return proAddress;
	}

	public void setProAddress(String proAddress) {
		this.proAddress = proAddress;
	}

	public String getProPostcode() {
		return proPostcode;
	}

	public void setProPostcode(String proPostcode) {
		this.proPostcode = proPostcode;
	}

	public String getProCity() {
		return proCity;
	}

	public void setProCity(String proCity) {
		this.proCity = proCity;
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

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public Blob getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(Blob identityCard) {
		this.identityCard = identityCard;
	}

	public String getStaffing() {
		return staffing;
	}

	public void setStaffing(String staffing) {
		this.staffing = staffing;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}