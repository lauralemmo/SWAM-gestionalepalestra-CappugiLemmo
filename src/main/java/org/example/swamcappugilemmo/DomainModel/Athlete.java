package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "athlete")
public class Athlete extends User{
    private String height;
    private String weight;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "subscription", joinColumns = @JoinColumn(name = "athlete_id", nullable = false))
    private List<Subscription> subscriptions;

    @OneToOne(mappedBy = "athlete", cascade = CascadeType.ALL , orphanRemoval = true)
    private WorkoutPlan workoutPlan;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;




    public void addSubscription(Subscription subscription){
        this.subscriptions.add(subscription);
    }

    public void addBookings(Booking booking) {
        this.bookings.add(booking);
    }


}
