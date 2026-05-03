package org.example.swamcappugilemmo.BusinessLogic.DTO;


import lombok.Getter;
import lombok.Setter;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;

import java.util.List;

@Getter
@Setter
public class ExerciseResponseDTO {
    private Long id;
    private String name;
    private String description;
    //private List<ExerciseWorkoutPlanResponseDTO> specificExercises;
}
