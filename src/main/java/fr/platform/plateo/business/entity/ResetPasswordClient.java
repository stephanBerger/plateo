package fr.platform.plateo.business.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resetpasswordclient")
public class ResetPasswordClient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String token;

	@Column(nullable = false)
	private Date expiryDate;

	@OneToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private Client client;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setExpiryDate(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expiryDate = now.getTime();
	}

	public boolean isExpired() {
		return new Date().after(this.expiryDate);
	}

}
