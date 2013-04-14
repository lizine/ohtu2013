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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

    @Entity
public class Rating {
    @Id
    private Integer id;

    @ManyToOne
    private Beer beer;

    @ManyToOne
    private User user;
 
    private int bValue;
    
    public Rating(){
    }
    
    public Rating(Beer beer, User user, int bValue){
    this.beer = beer;
    this.user = user;
    this.bValue=bValue;
    }

    public Integer getId() {
        return id;
    }

    public int getbValue() {
        return bValue;
    }

    public Beer getBeer() {
        return beer;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setbValue(int bValue) {
        this.bValue = bValue;
    }
    
}
