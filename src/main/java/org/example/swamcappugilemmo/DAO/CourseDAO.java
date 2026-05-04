package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.util.List;

@ApplicationScoped
public class CourseDAO {
    @PersistenceContext
    private EntityManager em;



    public void createCourse(Course course){
        em.persist(course);
        System.out.println("Nuovo corso aggiunto");
    }


    public Course getCourseById(Long id){
        Course course = em.find(Course.class, id);
        if (course == null) {
            throw new IllegalArgumentException("Course with id " + id + " not found.");
        }
        return course;
    }


    public Course getCourseByIdforUpdate(Long id){
        return em.find(Course.class, id);
    }


    public Course getCourseByOccurrence(Occurrence occurrence){
        Course course = em.find(Course.class, occurrence);
        if (course == null) {
            throw new IllegalArgumentException("Course with occurrence " + occurrence + " not found.");
        }
        return course;
    }


    public List<Course> getCoursesByPersonalTrainer(PersonalTrainer pt){
        return em.createQuery(
                        "SELECT c FROM Course c WHERE c.personalTrainer = :pt", Course.class)
                .setParameter("pt", pt)
                .getResultList();
    }


    public List<Course> getAllCourses(){
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }


    public Course updateCourse(Course course){
        Course newC = em.merge(course);
        if(newC == null){
            throw new RuntimeException("Update failed");
        }
        System.out.println("Corso aggiornato");
        return newC;
    }


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
