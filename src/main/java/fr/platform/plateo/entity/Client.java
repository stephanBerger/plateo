package fr.platform.plateo.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "client")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    // @Basic il y est par défault 
    @NotBlank(message = "Nom obligatoire")
    @Column(name = "nom", length=50, nullable = false)
    private String nom;
    
    
    @NotBlank(message = "Prénom obligatoire")
    @Column(name = "prenom", length=50, nullable = false)
    private String prenom;
    
    @NotBlank(message = "Email obligatoire")
    @Email(message = "Email au mauvais format")
    @Column(name = "email", length=50, nullable = false)
    private String email;
    
    @NotBlank(message = "Mot de passe obligatoire")
    @Column(name = "mot_de_passe", length=50, nullable = false)
    private String mot_de_passe;
    
 
    @Column(name = "telephone")
    private int telephone;
    
    @Column(name = "adresse", length=50)
    private String adresse;
    
    
    @Column(name = "code_postal")
    private int code_postal;

    @Column(name = "ville", length=50)
    private String ville;
    
      
    public Client() {}

    // Constructeurs
	public Client(long id,String nom,String prenom,String email,String mot_de_passe, int telephone, String adresse, int code_postal,
			String ville) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mot_de_passe = mot_de_passe;
		this.telephone = telephone;
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	
	
	
	//getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(int code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	

	
	

    
    
    
   
}