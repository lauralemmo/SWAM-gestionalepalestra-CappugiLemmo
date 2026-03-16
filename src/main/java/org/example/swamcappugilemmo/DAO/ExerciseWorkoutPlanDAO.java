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
}
