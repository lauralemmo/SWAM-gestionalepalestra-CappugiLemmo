package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

//DTO per creare una prenotazione, non contiene l' utente, lo conterrà solo quando un utente la selezione
@Getter
@Setter
public class BookingRequestDTO {
    private LocalDate date;
    private LocalTime hours;
    private Long courseId;
    private Long athleteId;
}
