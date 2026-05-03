package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;

    private String name;
    private int numMembers;
    private int numMax;


    @OneToMany(mappedBy = "course")
    private List<Occurrence> occurrences;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_tax_code")
    private PersonalTrainer personalTrainer;

    @OneToMany(mappedBy = "course")
    private List<Booking> bookings;


    public void addBookings(Booking booking) {
        this.bookings.add(booking);
    }
}