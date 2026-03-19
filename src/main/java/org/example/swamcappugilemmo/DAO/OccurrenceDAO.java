package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.util.List;

@ApplicationScoped
public class OccurrenceDAO {
    @PersistenceContext
    private EntityManager em;

    public void createOccurrence(Occurrence occurrence){
        em.persist(occurrence);
        System.out.println("Nuova lezione aggiunta");
    }

    public List<Occurrence> getOccurrencesByCourse(Course course){
        return em.createQuery(
                "SELECT o FROM Occurrence o WHERE o.course = :course", Occurrence.class)
                .setParameter("course", course)
                .getResultList();
    }


}
