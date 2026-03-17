package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Athlete;
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

    public Athlete findAthleteByTaxCode(String taxCode) {
        Athlete athlete = em.find(Athlete.class, taxCode);
        if (athlete == null) {
            throw new IllegalArgumentException("Athlete with tax code " + taxCode + " not found.");
        }
        return athlete;
    }

    public void createNewSubscription(String taxCode, SubscriptionType subscriptionType, LocalDate startDate) {
        Athlete athlete = em.find(Athlete.class,taxCode);
        if (athlete != null) {
            Subscription subscription = new Subscription(subscriptionType, startDate);
            athlete.addSubscription(subscription);
            em.merge(athlete);
        }
        else {
            throw new IllegalArgumentException("Athlete with tax code " + taxCode + " not found.");
        }
    }
    public Subscription getActiveSubscription(String tax_code) {
        Athlete athlete = em.find(Athlete.class,tax_code);
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
        Athlete athlete = em.find(Athlete.class,tax_code);
        if (athlete != null) {
            return athlete.getSubscriptions();
        }
        else {
            throw new IllegalArgumentException("Athlete with tax code " + tax_code + " not found.");
        }
    }

}
