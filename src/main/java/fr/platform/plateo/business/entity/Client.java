package fr.platform.plateo.business.entity;

import java.util.*;

/**
 * 
 */
public class Client {

    /**
     * Default constructor
     */
    public Client() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String client_lastname;

    /**
     * 
     */
    private String client_firstname;

    /**
     * 
     */
    private String client_email_address;

    /**
     * 
     */
    private String client_password;

    /**
     * 
     */
    private String client_phone_number;

    /**
     * 
     */
    private String client_address;

    /**
     * 
     */
    private String client_postcode;

    /**
     * 
     */
    private String client_city;

    /**
     * 
     */
    private Role client_role;

    /**
     * 
     */
    private List<Estimate> estimates;

}