package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;



@Entity
@Table(name = "athlete")
public class Athlete extends User{
    private String height;
    private String weight;

    @ElementCollection
    @CollectionTable(name = "subscription", joinColumns = @JoinColumn(name = "athlete_id"))
    private ArrayList<Subscription> subscriptions;

    @OneToOne(mappedBy = "athlete", cascade = CascadeType.ALL , orphanRemoval = true)
    private WorkoutPlan workoutPlan;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Booking> bookings;



    public Athlete(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                   LocalDate birth_date, String height, String weight, Subscription subscription) {
        super(name, surname, username, password, email, phone_number, tax_code, birth_date);
        this.height = height;
        this.weight = weight;
        this.subscriptions = new ArrayList<>();
        this.subscriptions.add(subscription);
        this.bookings = new ArrayList<>();
    }

    protected Athlete() {}


    public void createWorkoutPlan(LocalDate date, PersonalTrainer personalTrainer, Athlete athlete){
        this.workoutPlan = new WorkoutPlan(date, personalTrainer, athlete);
    }
    public void addSubscription(Subscription subscription){
        this.subscriptions.add(subscription);
    }

}
