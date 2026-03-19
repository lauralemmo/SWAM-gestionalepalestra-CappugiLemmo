package org.example.swamcappugilemmo.BusinessLogic.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRegistrationRequest;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponse;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Subscription;

import java.util.stream.Collectors;

@ApplicationScoped
public class AthleteMapper {

    // Da DTO a Entità (per la registrazione)
    public Athlete toEntity(AthleteRegistrationRequest request) {
        return new Athlete(
                request.getTax_code(),
                request.getName(),
                request.getSurname(),
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhone_number(),
                request.getBirth_date(),
                request.getHeight(),
                request.getWeight()
        );
    }

    // Da Entità a DTO (per la risposta)
    public AthleteResponse toDto(Athlete athlete) {
        AthleteResponse response = new AthleteResponse();
        response.setTax_code(athlete.getTax_code());
        response.setName(athlete.getName());
        response.setSurname(athlete.getSurname());
        response.setUsername(athlete.getUsername());
        response.setEmail(athlete.getEmail());
        response.setPhone_number(athlete.getPhone_number());
        response.setBirth_date(athlete.getBirth_date());
        response.setHeight(athlete.getHeight());
        response.setWeight(athlete.getWeight());

        // Mappa anche la lista delle sottoscrizioni
        response.setSubscriptions(athlete.getSubscriptions().stream()
                .map(this::toSubscriptionDto)
                .collect(Collectors.toList()));

        return response;
    }

    // Mapper di supporto per la Subscription
    public SubscriptionDTO toSubscriptionDto(Subscription s) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setType(s.getType().name());
        dto.setPrice(s.getPrice());
        dto.setStartDate(s.isActive() ? "Attivo" : "Scaduto"); // Mantiene la tua logica originale
        return dto;
    }
}
