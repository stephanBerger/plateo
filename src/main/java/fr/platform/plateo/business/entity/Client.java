package fr.platform.plateo.business.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 */
@Entity
@Table( name = "client" )
public class Client implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Client() {
    }

    public Client( String client_lastname, String client_firstname, String client_email_address, String client_password,
            String client_phone_number, String client_address, String client_postcode, String client_city, Role role ) {

        this.client_lastname = client_lastname;
        this.client_firstname = client_firstname;
        this.client_email_address = client_email_address;
        this.client_password = client_password;
        this.client_phone_number = client_phone_number;
        this.client_address = client_address;
        this.client_postcode = client_postcode;
        this.client_city = client_city;
        this.role = role;
    }

    /**
     * 
     */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    /**
     * 
     */
    @Column( length = 45, nullable = false )
    private String  client_lastname;

    /**
     * 
     */
    @Column( length = 45, nullable = false )
    private String  client_firstname;

    /**
     * 
     */
    @Column( length = 45, nullable = false )
    private String  client_email_address;

    /**
     * 
     */
    @Column( nullable = false )
    private String  client_password;

    /**
     * 
     */
    @Column( length = 20 )
    private String  client_phone_number;

    /**
     * 
     */
    @Column( length = 255 )
    private String  client_address;

    /**
     * 
     */
    @Column( length = 10 )
    private String  client_postcode;

    /**
     * 
     */
    @Column( length = 100 )
    private String  client_city;

    /**
     * 
     */
    @OneToOne
    private Role    role;

    /**
     * méthodes pour security
     */
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getUsername() {
        return client_email_address;
    }

    @Override
    public String getPassword() {
        return client_password;
    }

    public String getClient_lastname() {
        return client_lastname;
    }

    public void setClient_lastname( String client_lastname ) {
        this.client_lastname = client_lastname;
    }

    public String getClient_firstname() {
        return client_firstname;
    }

    public void setClient_firstname( String client_firstname ) {
        this.client_firstname = client_firstname;
    }

    public String getClient_email_address() {
        return client_email_address;
    }

    public void setClient_email_address( String client_email_address ) {
        this.client_email_address = client_email_address;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password( String client_password ) {
        this.client_password = client_password;
    }

    public String getClient_phone_number() {
        return client_phone_number;
    }

    public void setClient_phone_number( String client_phone_number ) {
        this.client_phone_number = client_phone_number;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address( String client_address ) {
        this.client_address = client_address;
    }

    public String getClient_postcode() {
        return client_postcode;
    }

    public void setClient_postcode( String client_postcode ) {
        this.client_postcode = client_postcode;
    }

    public String getClient_city() {
        return client_city;
    }

    public void setClient_city( String client_city ) {
        this.client_city = client_city;
    }

    public Role getRole() {
        return role;
    }

    public void setRole( Role role ) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

}