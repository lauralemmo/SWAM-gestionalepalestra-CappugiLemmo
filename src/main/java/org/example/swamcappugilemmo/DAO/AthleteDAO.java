package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;

@ApplicationScoped
public class AthleteDAO {

    @PersistenceContext
    private EntityManager em;


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

    public void saveAthlete(Athlete newAthlete) {
        em.persist(newAthlete);
    }
}
