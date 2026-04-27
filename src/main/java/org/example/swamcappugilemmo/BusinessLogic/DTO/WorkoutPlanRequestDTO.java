package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkoutPlanRequestDTO {
    private LocalDate date;
    private Long athleteId;
    private Long personalTrainerId;
}
