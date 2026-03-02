package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;



@MappedSuperclass   //impedisce query su User
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)   //in alternativa
//@Entity
public abstract class User {

    @Id
    @Column(length = 16)
    private String tax_code;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private LocalDate birth_date;

    public User(String tax_code, String name, String surname, String username, String password, String email,
                String phone_number, LocalDate birth_date){
        this.tax_code = tax_code;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
    }

    protected User() {}


}
