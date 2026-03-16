package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

@ApplicationScoped
public class WorkoutPlanDAO {

    @PersistenceContext
    private EntityManager em;

    public void saveWorkoutPlan(WorkoutPlan workoutPlan) {
        em.persist(workoutPlan);
    }

    public WorkoutPlan findById(Long workoutPlanId) {
        WorkoutPlan workoutPlan = em.find(WorkoutPlan.class, workoutPlanId);
        if (workoutPlan == null) {
            throw new IllegalArgumentException("WorkoutPlan with id " + workoutPlanId + " not found.");
        }
        return workoutPlan;
    }

    public void deleteWorkoutPlan(Long workoutPlanId) {
        WorkoutPlan workoutPlan = findById(workoutPlanId);
        em.remove(workoutPlan);
    }
}
