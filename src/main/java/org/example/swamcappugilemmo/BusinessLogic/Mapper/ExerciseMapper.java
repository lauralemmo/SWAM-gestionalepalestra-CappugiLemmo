package org.example.swamcappugilemmo.BusinessLogic.Mapper;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Exercise;

import java.util.Collections;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExerciseMapper {
    //@Inject
    //private ExerciseWorkoutPlanMapper ewpMapper;

    public Exercise toEntity(ExerciseRequestDTO request){
        Exercise e = new Exercise();
        e.setName(request.getName());
        e.setDescription(request.getDescription());
        return e;
    }


    public ExerciseResponseDTO toDTO(Exercise e){
        ExerciseResponseDTO response = new ExerciseResponseDTO();
        response.setId(e.getIdExercise());
        response.setName(e.getName());
        response.setDescription(e.getDescription());
        /*response.setSpecificExercises(
                e.getSpecificExercises() == null ? Collections.emptyList() :
                        e.getSpecificExercises()
                                .stream()
                                .map(ewpMapper :: toDTO)
                                .collect(Collectors.toList())
        );*/
        return response;
    }


}
