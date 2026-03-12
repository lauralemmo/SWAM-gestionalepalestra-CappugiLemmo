package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBooking;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete_id") // Questa sarà la Foreign Key nel DB
    private Athlete athlete;



    public Booking(LocalDate date, Course course, Athlete athlete) {
        this.date = date;
        this.course = course;
        this.athlete = athlete;
    }

    protected Booking() {}

}
