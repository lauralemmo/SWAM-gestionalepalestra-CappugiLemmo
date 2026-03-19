package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;
import java.util.ArrayList;

@Dependent
public class AthleteController {
    @Inject
    private AthleteDAO athleteDAO;

    /// ////////////// ATHLETE MANAGEMENT ///////////////
    @Transactional
    public void registerNewAthlete(String name, String surname, String username, String password, String email, String phone_number,
                                String tax_code, LocalDate birth_date, String height, String weight, SubscriptionType subscriptionType, LocalDate startDate) {
        Subscription initialSubscription = new Subscription(subscriptionType, startDate);
        Athlete newAthlete = new Athlete(tax_code,
                name,
                surname,
                username,
                password,
                email,
                phone_number,
                birth_date,
                height,
                weight);
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
    }

    @Transactional
    public void registerNewAthlete(Athlete newAthlete, SubscriptionType subscriptionType, LocalDate startDate) {
        Subscription initialSubscription = new Subscription(subscriptionType, startDate);
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
    }

    @Transactional
    public Athlete getAthleteByTaxCode(String tax_code) {
        return athleteDAO.findAthleteByTaxCode(tax_code);
    }

   /* @Transactional
    public void updateAthleteInfo(String tax_code, String height, String weight) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            athlete.setHeight(height);
            athlete.setWeight(weight);
            athleteDAO.updateAthlete(athlete);
        }
    }*/

}
