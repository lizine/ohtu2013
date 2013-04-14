package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Pub;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);

    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }

        System.out.println("Welcome!");
        
        System.out.println("Login (give ? to register a new user)");
        
        
        String komento=scanner.nextLine();
        
        if (komento.equals("?")){
            System.out.println("Register a new user");
            registerUser();
            
        }
        else {
        login();
        }

        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer();
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
               
            } else if (command.equals("6")){
                listBeers();
            
            } 
             else if (command.equals("7")){
                addBrewery();
            
            }
             else if (command.equals("8")){
                 deleteBrewery();
             }
             else if (command.equals("y")){
                 listUsers();
             
             }
              else if (command.equals("l")){
                listBeersFromPub();
                
            }  else if (command.equals("p")){
                listPubsAndBeers();
            }   else if (command.equals("x")){
                 removeBeerFromPub();
            
            }
             else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("0   quit");
        System.out.println("y   list users");
        System.out.println("x   delete beer from pub");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);
        
        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());        
        brewery = server.find(Brewery.class, brewery.getId());        
        brewery.addBeer(b);
        server.save(brewery);
        
        server.save(new Brewery("Paulaner"));
    }

    private void findBeer() {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println("found: " + foundBeer);
        System.out.println("give rating (leave emtpy if not): ");
        String bValue = scanner.nextLine();
 
         
        
            if (!bValue.isEmpty()){
               User kayttaja = server.find(User.class).where().like("id", "1").findUnique();
               int arvo = Integer.parseInt(bValue);
                Rating rating = new Rating(foundBeer, kayttaja, arvo);

                     server.save(rating);
            }
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }
    
     private void listBeers() {
        List<Beer> beers = server.find(Beer.class).findList();
        for (Beer beer : beers) {
            System.out.println(beer);
        }
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }
    
    private void addBrewery(){
        System.out.println("Give Brewerys name: ");
        String name = scanner.nextLine();
        
        Brewery exists = server.find(Brewery.class).where().like("name", name).findUnique();
         if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }
         Brewery uusipanimo = new Brewery(name);
         server.save(uusipanimo);
         
    }
    private void registerUser(){
        System.out.println("Give username: ");
        String kayttajatunnus= scanner.nextLine();
        
        User exists = server.find(User.class).where().like("kayttajatunnus", kayttajatunnus).findUnique();
         if (exists != null) {
            System.out.println(kayttajatunnus + " exists already");
            return;
        }
         User uusikayttaja = new User(kayttajatunnus);
         server.save(uusikayttaja);
         System.out.println("user created!");
         login();
    }
    private void login(){
        System.out.println("username:");
        String kayttajatunnus= scanner.nextLine();
     User exists = server.find(User.class).where().like("kayttajatunnus", kayttajatunnus).findUnique();
         if (exists != null) {
            System.out.println("welcome!");           
        }
         else{
        System.out.println("User not found");
        registerUser();
        
    }
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }
        

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }
     private void deleteBrewery() {
        System.out.print("brewery to delete: ");
        String n = scanner.nextLine();
        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", n).findUnique();

        if (breweryToDelete == null) {
            System.out.println(n + " not found");
            return;
        }
        

        server.delete(breweryToDelete);
        System.out.println("deleted: " + breweryToDelete);

    }

    private void listUsers() {
           List<User> users = server.find(User.class).findList();
        for (User user : users) {
            System.out.println(user);
        }
    }
     private void addBeerToPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }
        
        pub.addBeer(beer);
        server.save(pub);
    }
     private void removeBeerFromPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }
        
        pub.removeBeer(beer);
        server.save(pub);
    }

    private void listBeersFromPub() {
        System.out.println("From what pub: ");
        String nimi = scanner.nextLine();
        Pub pubi = server.find(Pub.class).where().like("name", nimi).findUnique();
        
        for (Beer beer : pubi.getBeers()) {
            System.out.println(beer);
        }
        
    }

    private void listPubsAndBeers() {
         List<Pub> pubs = server.find(Pub.class).findList();
        for (Pub pub : pubs) {
            System.out.println(pub);
             for (Beer beer : pub.getBeers()) {
            System.out.println(beer);
        }
        }
    }
}
