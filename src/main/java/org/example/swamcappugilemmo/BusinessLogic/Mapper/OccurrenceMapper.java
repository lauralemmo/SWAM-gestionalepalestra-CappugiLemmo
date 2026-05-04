package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;

@ApplicationScoped
public class OccurrenceMapper {
        public OccurrenceResponseDTO toDTO(Occurrence occurrence) {
            if (occurrence == null) {
                return null;
            }
            OccurrenceResponseDTO dto = new OccurrenceResponseDTO();
            dto.setIdOccurrence(occurrence.getIdOccurrence());
            dto.setDate(occurrence.getDate());
            dto.setHours(occurrence.getHours());
            if(occurrence.getCourse() != null) {
                dto.setCourseId(occurrence.getCourse().getIdCourse());
                dto.setCourseName(occurrence.getCourse().getName());
            }
            return dto;
        }

        public Occurrence toEntity(OccurrenceRequestDTO occurrenceRequestDTO, Course course) {
            if (occurrenceRequestDTO == null) {
                return null;
            }
            Occurrence occurrence = new Occurrence();
            occurrence.setDate(occurrenceRequestDTO.getDate());
            occurrence.setHours(occurrenceRequestDTO.getHours());
            occurrence.setCourse(course);
            return occurrence;
        }
}