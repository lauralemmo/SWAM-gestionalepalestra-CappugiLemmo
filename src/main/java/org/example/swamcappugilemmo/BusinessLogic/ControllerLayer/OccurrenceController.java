package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.OccurrenceDAO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class OccurrenceController {
    @Inject
    private OccurrenceDAO occurrenceDAO;
    @Inject
    private CourseDAO courseDAO;

    @Transactional
    public void addOccurrence(Long courseId, LocalDate date, LocalTime hours) {
        Course corso = courseDAO.getCourseById(courseId);
        Occurrence newOccurrence = new Occurrence(date, hours, corso);
        occurrenceDAO.createOccurrence(newOccurrence);
    }

   @Transactional
    public Occurrence getOccurrenceById(Long id) {
        return occurrenceDAO.getOccurrenceById(id);
   }

   @Transactional
    public List<Occurrence> getOccurrencesByCourseId(Long courseId) {
        Course corso = courseDAO.getCourseById(courseId);
        return occurrenceDAO.getOccurrencesByCourse(corso);
   }

}
