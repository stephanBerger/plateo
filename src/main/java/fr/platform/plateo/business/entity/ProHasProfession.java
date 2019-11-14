package fr.platform.plateo.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "pro_has_profession" )
public class ProHasProfession implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3266155058796637067L;

    /**
    *
    */
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer           id;

    /**
     *
     */
    @ManyToOne
    @JoinColumn( name = "pro_id" )
    private Pro               pro;

    /**
     *
     */
    @ManyToOne
    @JoinColumn( name = "profession_id" )
    private Profession        profession;

    public String toString() {
        return this.profession.getName();
    }
}
