package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

@ApplicationScoped
public class PersonalTrainerMapper {

    public PersonalTrainer toEntity(PersonalTrainerRequestDTO dto) {
        return new PersonalTrainer(
                dto.getTax_code(),
                dto.getName(),
                dto.getSurname(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getPhone_number(),
                dto.getBirth_date(),
                dto.getSalary(),
                dto.getStartDate(),
                dto.getEndDate()
        );
    }


    public PersonalTrainerResponseDTO toDto(PersonalTrainer pt){
        PersonalTrainerResponseDTO response = new PersonalTrainerResponseDTO();
        response.setName(pt.getName());
        response.setSurname(pt.getSurname());
        response.setEmail(pt.getEmail());
        response.setPhone_number(pt.getPhone_number());
        response.setActive(pt.isActive());
        response.setStartDate(pt.getStartDate());
        response.setEndDate(pt.getEndDate());

        return response;
    }


}
