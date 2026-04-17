package org.example.swamcappugilemmo.BusinessLogic.DTO;

import org.example.swamcappugilemmo.DomainModel.SubscriptionType;

import java.time.LocalDate;

public class AthleteRequestDTO {
    private String tax_code;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private LocalDate birth_date;
    private String height;
    private String weight;
    private SubscriptionType subscriptionType;
    private LocalDate startDate;

    // Getter e Setter necessari per la conversione JSON
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() {return surname;}
    public void setSurname(String surname) { this.surname = surname; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
    public String getTax_code() { return tax_code; }
    public void setTax_code(String tax_code) { this.tax_code = tax_code; }
    public LocalDate getBirth_date() { return birth_date; }
    public void setBirth_date(LocalDate birth_date) { this.birth_date = birth_date;}
    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
    public SubscriptionType getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(SubscriptionType subscriptionType) { this.subscriptionType = subscriptionType
    ; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate;
    }
}
