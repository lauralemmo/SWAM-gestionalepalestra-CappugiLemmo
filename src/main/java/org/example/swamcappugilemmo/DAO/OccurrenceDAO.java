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

    public Occurrence getOccurrenceById(Long id){
        Occurrence occurrence = em.find(Occurrence.class, id);
        if (occurrence == null) {
            throw new IllegalArgumentException("Occurrence with name " + id + " not found.");
        }
        return occurrence;
    }

     public void updateOccurrence(Occurrence occurrence){
         Occurrence newO = em.merge(occurrence);
         if(newO == null){
             throw new RuntimeException("Update failed");
         }
         System.out.println("Lezione aggiornata");
     }

     public void deleteOccurrence(Long id){
         Occurrence o = em.find(Occurrence.class, id);
         if (o == null) {
             throw new IllegalArgumentException("Occurrence with name " + id + " not found.");
         }
         em.remove(o);
         System.out.println("Lezione eliminata");
     }

}
