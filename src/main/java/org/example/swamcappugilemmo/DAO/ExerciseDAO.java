package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Exercise;

@ApplicationScoped
public class ExerciseDAO {

    @PersistenceContext
    private EntityManager em;

    public Exercise findById(Long exerciseId) {
        return em.find(Exercise.class, exerciseId);
    }
}
