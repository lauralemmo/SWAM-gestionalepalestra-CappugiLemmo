package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;

@ApplicationScoped
public class ExerciseWorkoutPlanDAO {

    @PersistenceContext
    private EntityManager em;

    public void saveExerciseWorkoutPlan(ExerciseWorkoutPlan exerciseWorkoutPlan) {
        em.persist(exerciseWorkoutPlan);
    }
    public ExerciseWorkoutPlan findById(Long exerciseWorkoutPlan_id) {
        ExerciseWorkoutPlan exerciseWorkoutPlan = em.find(ExerciseWorkoutPlan.class, exerciseWorkoutPlan_id);
        if (exerciseWorkoutPlan == null) {
            throw new IllegalArgumentException("ExerciseWorkoutPlan with id " + exerciseWorkoutPlan_id + " not found.");
        }
        return exerciseWorkoutPlan;
    }
    public void deleteExerciseWorkoutPlan(Long exerciseWorkoutPlan_id) {
        ExerciseWorkoutPlan exerciseWorkoutPlanToDelete = findById(exerciseWorkoutPlan_id);
        if (exerciseWorkoutPlanToDelete != null) {
            em.remove(exerciseWorkoutPlanToDelete);
        } else {
            throw new IllegalArgumentException("ExerciseWorkoutPlan with id " + exerciseWorkoutPlan_id + " not found.");
        }
    }
}
