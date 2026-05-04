package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.OccurrenceMapper;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.OccurrenceDAO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.Security.Secured;

import java.util.List;
import java.util.stream.Collectors;

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
    public OccurrenceResponseDTO addOccurrence(OccurrenceRequestDTO requestDTO) {
        try {
            Course course = courseDAO.getCourseById(requestDTO.getCourseId()); // Lancia IllegalArgumentException se non trovato
            
            Occurrence newOccurrence = occurrenceMapper.toEntity(requestDTO, course);
            occurrenceDAO.createOccurrence(newOccurrence);
            return occurrenceMapper.toDTO(newOccurrence);
        }

        catch (IllegalArgumentException e) {
            // Qui capisci se l'ID del corso era sbagliato
            System.err.println("Errore: Corso con ID " + requestDTO.getCourseId() + " non trovato. " + e.getMessage());
            throw e; // Rilancia per informare il Service
        }

        catch (Exception e) {
            System.err.println("Errore generico durante l'aggiunta dell'occorrenza: " + e.getMessage());
            throw new RuntimeException("Impossibile aggiungere l'occorrenza", e);
        }
    }

   @Transactional
    public OccurrenceResponseDTO getOccurrenceById(Long id) {
        Occurrence occurrence = occurrenceDAO.getOccurrenceById(id);
        return occurrenceMapper.toDTO(occurrence);
   }

   @Transactional
    public List<OccurrenceResponseDTO> getOccurrencesByCourseId(Long courseId) {
        Course corso = courseDAO.getCourseById(courseId);
        List<Occurrence> occurrences = occurrenceDAO.getOccurrencesByCourse(corso);
        return occurrences.stream()
               .map(occurrenceMapper::toDTO)
               .collect(Collectors.toList());
   }

   @Transactional
    public OccurrenceResponseDTO updateOccurrenceSecurely(Long idOccurrence, OccurrenceRequestDTO updatedData, String callerUsername, boolean callerRole) {

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
        return occurrenceMapper.toDTO(existingOccurrence);
    }

    @Transactional
    public OccurrenceResponseDTO deleteOccurrence(Long idOccurrence) {
        Occurrence occurrence = occurrenceDAO.getOccurrenceById(idOccurrence);
        if (occurrence == null) {
            throw new IllegalArgumentException("Data lezione non trovata nel database");
        }
        OccurrenceResponseDTO dto = occurrenceMapper.toDTO(occurrence);
        occurrenceDAO.deleteOccurrence(idOccurrence);
        return dto;
    }
}
