package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.ExerciseWorkoutPlanMapper;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.ExerciseDAO;
import org.example.swamcappugilemmo.DAO.ExerciseWorkoutPlanDAO;
import org.example.swamcappugilemmo.DAO.WorkoutPlanDAO;
import org.example.swamcappugilemmo.DomainModel.*;


@ApplicationScoped
public class ExerciseWorkoutPlanController {
    @Inject
    private ExerciseWorkoutPlanDAO ewpDAO;
    @Inject
    private ExerciseDAO exerciseDAO;
    @Inject
    private WorkoutPlanDAO workoutPlanDAO;
    @Inject
    private ExerciseWorkoutPlanMapper ewpMapper;


    @Transactional
    public ExerciseWorkoutPlanResponseDTO addExerciseToPlan(ExerciseWorkoutPlanRequestDTO request) {
        Exercise exercise = exerciseDAO.findById(request.getExerciseId());
        WorkoutPlan plan = workoutPlanDAO.findById(request.getWorkoutPlanId());
        if (plan == null || exercise == null) {
            throw new IllegalArgumentException("Reference not found");
        }
        ExerciseWorkoutPlan ewp = ewpMapper.toEntity(request, exercise, plan);
        ewpDAO.saveExerciseWorkoutPlan(ewp);
        return ewpMapper.toDTO(ewp);
    }


    @Transactional
    public ExerciseWorkoutPlanResponseDTO getEwpById(Long id) {
        return ewpMapper.toDTO(ewpDAO.findById(id));
    }


    @Transactional
    public ExerciseWorkoutPlanResponseDTO updateExerciseInPlan(Long Id, ExerciseWorkoutPlanRequestDTO request) {
        ExerciseWorkoutPlan ewp = ewpDAO.findById(Id);
        ewp.setNumSeries(request.getNumSeries());
        ewp.setNumRepetitions(request.getNumRepetitions());
        ewp.setWeight(request.getWeight());
        ewp.setExercise(exerciseDAO.findById(request.getExerciseId()));
        ewp.setWorkoutPlan(workoutPlanDAO.findById(request.getWorkoutPlanId()));
        ExerciseWorkoutPlan updatedEwp = ewpDAO.update(ewp);
        return ewpMapper.toDTO(updatedEwp);
    }


    @Transactional
    public ExerciseWorkoutPlanResponseDTO deleteExerciseInPlan(Long id) {
        return ewpMapper.toDTO(ewpDAO.deleteExerciseWorkoutPlan(id));
    }


}
