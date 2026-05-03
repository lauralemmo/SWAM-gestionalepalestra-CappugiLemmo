package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

@ApplicationScoped
public class CourseMapper {

    public Course toEntity(CourseRequestDTO dto, PersonalTrainer pt){
        Course entity = new Course();
        entity.setName(dto.getName());
        entity.setNumMembers(dto.getNumMembers());
        entity.setNumMax(dto.getNumMax());
        entity.setPersonalTrainer(pt);
        return entity;
    }


    public CourseResponseDTO toDto(Course c){
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(c.getIdCourse());
        dto.setName(c.getName());
        dto.setNumMembers(c.getNumMembers());
        dto.setNumMax(c.getNumMax());
        dto.setIdPersonalTrainer(c.getPersonalTrainer().getIdUser());
        return dto;
    }


}
