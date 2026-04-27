package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;



//APPENA INIZIATO




public class ExerciseWorkoutPlanMapper {

    public ExerciseWorkoutPlanResponseDTO toDTO(ExerciseWorkoutPlan ewp){
        ExerciseWorkoutPlanResponseDTO response = new ExerciseWorkoutPlanResponseDTO();
        response.setId(ewp.getIdEW());
        return response;
    }

}
