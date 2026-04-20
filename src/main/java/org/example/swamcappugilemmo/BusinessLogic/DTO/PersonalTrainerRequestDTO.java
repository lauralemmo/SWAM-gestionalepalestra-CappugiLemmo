package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PersonalTrainerRequestDTO {

    private String tax_code;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private LocalDate birth_date;
    private int salary;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;

}
