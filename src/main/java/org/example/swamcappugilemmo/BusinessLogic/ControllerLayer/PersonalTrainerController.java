package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.PersonalTrainerMapper;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.time.LocalDate;
import java.util.List;

@Dependent
public class PersonalTrainerController {
    @Inject
    PersonalTrainerDAO personalTrainerDAO;
    @Inject
    PersonalTrainerMapper ptMapper;

    public PersonalTrainerController(){
        this.personalTrainerDAO = new PersonalTrainerDAO();
    }



    /*@Transactional
    public void addPersonalTrainer(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                                   LocalDate birth_date, int salary, LocalDate startDate, LocalDate endDate){
        PersonalTrainer pt = new PersonalTrainer(name, surname, username, password, email, phone_number, tax_code, birth_date, salary, startDate, endDate);
        personalTrainerDAO.createPersonalTrainer(pt);
    }*/

    @Transactional
    public void addPersonalTrainer(PersonalTrainerRequestDTO request){
        PersonalTrainer newPT = ptMapper.toEntity(request);
        personalTrainerDAO.createPersonalTrainer(newPT);
    }

    @Transactional
    public void addNewSubscriptionToPersonalTrainer(String taxCode, LocalDate startDate, LocalDate endDate){
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerByTaxCode(taxCode);
        pt.setActive(true);
        pt.setStartDate(startDate);
        pt.setEndDate(endDate);
        personalTrainerDAO.updatePersonalTrainer(pt);
    }


    /*@Transactional
    public PersonalTrainer getPersonalTrainerByTaxCode(String taxCode){
        return personalTrainerDAO.getPersonalTrainerByTaxCode(taxCode);
    }*/

    @Transactional
    public PersonalTrainerResponseDTO getPersonalTrainerByTaxCode(String taxCode){
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerByTaxCode(taxCode);
        return ptMapper.toDto(pt);
    }

    @Transactional
    public List<PersonalTrainer> getAllPersonalTrainer(){
        return personalTrainerDAO.getAllPersonalTrainers();
    }

    @Transactional
    public void updatePersonalTrainer(Long id, String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                                      LocalDate birth_date, int salary, LocalDate startDate, LocalDate endDate){

        //Questo era sbagliato, non si deve creare un nuovo PersonalTrainer, ma recuperare quello esistente e modificarlo
        //PersonalTrainer pt = new PersonalTrainer(name, surname, username, password, email, phone_number, tax_code, birth_date, salary, startDate, endDate);
        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainerById(id);
        pt.setName(name);
        pt.setSurname(surname);
        pt.setUsername(username);
        pt.setPassword(password);
        pt.setEmail(email);
        pt.setPhone_number(phone_number);
        pt.setTax_code(tax_code);
        pt.setBirth_date(birth_date);
        pt.setSalary(salary);
        pt.setStartDate(startDate);
        pt.setEndDate(endDate);
        // Usa il merge
        personalTrainerDAO.updatePersonalTrainer(pt);
    }

    @Transactional
    public void deletePersonalTrainer(String taxCode){
        personalTrainerDAO.deletePersonalTrainer(taxCode);
    }


}
