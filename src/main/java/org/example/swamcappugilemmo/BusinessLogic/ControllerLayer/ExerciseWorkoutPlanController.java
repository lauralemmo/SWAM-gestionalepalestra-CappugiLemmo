package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.ExerciseDAO;
import org.example.swamcappugilemmo.DAO.ExerciseWorkoutPlanDAO;
import org.example.swamcappugilemmo.DAO.WorkoutPlanDAO;
import org.example.swamcappugilemmo.DomainModel.Exercise;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

public class ExerciseWorkoutPlanController {
    @Inject
    private WorkoutPlanDAO workoutPlanDAO;
    @Inject
    private ExerciseWorkoutPlanDAO exerciseWorkoutPlanDAO;
    @Inject
    private ExerciseDAO exerciseDAO;

    @Transactional
    public void addExerciseToPlan(Long workoutPlanId, Long exerciseId, int series, int reps, double weight) {
        WorkoutPlan plan = workoutPlanDAO.findById(workoutPlanId);
        Exercise exercise = exerciseDAO.findById(exerciseId);

        if (plan == null || exercise == null) {
            throw new IllegalArgumentException("Reference not found for workout plan id: " + workoutPlanId + " or exercise id: " + exerciseId);
        }
        ExerciseWorkoutPlan details = new ExerciseWorkoutPlan(series, reps, weight, exercise);
        plan.addExercise(details);
        exerciseWorkoutPlanDAO.saveExerciseWorkoutPlan(details);
    }
    @Transactional
    public void updateExerciseInPlan(Long exerciseWorkoutPlanId, int series, int reps, double weight) {
        ExerciseWorkoutPlan details = exerciseWorkoutPlanDAO.findById(exerciseWorkoutPlanId);
        if (details == null) {
            throw new IllegalArgumentException("Exercise details not found for id: " + exerciseWorkoutPlanId);
        }
        details.setNumSeries(series);
        details.setNumRepetitions(reps);
        details.setWeight(weight);
    }

    @Transactional
    public void deleteExerciseInPlan(Long exerciseWorkoutPlan_id) {
        ExerciseWorkoutPlan details = exerciseWorkoutPlanDAO.findById(exerciseWorkoutPlan_id);
        if (details == null) {
            throw new IllegalArgumentException("Exercise details not found for id: " + exerciseWorkoutPlan_id);
        }
        exerciseWorkoutPlanDAO.deleteExerciseWorkoutPlan(exerciseWorkoutPlan_id);
    }
}
