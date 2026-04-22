package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.PersonalTrainerMapper;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import org.example.swamcappugilemmo.Security.Secured;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PersonalTrainerController {
    @Inject
    PersonalTrainerDAO personalTrainerDAO;
    @Inject
    PersonalTrainerMapper ptMapper;


    @Transactional
    public PersonalTrainerResponseDTO addPersonalTrainer(PersonalTrainerRequestDTO request){
        PersonalTrainer newPT = ptMapper.toEntity(request);
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        newPT.setPassword(hashedPassword);
        personalTrainerDAO.createPersonalTrainer(newPT);
        return ptMapper.toDto(newPT);
    }

    @Transactional
    public PersonalTrainerResponseDTO getPersonalTrainerById(Long id){
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerById(id);
        return ptMapper.toDto(pt);
    }


    @Transactional
    public List<PersonalTrainerResponseDTO> getAllPersonalTrainers(){
        return personalTrainerDAO.getAllPersonalTrainers()
                .stream()
                .map(ptMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonalTrainerResponseDTO updatePersonalTrainer(Long id, PersonalTrainerRequestDTO request){
        //non si deve creare un nuovo PersonalTrainer, ma recuperare quello esistente e modificarlo
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerById(id);
        pt.setTax_code(request.getTax_code());
        pt.setName(request.getName());
        pt.setSurname(request.getSurname());
        pt.setUsername(request.getUsername());
        pt.setPassword(request.getPassword());
        pt.setEmail(request.getEmail());
        pt.setPhone_number(request.getPhone_number());
        pt.setBirth_date(request.getBirth_date());
        pt.setSalary(request.getSalary());
        pt.setActive(request.isActive());
        pt.setStartDate(request.getStartDate());
        pt.setEndDate(request.getEndDate());

        PersonalTrainer updatedPT = personalTrainerDAO.updatePersonalTrainer(pt);
        return ptMapper.toDto(updatedPT);
    }


    @Transactional
    public PersonalTrainerResponseDTO deletePersonalTrainer(Long id){
        PersonalTrainer pt = personalTrainerDAO.deletePersonalTrainer(id);
        return ptMapper.toDto(pt);
    }





    /*@Transactional
    public void addNewSubscriptionToPersonalTrainer(String taxCode, LocalDate startDate, LocalDate endDate){
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerByTaxCode(taxCode);
        pt.setActive(true);
        pt.setStartDate(startDate);
        pt.setEndDate(endDate);
        personalTrainerDAO.updatePersonalTrainer(pt);
    }*/

}
