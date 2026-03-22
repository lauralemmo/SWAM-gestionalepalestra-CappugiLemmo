package org.example.swamcappugilemmo.BusinessLogic.DTO;

import java.time.LocalDate;
import java.util.List;

public class AthleteResponseDTO {
    private String tax_code;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phone_number;
    private LocalDate birth_date;
    private String height;
    private String weight;
    private List<SubscriptionDTO> subscriptions;

    // Costruttore che copia i dati dall'Entità Athlete
   /* public AthleteResponse(Athlete athlete) {
        this.tax_code = athlete.getTax_code();
        this.name = athlete.getName();
        this.surname = athlete.getSurname();
        this.username = athlete.getUsername();
        this.email = athlete.getEmail();
        this.phone_number = athlete.getPhone_number();
        this.birth_date = athlete.getBirth_date();
        this.height = athlete.getHeight();
        this.weight = athlete.getWeight();
        this.subscriptions = athlete.getSubscriptions().stream()
                .map(SubscriptionDTO::new)
                .collect(Collectors.toList());
    }*/

    // Getter (necessari per la conversione JSON)
    public String getTax_code() { return tax_code; }
    public void setTax_code(String tax_code) { this.tax_code = tax_code; }
    public String getName() { return name; }
    public  void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone_number() { return phone_number; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
    public LocalDate getBirth_date() { return birth_date; }
    public void setBirth_date(LocalDate birth_date) { this.birth_date = birth_date; }
    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
    public List<SubscriptionDTO> getSubscriptions() {
        return subscriptions;
    }
    public void setSubscriptions(List<SubscriptionDTO> subscriptions) {
        this.subscriptions = subscriptions;
    }
}