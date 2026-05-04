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

    // crea un nuovo abbonamento per un atleta, se ne ha già uno attivo, non ha senso crearne un altro, quindi prima di creare un nuovo abbonamento, bisogna verificare se l'atleta ha già un abbonamento attivo
    @Transactional
    public void crateNewSubscription(SubscriptionRequestDTO subscriptionRequestDTO) {
        Athlete athlete = athleteDAO.findById(subscriptionRequestDTO.getIdUser());
        Subscription activeSub = athleteDAO.getActiveSubscription(athlete.getTax_code());
        if (activeSub != null && activeSub.isActive()) {
            throw new IllegalStateException("Impossibile aggiungere un nuovo abbonamento: quello attuale è ancora attivo fino al " + activeSub.getEnd_date());
        }
        if (subscriptionRequestDTO.getStartDate() == null || subscriptionRequestDTO.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data di inizio non valida.");
        }
        Subscription sub = subscriptionMapper.toEntity(subscriptionRequestDTO);
        athleteDAO.createNewSubscription(athlete.getIdUser(), sub);
    }

    @Transactional
    public Subscription getActiveSubscription(String tax_code) {
        return athleteDAO.getActiveSubscription(tax_code);
    }

    // vedi lo storico delle sottoscrizioni di un atleta
    @Transactional
    public List<Subscription> getSubscriptions(String tax_code) {
        return athleteDAO.getSubscriptions(tax_code);
    }

    @Transactional
    public SubscriptionResponseDTO getActiveSubscriptionDTO(String tax_code) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code); // Recupero entità
        if (athlete == null) return null;

        return athlete.getSubscriptions().stream()
                .filter(Subscription::isActive) // Usa la logica del Domain Model
                .findFirst()
                .map(subscriptionMapper::toDto)
                .orElse(null);
    }


}
