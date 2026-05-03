package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.util.Collections;
import java.util.stream.Collectors;

@ApplicationScoped
public class PersonalTrainerMapper {
    @Inject
    private WorkoutPlanMapper wpMapper;
    private CourseMapper cMapper;

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
        response.setWorkoutPlans(
                pt.getWorkoutPlans() == null ? Collections.emptyList() :
                        pt.getWorkoutPlans()
                                .stream()
                                .map(wpMapper :: toDTO)
                                .collect(Collectors.toList())
        );
        response.setCourses(
                pt.getCourses() == null ? Collections.emptyList() :
                        pt.getCourses()
                                .stream()
                                .map(cMapper :: toDto)
                                .collect(Collectors.toList())
        );
        return response;
    }


}
