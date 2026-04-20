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
        try {
            Course course = courseDAO.getCourseById(courseId); // Lancia IllegalArgumentException se non trovato
            //Occurrence newOccurrence = new Occurrence(date, hours, course);
            Occurrence newOccurrence = new Occurrence();
            newOccurrence.setDate(date);
            newOccurrence.setHours(hours);
            newOccurrence.setCourse(course);
            occurrenceDAO.createOccurrence(newOccurrence);
        }

        catch (IllegalArgumentException e) {
            // Qui capisci se l'ID del corso era sbagliato
            System.err.println("Errore: Corso con ID " + courseId + " non trovato. " + e.getMessage());
            throw e; // Rilancia per informare il Service
        }

        catch (Exception e) {
            System.err.println("Errore generico durante l'aggiunta dell'occorrenza: " + e.getMessage());
            throw new RuntimeException("Impossibile aggiungere l'occorrenza", e);
        }
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
