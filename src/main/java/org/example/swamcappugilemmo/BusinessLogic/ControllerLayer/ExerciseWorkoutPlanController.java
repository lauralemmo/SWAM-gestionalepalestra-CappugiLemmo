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
            throw new IllegalArgumentException("Riferimenti non validi per la creazione dell'esercizio");
        }
        ExerciseWorkoutPlan details = new ExerciseWorkoutPlan(series, reps, weight, exercise);
        plan.addExercise(details);
        exerciseWorkoutPlanDAO.saveExerciseWorkoutPlan(details);
    }
}
