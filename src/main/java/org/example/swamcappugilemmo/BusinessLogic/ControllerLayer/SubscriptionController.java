package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;
import java.util.ArrayList;

@ApplicationScoped
public class SubscriptionController {
    @Inject
    private AthleteDAO athleteDAO; //non ha il suo dao, ma usa quello dell'atleta per gestire le sottoscrizioni

    /// ////////////// SUBSCRIPTION MANAGEMENT ///////////////
    @Transactional
    public void crateNewSubscription(String tax_code, SubscriptionType subscriptionType, LocalDate startDate) {
        //non ha senso creare un nuovo abbonamento se il codice fiscale è vuoto, se il tipo di abbonamento è null o se la data di inizio è precedente alla data odierna
        if (tax_code.equals(" ")|| subscriptionType == null || startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid input for creating a new subscription.");
        }
            athleteDAO.createNewSubscription(tax_code, subscriptionType, startDate);
    }
    @Transactional
    public Subscription getActiveSubscription(String tax_code) {
        return athleteDAO.getActiveSubscription(tax_code);
    }
    // vedi lo storico delle sottoscrizioni di un atleta
    @Transactional
    public ArrayList<Subscription> getSubscriptions(String tax_code) {
        return athleteDAO.getSubscriptions(tax_code);
    }
}
