package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRegistrationRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.AthleteMapper;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.CourseMapper;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DomainModel.*;

import java.time.LocalDate;
import java.util.List;

@Dependent
public class CourseController {
    @Inject
    CourseDAO courseDAO;
    @Inject
    private CourseMapper courseMapper;
    @Inject
    private PersonalTrainerDAO personalTrainerDAO;

    public CourseController(){
        this.courseDAO = new CourseDAO();
    }



    /*@Transactional
    public void addCourse(String name, int numMembers, int numMax, PersonalTrainer personalTrainer){
        Course c = new Course(name, numMembers, numMax, personalTrainer);
        courseDAO.createCourse(c);
    }*/

    @Transactional
    public void addCourse(CourseDTO request) {
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerByTaxCode(request.getTaxCode());
        Course newC = courseMapper.toEntity(request,pt);
        courseDAO.createCourse(newC);

    }


    /*@Transactional
    public Course getCourseByName(String name){
        return courseDAO.getCourseByName(name);
    }*/

    @Transactional
    public CourseDTO getCourseByName(String name) {
        Course c = courseDAO.getCourseByName(name);
        if (c != null) {
            return courseMapper.toDto(c);
        } else {
            return null;
        }
    }



    /*@Transactional
    public Course getCourseByOccurrence(Occurrence o){
        return courseDAO.getCourseByOccurrence(o);
    }*/

    @Transactional
    public CourseDTO getCourseByOccurrence(Occurrence o){
        Course c = courseDAO.getCourseByOccurrence(o);
        if (c != null) {
            return courseMapper.toDto(c);
        } else{
            return null;
        }
    }



    /*@Transactional
    public List<Course> getCoursesByPersonalTrainer(PersonalTrainer pt){
        return courseDAO.getCoursesByPersonalTrainer(pt);
    }*/

    @Transactional
    public List<CourseDTO> getCoursesByPersonalTrainer(PersonalTrainer pt){
        List<Course> cc = courseDAO.getCoursesByPersonalTrainer(pt);
        if (cc != null) {
            return null;
            // TODO tolto perche dava errore, va rimesso!
            //return courseMapper.toDto(cc);
        } else{
            return null;
        }
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
