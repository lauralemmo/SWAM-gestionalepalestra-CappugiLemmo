package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRegistrationRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.AthleteMapper;
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

    @Inject
    private AthleteMapper athleteMapper;

    /// ////////////// ATHLETE MANAGEMENT ///////////////
    /*@Transactional
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
    } //vecchio metodo, ora uso quello con i mapper

    @Transactional
    public void registerNewAthlete(Athlete newAthlete, SubscriptionType subscriptionType, LocalDate startDate) {
        Subscription initialSubscription = new Subscription(subscriptionType, startDate);
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
    }*/

    @Transactional public void registerNewAthlete(AthleteRegistrationRequestDTO request) {
        Athlete newAthlete = athleteMapper.toEntity(request);
        Subscription initialSubscription = new Subscription(request.getSubscriptionType(), request.getStartDate());
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);

    }

    @Transactional
    public AthleteResponseDTO getAthleteByTaxCode(String tax_code) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            return athleteMapper.toDto(athlete);
        } else {
            return null; // O gestisci l'errore come preferisci
        }
    }

    @Transactional
    public void updateAthleteUsername(String tax_code, String request) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            athlete.setUsername(request);
        }
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
