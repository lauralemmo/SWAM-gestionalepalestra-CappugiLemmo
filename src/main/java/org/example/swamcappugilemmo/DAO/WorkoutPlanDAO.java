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
        return null;
    }
}
