package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OccurrenceResponseDTO {
    private Long idOccurrence;
    private DayOfWeek dayOfWeek;
    private LocalTime hours;
    private Long courseId;
    private String courseName;
}