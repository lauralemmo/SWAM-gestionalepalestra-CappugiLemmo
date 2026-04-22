package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

@ApplicationScoped
public class PersonalTrainerMapper {

    public PersonalTrainer toEntity(PersonalTrainerRequestDTO dto) {
        PersonalTrainer pt = new PersonalTrainer();
        pt.setTax_code(dto.getTax_code());
        pt.setName(dto.getName());
        pt.setSurname(dto.getSurname());
        pt.setUsername(dto.getUsername());
        pt.setPassword(dto.getPassword());
        pt.setEmail(dto.getEmail());
        pt.setPhone_number(dto.getPhone_number());
        pt.setBirth_date(dto.getBirth_date());
        pt.setSalary(dto.getSalary());
        pt.setActive(dto.isActive());
        pt.setStartDate(dto.getStartDate());
        pt.setEndDate(dto.getEndDate());
        return pt;
    }


    public PersonalTrainerResponseDTO toDto(PersonalTrainer pt){
        PersonalTrainerResponseDTO response = new PersonalTrainerResponseDTO();
        response.setIdUser(pt.getIdUser());
        response.setTax_code(pt.getTax_code());
        response.setName(pt.getName());
        response.setSurname(pt.getSurname());
        response.setUsername(pt.getUsername());
        response.setEmail(pt.getEmail());
        response.setPhone_number(pt.getPhone_number());
        response.setBirth_date(pt.getBirth_date());
        response.setSalary(pt.getSalary());
        response.setActive(pt.isActive());
        response.setStartDate(pt.getStartDate());
        response.setEndDate(pt.getEndDate());
        return response;
    }


}
