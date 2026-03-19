package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.time.LocalDate;
import java.util.List;

@Dependent
public class CourseController {
    @Inject
    CourseDAO courseDAO;

    public CourseController(){
        this.courseDAO = new CourseDAO();
    }



    @Transactional
    public void addCourse(String name, int numMembers, int numMax, PersonalTrainer personalTrainer){
        Course c = new Course(name, numMembers, numMax, personalTrainer);
        courseDAO.createCourse(c);
    }

    @Transactional
    public Course getCourseByName(String name){
        return courseDAO.getCourseByName(name);
    }

    @Transactional
    public Course getCourseByOccurrence(Occurrence o){
        return courseDAO.getCourseByOccurrence(o);
    }

    @Transactional
    public List<Course> getCoursesByPersonalTrainer(PersonalTrainer pt){
        return courseDAO.getCoursesByPersonalTrainer(pt);
    }

    @Transactional
    public List<Course> getAllCourses(){
        return courseDAO.getAllCourses();
    }

    @Transactional
    public void updateCourse(String name, int numMembers, int numMax, PersonalTrainer personalTrainer){
        Course course = new Course(name, numMembers, numMax, personalTrainer);
        courseDAO.updateCourse(course);
    }

    @Transactional
    public void deleteCourse(String name){
        courseDAO.deleteCourse(name);
    }

}
