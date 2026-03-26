package org.example.swamcappugilemmo.BusinessLogic.DTO;

import java.time.LocalDate;

public class PersonalTrainerRequestDTO {
    private String tax_code;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private LocalDate birth_date;
    private int salary;
    private boolean active = false;
    private LocalDate startDate;
    private LocalDate endDate;


    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getSurname() {return surname;}

    public void setSurname(String surname) {this.surname = surname;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhone_number() {return phone_number;}

    public void setPhone_number(String phone_number) {this.phone_number = phone_number;}

    public boolean isActive() {return active;}

    public void setActive(boolean active) {this.active = active;}

    public LocalDate getStartDate() {return startDate;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
}
