package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

@ApplicationScoped
public class ExerciseWorkoutPlanDAO {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void saveExerciseWorkoutPlan(ExerciseWorkoutPlan exerciseWorkoutPlan) {
        em.persist(exerciseWorkoutPlan);
        System.out.println("Nuova esercizio specifico creato");
    }

    @Transactional
    public ExerciseWorkoutPlan findById(Long id) {
        ExerciseWorkoutPlan ewp = em.find(ExerciseWorkoutPlan.class, id);
        if (ewp == null) {
            throw new IllegalArgumentException("ExerciseWorkoutPlan with id " + id + " not found.");
        }
        return ewp;
    }

    @Transactional
    public ExerciseWorkoutPlan update(ExerciseWorkoutPlan exerciseWorkoutPlan) {
        ExerciseWorkoutPlan ewp = em.merge(exerciseWorkoutPlan);
        if(ewp == null){
            throw new RuntimeException("Aggiornamento fallito");
        }
        System.out.println("Esercizio della scheda modificato con successo");
        return ewp;
    }

    @Transactional
    public ExerciseWorkoutPlan deleteExerciseWorkoutPlan(Long id) {
        ExerciseWorkoutPlan ewpToDelete = findById(id);
        if (ewpToDelete != null) {
            em.remove(ewpToDelete);
        } else {
            throw new IllegalArgumentException("ExerciseWorkoutPlan with id " + id + " not found.");
        }
        System.out.println("Esercizio della scheda eliminato");
        return ewpToDelete;
    }
}
