package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceDTO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;

@ApplicationScoped
public class OccurrenceMapper {
        public OccurrenceDTO toDTO(Occurrence occurrence) {
            if (occurrence == null) {
                return null;
            }
            OccurrenceDTO dto = new OccurrenceDTO();
            dto.setIdOccurrence(occurrence.getIdOccurrence());
            dto.setDate(occurrence.getDate());
            dto.setHours(occurrence.getHours());
            if (occurrence.getCourse() != null) {
                dto.setCourseName(occurrence.getCourse().getName());
            }
            return dto;
        }

    // Ricorda di passare anche l'entità Course quando converti da DTO a Entità, altrimenti non saprai a quale corso associare l'occorrenza
        public Occurrence toEntity(OccurrenceDTO occurrenceDTO, Course course) {
            if (occurrenceDTO == null) {
                return null;
            }
            Occurrence occurrence = new Occurrence();
            occurrence.setDate(occurrenceDTO.getDate());
            occurrence.setHours(occurrenceDTO.getHours());
            occurrence.setCourse(course); // Colleghiamo l'entità corso
            return occurrence;
        }
}
