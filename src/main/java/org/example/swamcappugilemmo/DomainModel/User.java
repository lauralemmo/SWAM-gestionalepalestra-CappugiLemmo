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

    public User(String name){
        this.name = name;
    }

    protected User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTax_code() {
        return tax_code;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

