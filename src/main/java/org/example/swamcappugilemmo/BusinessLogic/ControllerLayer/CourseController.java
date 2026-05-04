package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.CourseMapper;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DomainModel.*;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class CourseController {
    @Inject
    CourseDAO courseDAO;
    @Inject
    private CourseMapper courseMapper;
    @Inject
    private PersonalTrainerDAO personalTrainerDAO;


    @Transactional
    public CourseResponseDTO addCourse(CourseRequestDTO request) {
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerById(request.getIdPersonalTrainer());
        Course newC = courseMapper.toEntity(request,pt);
        courseDAO.createCourse(newC);
        return courseMapper.toDto(newC);
    }


    @Transactional
    public CourseResponseDTO getCourseById(Long id) {
        Course c = courseDAO.getCourseById(id);
        if (c != null) {
            return courseMapper.toDto(c);
        } else {
            return null;
        }
    }


    @Transactional
    public CourseResponseDTO getCourseByOccurrence(Occurrence o){
        Course c = courseDAO.getCourseByOccurrence(o);
        if (c != null) {
            return courseMapper.toDto(c);
        } else{
            return null;
        }
    }


    @Transactional
    public List<CourseResponseDTO> getCoursesByPersonalTrainer(PersonalTrainer pt){
        List<Course> cc = courseDAO.getCoursesByPersonalTrainer(pt);
        if (cc != null) {
            return cc
                    .stream()
                    .map(courseMapper::toDto)
                    .collect(Collectors.toList());
        } else{
            return null;
        }
    }


    @Transactional
    public List<CourseResponseDTO> getAllCourses(){
        return courseDAO.getAllCourses()
                .stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request){
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerById(request.getIdPersonalTrainer());
        Course course = courseDAO.getCourseById(id);
        course.setName(request.getName());
        course.setNumMembers(request.getNumMembers());
        course.setNumMax(request.getNumMax());
        course.setPersonalTrainer(pt);
        Course updatedCourse = courseDAO.updateCourse(course);
        return courseMapper.toDto(updatedCourse);
    }


    @Transactional
    public void deleteCourse(Long id){
        courseDAO.deleteCourse(id);
    }

}
