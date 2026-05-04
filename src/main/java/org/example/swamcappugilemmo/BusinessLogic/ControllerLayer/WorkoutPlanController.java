package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.WorkoutPlanMapper;
import org.example.swamcappugilemmo.BusinessLogic.ServiceLayer.WorkoutPlanService;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DAO.WorkoutPlanDAO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//@Dependent
@ApplicationScoped
public class WorkoutPlanController {
    @Inject
    private WorkoutPlanDAO wpDAO;
    @Inject
    private AthleteDAO athleteDAO;
    @Inject
    private PersonalTrainerDAO ptDAO;
    @Inject
    private WorkoutPlanMapper wpMapper;


    @Transactional
    public WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanRequestDTO request) {
        Athlete athlete = athleteDAO.findById(request.getAthleteId());
        PersonalTrainer pt = ptDAO.getPersonalTrainerById(request.getPersonalTrainerId());
        WorkoutPlan wp = wpMapper.toEntity(request, athlete, pt);
        wpDAO.saveWorkoutPlan(wp);
        return wpMapper.toDTO(wp);
    }

    @Transactional
    public WorkoutPlanResponseDTO getWorkoutPlanById(Long id) {
        return wpMapper.toDTO(wpDAO.findById(id));
    }

    @Transactional
    public List<WorkoutPlanResponseDTO> getAllWorkoutPlans() {
        return wpDAO.findAll()
                .stream()
                .map(wpMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkoutPlanResponseDTO updateWorkoutPlan(Long id, WorkoutPlanRequestDTO request) {
        WorkoutPlan wp = wpDAO.findById(id);
        wp.setDate(request.getDate());
        wp.setAthlete(athleteDAO.findById(request.getAthleteId()));
        wp.setPersonalTrainer(ptDAO.getPersonalTrainerById(request.getPersonalTrainerId()));
        WorkoutPlan updatedWp = wpDAO.update(wp);
        return wpMapper.toDTO(updatedWp);
    }

    @Transactional
    public void deleteWorkoutPlan(Long id){
        WorkoutPlan wp = wpDAO.findById(id);
        if (wp == null) {
            throw new IllegalArgumentException("WorkoutPlan with id " + id + " not found");
        }
        wpDAO.deleteWorkoutPlan(id);
    }


}
