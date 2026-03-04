package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;



@Entity
@Table(name = "athlete")
public class Athlete extends User{
    private String height;
    private String weight;
    @Embedded
    private Subscription subscription;
    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true)
    private WorkoutPlan workoutPlan;
    @OneToMany
    @JoinColumn(name = "athlete_id")
    private ArrayList<Booking> bookings;


    public Athlete(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                   LocalDate birth_date, String height, String weight, Subscription subscription) {
        super(name, surname, username, password, email, phone_number, tax_code, birth_date);
        this.height = height;
        this.weight = weight;
        this.subscription = subscription;
        this.bookings = new ArrayList<>();
    }

    protected Athlete() {}

}
