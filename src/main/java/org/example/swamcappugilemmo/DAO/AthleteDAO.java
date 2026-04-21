package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AthleteDAO {

    @PersistenceContext
    private EntityManager em;

    public void saveAthlete(Athlete newAthlete) {
        em.persist(newAthlete);
    }

    public List<Athlete> findAll() {
        return em.createQuery("SELECT a FROM Athlete a", Athlete.class).getResultList();
    }

    public Athlete findById(long id) {
        Athlete athlete = em.find(Athlete.class, id);
        if (athlete == null) {
            throw new IllegalArgumentException("Athlete with id " + id + " not found.");
        }
        return athlete;
    }

    public Athlete findAthleteByTaxCode(String taxCode) {
        try {
            return em.createQuery("SELECT a FROM Athlete a WHERE a.tax_code = :tc", Athlete.class)
                    .setParameter("tc", taxCode)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Ora restituisce null correttamente se non lo trova
        } catch (jakarta.persistence.NonUniqueResultException e) {
            throw new RuntimeException("Errore critico: database corrotto, trovati più atleti con lo stesso codice fiscale!");
        }
    }

    public Athlete findAthleteByUsername(String username) {
        Athlete athlete;
        athlete = em.createQuery("SELECT a FROM Athlete a WHERE a.username = :un", Athlete.class)
                .setParameter("un", username)
                .getSingleResult();
        if (athlete == null) {
            throw new IllegalArgumentException("Athlete with username " + username + " not found.");
        }
        return athlete;
    }

    public void createNewSubscription(String taxCode, Subscription subscription) {
        Athlete athlete = findAthleteByTaxCode(taxCode);
        if (athlete != null) {
            //Subscription subscription = new Subscription(subscriptionType, startDate);
            athlete.addSubscription(subscription);
            em.merge(athlete);
        }
        else {
            throw new IllegalArgumentException("Athlete with tax code " + taxCode + " not found.");
        }
    }
    public Subscription getActiveSubscription(String tax_code) {
        Athlete athlete = findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            return athlete.getSubscriptions().stream()
                    .filter(Subscription::isActive)
                    .findFirst()
                    .orElse(null);
        }
        else {
            throw new IllegalArgumentException("Athlete with tax code " + tax_code + " not found.");
        }
    }
    public List<Subscription> getSubscriptions(String tax_code){
        Athlete athlete = findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            return athlete.getSubscriptions();
        }
        else {
            throw new IllegalArgumentException("Athlete with tax code " + tax_code + " not found.");
        }
    }

    public void updateAthlete(Athlete athlete){
        Athlete newA = em.merge(athlete);
        if(newA == null){
            throw new RuntimeException("Update failed");
        }
        System.out.println("Atleta aggiornato");
    }

    public void deleteAthlete(String username){
        //TODO correggere questo metodo, find non funziona con username, bisogna fare una query per trovare l'atleta con quel username e poi eliminarlo
        Athlete a = em.find(Athlete.class, username);
        if(a == null){
            throw new RuntimeException("Atleta non trovato");
        }
        em.remove(a);
        System.out.println("Atleta eliminato");
    }

    public void deleteAthlete(Long id){
        Athlete a = em.find(Athlete.class, id);
        if(a == null){
            throw new RuntimeException("Atleta non trovato");
        }
        em.remove(a);
        System.out.println("Atleta eliminato");
    }

}
