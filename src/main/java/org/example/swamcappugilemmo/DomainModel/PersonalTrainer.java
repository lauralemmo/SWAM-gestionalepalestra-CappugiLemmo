package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "personalTrainer")
public class PersonalTrainer extends User{

    private int salary;
    private boolean active = false;
    private LocalDate startDate;
    private LocalDate endDate;

    public PersonalTrainer(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                           LocalDate birth_date, int salary, LocalDate startDate, LocalDate endDate) {
        super(name, surname, username, password, email, phone_number, tax_code, birth_date);
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected PersonalTrainer() {}

}
