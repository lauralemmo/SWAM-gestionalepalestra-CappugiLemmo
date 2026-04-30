package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Exercise;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

import java.util.Collections;
import java.util.stream.Collectors;


@ApplicationScoped
public class ExerciseWorkoutPlanMapper {

    public ExerciseWorkoutPlan toEntity(ExerciseWorkoutPlanRequestDTO request, Exercise exercise, WorkoutPlan workoutPlan) {
        ExerciseWorkoutPlan ewp = new ExerciseWorkoutPlan();
        ewp.setNumSeries(request.getNumSeries());
        ewp.setNumRepetitions(request.getNumRepetitions());
        ewp.setWeight(request.getWeight());
        ewp.setExercise(exercise);
        ewp.setWorkoutPlan(workoutPlan);
        return ewp;
    }


    public ExerciseWorkoutPlanResponseDTO toDTO(ExerciseWorkoutPlan ewp){
        ExerciseWorkoutPlanResponseDTO response = new ExerciseWorkoutPlanResponseDTO();
        response.setId(ewp.getIdEW());
        response.setNumSeries(ewp.getNumSeries());
        response.setNumRepetitions(ewp.getNumRepetitions());
        response.setWeight(ewp.getWeight());
        response.setExerciseId(ewp.getExercise().getIdExercise());
        response.setWorkoutPlanId(ewp.getWorkoutPlan().getIdWorkoutPlan());
        return response;
    }

}
