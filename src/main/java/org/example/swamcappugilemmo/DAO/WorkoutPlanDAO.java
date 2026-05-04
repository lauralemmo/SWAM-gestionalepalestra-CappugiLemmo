package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.WorkoutPlan;

import java.util.List;

@ApplicationScoped
public class WorkoutPlanDAO {

    @PersistenceContext
    private EntityManager em;



    public void saveWorkoutPlan(WorkoutPlan workoutPlan) {
        em.persist(workoutPlan);
        System.out.println("Nuova scheda di allenamento creata");
    }


    public WorkoutPlan findById(Long id) {
        WorkoutPlan workoutPlan = em.find(WorkoutPlan.class, id);
        if (workoutPlan == null) {
            throw new EntityNotFoundException("WorkoutPlan with id " + id + " not found.");
        }
        return workoutPlan;
    }


    public List<WorkoutPlan> findAll() {
        return em.createQuery("SELECT w FROM WorkoutPlan w", WorkoutPlan.class)
                .getResultList();
    }


    public WorkoutPlan update(WorkoutPlan wp) {
        WorkoutPlan newWp = em.merge(wp);
        if(newWp == null){
            throw new RuntimeException("Aggiornamento fallito");
        }
        System.out.println("Scheda di allenamento modificata");
        return newWp;
    }


    public void deleteWorkoutPlan(Long id) {
        WorkoutPlan workoutPlan = findById(id);
        em.remove(workoutPlan);
        System.out.println("Scheda di allenamento eliminata");

    }

}
