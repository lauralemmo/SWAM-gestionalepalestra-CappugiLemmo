package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    public PersonalTrainer getPersonalTrainerByTaxCode(String taxCode){
        PersonalTrainer pt = em.find(PersonalTrainer.class, taxCode);
        if (pt == null) {
            throw new IllegalArgumentException("Personal trainer with tax code " + taxCode + " not found.");
        }
        return pt;
    }

    public List<PersonalTrainer>  getAllPersonalTrainers(){
        return em.createQuery("SELECT pt FROM PersonalTrainer pt", PersonalTrainer.class).getResultList();
    }

    public void updatePersonalTrainer(PersonalTrainer pt){
        PersonalTrainer newPt = em.merge(pt);
        if(newPt == null){
            throw new RuntimeException("Update failed");
        }
        System.out.println("Personal trainer aggiornato");
    }

    public void deletePersonalTrainer(String taxCode){
        PersonalTrainer pt = em.find(PersonalTrainer.class, taxCode);
        if (pt == null) {
            throw new RuntimeException("Personal trainer not found");
        }
        em.remove(pt);
        System.out.println("Personal trainer eliminato");
    }





}
