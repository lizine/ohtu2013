package olutopas.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Beer {

    private String name;
    
    @Id
    private Integer id;
    
    @ManyToOne
    private Brewery brewery;
    
    @ManyToMany(mappedBy = "beers", cascade = CascadeType.ALL)
    List<Pub> pubs;

     public void setPubs(List<Pub> pubs) {
        this.pubs = pubs;
    }

    public List<Pub> getPubs() {
        return pubs;
    }   

    @Override
    public boolean equals(Object o) {
        Beer other = (Beer)o;
        
        return id == other.getId();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }       
    
    

    public void addToPubs(Pub pub) {
        if ( pubs==null  ) pubs = new ArrayList<Pub>();
        pubs.add(pub);
    }

    public void removePub(Pub pub) {
        pubs.remove(pub);
    }
    
    
    

    public List<Rating> getRatings() {
        return ratings;
    }
    
    @OneToMany
    private List<Rating> ratings;
    
    
    public Beer() {
    }
    
    public Beer(String name) {
        this.name = name;
    }        
    
     public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    
   
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }
    
    public double laskeKeskiarvo(){
    double ka = 0.0;
    int maara = getRatings().size();
        for (Rating rating : ratings) {
            ka= ka +rating.getbValue();
        }
        return ka/maara;
    }

    @Override
    public String toString() {
        // olioiden kannattaa sisäisestikin käyttää gettereitä oliomuuttujien sijaan
        // näin esim. olueeseen liittyvä panimo tulee varmasti ladattua kannasta
        return getName() + " ("+getBrewery().getName()+")" + "ratings given: " + getRatings().size() + " average: " + laskeKeskiarvo();
    }     
}
