package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import java.util.List;


@ApplicationScoped
public class PersonalTrainerDAO {
    @PersistenceContext
    private EntityManager em;


    public void createPersonalTrainer(PersonalTrainer pt){
        em.persist(pt);
        System.out.println("Nuovo personal trainer aggiunto");
    }


    public PersonalTrainer getPersonalTrainerById(Long id){
        PersonalTrainer pt = em.find(PersonalTrainer.class, id);
        if (pt == null) {
            throw new EntityNotFoundException("Personal trainer with id " + id + " not found.");
        }
        return pt;
    }


    public PersonalTrainer getPersonalTrainerByUsername(String username) {
        PersonalTrainer pt = em.createQuery("SELECT pt FROM PersonalTrainer pt WHERE pt.username = :username", PersonalTrainer.class)
                .setParameter("username", username)
                .getSingleResult();
        if (pt == null) {
            throw new EntityNotFoundException("Personal trainer with username " + username + " not found.");
            }
        return pt;
    }


    public List<PersonalTrainer>  getAllPersonalTrainers(){
        return em.createQuery("SELECT pt FROM PersonalTrainer pt", PersonalTrainer.class).getResultList();
    }


    public PersonalTrainer updatePersonalTrainer(PersonalTrainer pt){
        PersonalTrainer newPt = em.merge(pt);
        if(newPt == null){
            throw new RuntimeException("Update failed");
        }
        System.out.println("Personal trainer aggiornato");
        return newPt;
    }


    public PersonalTrainer deletePersonalTrainer(Long id){
        PersonalTrainer pt = getPersonalTrainerById(id);
        em.remove(pt);
        System.out.println("Personal trainer eliminato");
        return pt;
    }


}
