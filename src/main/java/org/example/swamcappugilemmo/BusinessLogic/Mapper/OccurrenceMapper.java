package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceDTO;
import org.example.swamcappugilemmo.DomainModel.Occurrence;

public class OccurrenceMapper {
        public OccurrenceDTO toDTO(Occurrence occurrence) {
            if (occurrence == null) {
                return null;
            }
            OccurrenceDTO dto = new OccurrenceDTO();
            dto.setDate(occurrence.getDate());
            dto.setHours(occurrence.getHours());
            if (occurrence.getCourse() != null) {
                dto.setCourseName(occurrence.getCourse().getName());
            }
            return dto;
        }

        public Occurrence toEntity(OccurrenceDTO occurrenceDTO) {
            if (occurrenceDTO == null) {
                return null;
            }
            Occurrence occurrence = new Occurrence();
            occurrence.setDate(occurrenceDTO.getDate());
            occurrence.setHours(occurrenceDTO.getHours());
            // La gestione del course va fatta a parte, perché serve un Course già esistente
            return occurrence;
        }

}
