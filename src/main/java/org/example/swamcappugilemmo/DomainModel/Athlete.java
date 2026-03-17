package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "athlete")
public class Athlete extends User{
    private String height;
    private String weight;

    @ElementCollection
    @CollectionTable(name = "subscription", joinColumns = @JoinColumn(name = "athlete_id", nullable = false))
    private List<Subscription> subscriptions;

    @OneToOne(mappedBy = "athlete", cascade = CascadeType.ALL , orphanRemoval = true)
    private WorkoutPlan workoutPlan;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;



    public Athlete(String name, String surname, String username, String password, String email, String phone_number, String tax_code,
                   LocalDate birth_date, String height, String weight, Subscription subscription) {
        super(name, surname, username, password, email, phone_number, tax_code, birth_date);
        this.height = height;
        this.weight = weight;
        this.subscriptions = new ArrayList<>();
        this.subscriptions.add(subscription);
    }

    protected Athlete() {}

    public void addSubscription(Subscription subscription){
        this.subscriptions.add(subscription);
    }
    public List<Subscription> getSubscriptions(){
        return subscriptions;
    }
    public void setWorkoutPlan(WorkoutPlan workoutPlan){
        this.workoutPlan = workoutPlan;
    }
    public WorkoutPlan getWorkoutPlan(){
        return workoutPlan;
    }

}
