package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionDTO;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import org.example.swamcappugilemmo.DomainModel.Subscription;

@ApplicationScoped
public class CourseMapper {

    public Course toEntity(CourseDTO dto, PersonalTrainer pt){
        return new Course(
                dto.getName(),
                dto.getNumMembers(),
                dto.getNumMax(),
                pt
        );
    }


    public CourseDTO toDto(Course c){
        CourseDTO dto = new CourseDTO();
        dto.setName(c.getName());
        dto.setNumMembers(c.getNumMembers());
        dto.setNumMax(c.getNumMax());
        return dto;
    }


}
