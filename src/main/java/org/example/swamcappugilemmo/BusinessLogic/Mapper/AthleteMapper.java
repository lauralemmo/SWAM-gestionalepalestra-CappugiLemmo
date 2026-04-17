package org.example.swamcappugilemmo.BusinessLogic.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;

import java.util.stream.Collectors;

@ApplicationScoped
public class AthleteMapper {
    @Inject
    private SubscriptionMapper subscriptionMapper;

    // Da DTO a Entità (per la registrazione)
    public Athlete toEntity(AthleteRequestDTO request) {
        /*return new Athlete(
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
        );*/
        Athlete athlete = new Athlete();
        athlete.setName(request.getName());
        athlete.setSurname(request.getSurname());
        athlete.setUsername(request.getUsername());
        athlete.setPassword(request.getPassword());
        athlete.setEmail(request.getEmail());
        athlete.setPhone_number(request.getPhone_number());
        athlete.setBirth_date(request.getBirth_date());
        athlete.setHeight(request.getHeight());
        athlete.setWeight(request.getWeight());
        return athlete;
    }

    // Da Entità a DTO (per la risposta)
    public AthleteResponseDTO toDto(Athlete athlete) {
        AthleteResponseDTO response = new AthleteResponseDTO();
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
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList()));

        return response;
    }


}
