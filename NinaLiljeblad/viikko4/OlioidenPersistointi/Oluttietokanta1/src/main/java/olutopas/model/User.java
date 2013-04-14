/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.model;

/**
 *
 * @author lini
 */
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
    private String kayttajatunnus;
    
     @Id
    private Integer id;
     
     @OneToMany
    private Rating rating;

     
     public User(){
     }
     
     public User(String kayttajatunnus){
     this.kayttajatunnus=kayttajatunnus;
     }
    /**
     * @return the kayttajatunnus
     */
    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setRating(Rating rating){
    this.rating=rating;
    }
    public Rating getRating(){
        return rating;
    }
    /**
     * @param kayttajatunnus the kayttajatunnus to set
     */
    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
       
        return getKayttajatunnus();
    }     
}
