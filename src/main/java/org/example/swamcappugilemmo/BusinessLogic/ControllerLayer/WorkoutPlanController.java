package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.WorkoutPlanDAO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

import java.time.LocalDate;

public class WorkoutPlanController {
    @Inject
    private WorkoutPlanDAO workoutPlanDAO;
    @Inject
    private AthleteDAO athleteDAO;

    @Transactional
    public void createWorkoutPlan(LocalDate data, PersonalTrainer personalTrainer, Athlete athlete) {
        Athlete _athlete = athleteDAO.findAthleteByTaxCode(athlete.getTax_code());
        WorkoutPlan workoutPlan = new WorkoutPlan(data, personalTrainer, _athlete);
        _athlete.setWorkoutPlan(workoutPlan);
        workoutPlanDAO.saveWorkoutPlan(workoutPlan);
    }

    // se l'atleta non ha un piano di allenamento, non ha senso aggiornarlo
    @Transactional
    public void updateWorkoutPlan(WorkoutPlan workoutPlan) {
        Athlete _athlete = athleteDAO.findAthleteByTaxCode(workoutPlan.getAthlete().getTax_code());
        if (_athlete.getWorkoutPlan() == null) {
            throw new IllegalArgumentException("Athlete with tax code " + workoutPlan.getAthlete().getTax_code() + " does not have a workout plan to update.");
        }
        workoutPlanDAO.saveWorkoutPlan(workoutPlan);
        _athlete.setWorkoutPlan(workoutPlan);
        workoutPlanDAO.saveWorkoutPlan(workoutPlan);
    }

    @Transactional
    public void deleteWorkoutPlan(Long workoutPlan_id) {
        workoutPlanDAO.deleteWorkoutPlan(workoutPlan_id);
    }


}
