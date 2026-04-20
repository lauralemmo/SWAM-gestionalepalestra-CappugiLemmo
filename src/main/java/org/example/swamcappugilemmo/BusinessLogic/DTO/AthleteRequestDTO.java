package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;

@Setter
@Getter
public class AthleteRequestDTO {
    private String tax_code;
    // Getter e Setter necessari per la conversione JSON
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private LocalDate birth_date;
    private String height;
    private String weight;
    private SubscriptionType subscriptionType;
    private LocalDate startDate;

}
