package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseWorkoutPlanRequestDTO {
    private int numSeries;
    private int numRepetitions;
    private double weight;
    private Long exerciseId;
    private Long workoutPlanId;
}
