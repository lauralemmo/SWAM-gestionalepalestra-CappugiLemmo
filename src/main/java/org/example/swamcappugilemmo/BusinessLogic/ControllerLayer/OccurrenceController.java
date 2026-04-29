package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.OccurrenceMapper;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.OccurrenceDAO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.Security.Secured;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class OccurrenceController {
    @Inject
    private OccurrenceDAO occurrenceDAO;
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private OccurrenceMapper occurrenceMapper;

    @Secured({"ADMIN"}) // Solo l' admin può aggiungere nuove date
    @Transactional
    public OccurrenceDTO addOccurrence(Long courseId, LocalDate date, LocalTime hours) {
        try {
            Course course = courseDAO.getCourseById(courseId); // Lancia IllegalArgumentException se non trovato
            //Occurrence newOccurrence = new Occurrence(date, hours, course);
            Occurrence newOccurrence = new Occurrence();
            newOccurrence.setDate(date);
            newOccurrence.setHours(hours);
            newOccurrence.setCourse(course);
            occurrenceDAO.createOccurrence(newOccurrence);
            return occurrenceMapper.toDTO(newOccurrence);
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

   @Transactional
    public void updateOccurrenceSecurely(Long idOccurrence, Occurrence updatedData, String callerUsername, boolean callerRole) {

        // Controlliamo se l'occorrenza esiste
        Occurrence existingOccurrence = occurrenceDAO.getOccurrenceById(idOccurrence);
        if (existingOccurrence == null) {
            throw new IllegalArgumentException("Data lezione non trovata nel database");
        }
        // Troviamo il corso collegato
        Long courseId = existingOccurrence.getCourse().getIdCourse();
        Course course = courseDAO.getCourseById(courseId);

        // Verifichiamo i permessi
        if (!callerRole && !course.getPersonalTrainer().getUsername().equals(callerUsername)) {
            throw new SecurityException("Accesso negato: Puoi modificare solo le date dei TUOI corsi!");
        }

        // Modifica dei dati sicura
        existingOccurrence.setDate(updatedData.getDate());
        existingOccurrence.setHours(updatedData.getHours());

        // Salvataggio
        occurrenceDAO.updateOccurrence(existingOccurrence);
    }

}
