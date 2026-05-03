package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.util.List;

@ApplicationScoped
public class CourseDAO {
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void createCourse(Course course){
        em.persist(course);
        System.out.println("Nuovo corso aggiunto");
    }

    @Transactional
    public Course getCourseById(Long id){
        Course course = em.find(Course.class, id);
        if (course == null) {
            throw new IllegalArgumentException("Course with name " + id + " not found.");
        }
        return course;
    }

    @Transactional
    public Course getCourseByIdforUpdate(Long id){
        return em.find(Course.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

    @Transactional
    public Course getCourseByOccurrence(Occurrence occurrence){
        Course course = em.find(Course.class, occurrence);
        if (course == null) {
            throw new IllegalArgumentException("Course with occurrence " + occurrence + " not found.");
        }
        return course;
    }

    @Transactional
    public List<Course> getCoursesByPersonalTrainer(PersonalTrainer pt){
        return em.createQuery(
                        "SELECT c FROM Course c WHERE c.personalTrainer = :pt", Course.class)
                .setParameter("pt", pt)
                .getResultList();
    }

    @Transactional
    public List<Course> getAllCourses(){
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    @Transactional
    public Course updateCourse(Course course){
        Course newC = em.merge(course);
        if(newC == null){
            throw new RuntimeException("Update failed");
        }
        System.out.println("Corso aggiornato");
        return newC;
    }

    @Transactional
    public Course deleteCourse(String name){
        Course c = em.find(Course.class, name);
        if (c == null) {
            throw new RuntimeException("Course not found");
        }
        em.remove(c);
        System.out.println("Corso eliminato");
        return c;
    }



}

