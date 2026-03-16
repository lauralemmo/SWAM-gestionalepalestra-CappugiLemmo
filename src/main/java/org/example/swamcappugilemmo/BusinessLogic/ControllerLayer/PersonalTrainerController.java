package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DAO.PersonalTrainerDAO;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;

import java.time.LocalDate;
import java.util.List;


public class PersonalTrainerController {
    @Inject
    PersonalTrainerDAO personalTrainerDAO;

    public PersonalTrainerController(){
        this.personalTrainerDAO = new PersonalTrainerDAO();
    }



    @Transactional
    public void addPersonalTrainer(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                                   LocalDate birth_date, int salary, LocalDate startDate, LocalDate endDate){
        PersonalTrainer pt = new PersonalTrainer(name, surname, username, password, email, phone_number, tax_code, birth_date, salary, startDate, endDate);
        personalTrainerDAO.createPersonalTrainer(pt);
    }

    @Transactional
    public PersonalTrainer getPersonalTrainerByTaxCode(String taxCode){
        return personalTrainerDAO.getPersonalTrainerByTaxCode(taxCode);
    }

    @Transactional
    public List<PersonalTrainer> getAllPersonalTrainer(){
        return personalTrainerDAO.getAllPersonalTrainers();
    }

    @Transactional
    public void updatePersonalTrainer(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                                      LocalDate birth_date, int salary, LocalDate startDate, LocalDate endDate){
        PersonalTrainer pt = new PersonalTrainer(name, surname, username, password, email, phone_number, tax_code, birth_date, salary, startDate, endDate);
        personalTrainerDAO.updatePersonalTrainer(pt);
    }

    @Transactional
    public void deletePersonalTrainer(String taxCode){
        personalTrainerDAO.deletePersonalTrainer(taxCode);
    }


}
