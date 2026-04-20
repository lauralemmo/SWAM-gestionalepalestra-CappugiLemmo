package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;

//Sarà principalmente utilizzato per le GET
@Getter
@Setter
public class SubscriptionResponseDTO {
    private SubscriptionType type;
    private String price;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}