package fr.platform.plateo.business.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

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

    /**
     * 
     */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    /**
     * 
     */
    @NotBlank( message = "Entrer le nom" )
    @Column( name = "client_lastname", length = 45, nullable = false )
    private String  clientLastname;

    /**
     * 
     */
    @NotBlank( message = "Entrer le prénom" )
    @Column( name = "client_firstname", length = 45, nullable = false )
    private String  clientFirstname;

    /**
     * 
     */
    @NotBlank( message = "Entrer une adresse email valide" )
    @Column( name = "client_email_address", length = 45, nullable = false )
    private String  clientEmailAddress;

    /**
     * 
     */
    @NotBlank( message = "Entrer un mot de passe valide" )
    @Column( name = "client_password", nullable = false )
    private String  ClientPassword;

    /**
     * 
     */
    @Column( name = "client_phone_number", length = 20 )
    private String  clientPhoneNumber;

    /**
     * 
     */
    @Column( name = "client_address", length = 255 )
    private String  clientAddress;

    /**
     * 
     */
    @Column( name = "client_postcode", length = 10 )
    private String  clientPostcode;

    /**
     * 
     */
    @Column( name = "client_city", length = 100 )
    private String  clientCity;

    /**
     * 
     */
    @ManyToOne
    @JoinColumn( name = "role_id" )
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

    public Role getRole() {
        return role;
    }

    public void setRole( Role role ) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getClientLastname() {
        return clientLastname;
    }

    public void setClientLastname( String clientLastname ) {
        this.clientLastname = clientLastname;
    }

    public String getClientFirstname() {
        return clientFirstname;
    }

    public void setClientFirstname( String clientFirstname ) {
        this.clientFirstname = clientFirstname;
    }

    public String getClientEmailAddress() {
        return clientEmailAddress;
    }

    public void setClientEmailAddress( String clientEmailAddress ) {
        this.clientEmailAddress = clientEmailAddress;
    }

    public String getClientPassword() {
        return ClientPassword;
    }

    public void setClientPassword( String clientPassword ) {
        ClientPassword = clientPassword;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber( String clientPhoneNumber ) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress( String clientAddress ) {
        this.clientAddress = clientAddress;
    }

    public String getClientPostcode() {
        return clientPostcode;
    }

    public void setClientPostcode( String clientPostcode ) {
        this.clientPostcode = clientPostcode;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity( String clientCity ) {
        this.clientCity = clientCity;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
    

}