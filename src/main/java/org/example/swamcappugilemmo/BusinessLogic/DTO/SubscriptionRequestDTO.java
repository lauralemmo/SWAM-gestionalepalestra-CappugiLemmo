package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;

@Getter
@Setter
public class SubscriptionRequestDTO {
    Long idUser;
    private SubscriptionType type;
    private LocalDate startDate;
}
