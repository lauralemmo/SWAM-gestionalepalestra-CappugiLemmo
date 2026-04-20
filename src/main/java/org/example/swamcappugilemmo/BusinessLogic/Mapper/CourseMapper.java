package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseDTO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

@ApplicationScoped
public class CourseMapper {

    public Course toEntity(CourseDTO dto, PersonalTrainer pt){
        Course entity = new Course();
        entity.setName(dto.getName());
        entity.setNumMembers(dto.getNumMembers());
        entity.setNumMax(dto.getNumMax());
        entity.setPersonalTrainer(pt);
        return entity;
//        return new Course(
//                dto.getName(),
//                dto.getNumMembers(),
//                dto.getNumMax(),
//                pt
//        );
    }


    public CourseDTO toDto(Course c){
        CourseDTO dto = new CourseDTO();
        dto.setName(c.getName());
        dto.setNumMembers(c.getNumMembers());
        dto.setNumMax(c.getNumMax());
        return dto;
    }


}
