package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;



@Entity
@Table(name = "athlete")
public class Athlete extends User{
    private String height;
    private String weight;


    public Athlete(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                   LocalDate birth_date, String height, String weight) {
        super(name, surname, username, password, email, phone_number, tax_code, birth_date);
        this.height = height;
        this.weight = weight;
    }

    protected Athlete() {}

}
