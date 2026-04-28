package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OccurrenceDTO {
        private Long idOccurrence;
        private LocalDate date;
        private LocalTime hours;
        private String courseName;

}
