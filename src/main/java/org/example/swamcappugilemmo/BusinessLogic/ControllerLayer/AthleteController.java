package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.inject.Inject;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;

public class AthleteController {
    @Inject
    private AthleteDAO athleteDAO;

    @Transactional
    public void crateNewSubscription(String tax_code, SubscriptionType subscriptionType, LocalDate startDate) {
        athleteDAO.createNewSubscription(tax_code, subscriptionType, startDate);
    }
    @Transactional
    public void registerNewAthlete(String name, String surname, String username, String password, String email, String phone_number,
                                String tax_code, LocalDate birth_date, String height, String weight, SubscriptionType subscriptionType, LocalDate startDate) {
        Subscription initialSubscription = new Subscription(subscriptionType, startDate);
        Athlete newAthlete = new Athlete(name, surname, username, password, email, phone_number, tax_code, birth_date, height, weight, initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
    }
}
