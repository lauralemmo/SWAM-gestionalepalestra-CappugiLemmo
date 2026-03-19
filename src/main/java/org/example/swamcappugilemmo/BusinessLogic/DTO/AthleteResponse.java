package org.example.swamcappugilemmo.BusinessLogic.DTO;

import org.example.swamcappugilemmo.DomainModel.Athlete;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AthleteResponse {
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
    public AthleteResponse(Athlete athlete) {
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
    }

    // Getter (necessari per la conversione JSON)
    public String getTax_code() { return tax_code; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone_number() { return phone_number; }
    public LocalDate getBirth_date() { return birth_date; }
    public String getHeight() { return height; }
    public String getWeight() { return weight; }
    public List<SubscriptionDTO> getSubscriptions() {
        return subscriptions;
    }
}