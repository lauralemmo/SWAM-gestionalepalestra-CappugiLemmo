package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.SubscriptionMapper;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SubscriptionController {
    @Inject
    private AthleteDAO athleteDAO; //non ha il suo dao, ma usa quello dell'atleta per gestire le sottoscrizioni
    @Inject
    private SubscriptionMapper subscriptionMapper;

    /// ////////////// SUBSCRIPTION MANAGEMENT ///////////////

    @Transactional
    public SubscriptionResponseDTO createNewSubscription(SubscriptionRequestDTO subscriptionRequestDTO) {
        if (subscriptionRequestDTO.getStartDate() == null || subscriptionRequestDTO.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data di inizio non valida: deve essere oggi o in futuro.");
        }

        Athlete athlete = athleteDAO.findById(subscriptionRequestDTO.getIdUser());
        
        // Calcoliamo le date del nuovo abbonamento
        LocalDate newStartDate = subscriptionRequestDTO.getStartDate();
        LocalDate newEndDate = newStartDate.plusMonths(subscriptionRequestDTO.getType().getMonths());

        // Controlliamo che il nuovo abbonamento non si sovrapponga a nessuno di quelli già esistenti
        if (athlete.getSubscriptions() != null) {
            for (Subscription existingSub : athlete.getSubscriptions()) {
                boolean overlaps = !newStartDate.isAfter(existingSub.getEnd_date()) && 
                                   !newEndDate.isBefore(existingSub.getStart_date());
                
                if (overlaps) {
                    throw new IllegalStateException("Impossibile aggiungere l'abbonamento: si sovrappone con un abbonamento esistente (dal " 
                            + existingSub.getStart_date() + " al " + existingSub.getEnd_date() + ").");
                }
            }
        }

        Subscription sub = subscriptionMapper.toEntity(subscriptionRequestDTO);
        athleteDAO.createNewSubscription(athlete.getIdUser(), sub);
        return subscriptionMapper.toDto(sub, athlete.getIdUser());
    }

    @Transactional
    public Subscription getActiveSubscription(String tax_code) {
        return athleteDAO.getActiveSubscription(tax_code);
    }

    // vedi lo storico delle sottoscrizioni di un atleta
    @Transactional
    public List<Subscription> getSubscriptions(Long id) {
        return athleteDAO.getSubscriptions(id);
    }

    @Transactional
    public SubscriptionResponseDTO getActiveSubscriptionDTO(String tax_code) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code); // Recupero entità
        if (athlete == null) return null;

        return athlete.getSubscriptions().stream()
                .filter(Subscription::isActive) // Usa la logica del Domain Model
                .findFirst()
                .map(sub -> subscriptionMapper.toDto(sub, athlete.getIdUser())) // Passo l'ID dell'atleta
                .orElse(null);
    }


}
