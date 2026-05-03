package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WorkoutPlanResponseDTO {
    private Long id;
    private LocalDate date;
    private Long athleteId;
    private Long personalTrainerId;
    //private List<ExerciseWorkoutPlanResponseDTO> specificExercises;
}
