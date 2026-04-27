package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

import java.util.Collections;
import java.util.stream.Collectors;

public class WorkoutPlanMapper {
    @Inject
    private ExerciseWorkoutPlanMapper ewpMapper;

    public WorkoutPlan toEntity(WorkoutPlanRequestDTO request, Athlete athlete, PersonalTrainer pt){
        WorkoutPlan wp = new WorkoutPlan();
        wp.setDate(request.getDate());
        wp.setAthlete(athlete);
        wp.setPersonalTrainer(pt);
        return wp;
    }

    public WorkoutPlanResponseDTO toDTO(WorkoutPlan wp){
        WorkoutPlanResponseDTO response = new WorkoutPlanResponseDTO();
        response.setId(wp.getIdWorkoutPlan());
        response.setDate(wp.getDate());
        response.setAthleteId(wp.getAthlete().getIdUser());
        response.setPersonalTrainerId(wp.getPersonalTrainer().getIdUser());
        response.setSpecificExercises(
                wp.getSpecificExercises() == null ? Collections.emptyList() :
                        wp.getSpecificExercises()
                                .stream()
                                .map(ewpMapper :: toDTO)
                                .collect(Collectors.toList())
        );

        return response;
    }


}
