package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Exercise;

import java.util.List;

@ApplicationScoped
public class ExerciseDAO {

    @PersistenceContext
    private EntityManager em;



    public void createExercise(Exercise exercise) {
        em.persist(exercise);
        System.out.println("Esercizio creato");
    }


    public List<Exercise> getAllExercises() {
        return em.createQuery("SELECT e FROM Exercise e", Exercise.class).getResultList();
    }


    public Exercise getExerciseById(Long exerciseId) {
        Exercise e = em.find(Exercise.class, exerciseId);
        if (e == null) {
            throw new EntityNotFoundException("Exercise with id " + exerciseId + " not found.");
        }
        return e;
    }


    public Exercise updateExercise(Exercise e) {
        Exercise newE = em.merge(e);
        if (newE == null) {
            throw new RuntimeException("Aggiornamento fallito");
        }
        System.out.println("Esercizio modificato correttamente");
        return newE;
    }


    public Exercise deleteExercise(Long exerciseId) {
        Exercise e = getExerciseById(exerciseId);
        em.remove(e);
        System.out.println("Esercizio eliminato");
        return e;
    }


}
