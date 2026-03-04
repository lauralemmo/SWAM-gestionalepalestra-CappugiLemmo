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
    @OneToOne
    private Course course;


    public Booking(Long idBooking, LocalDate date, Course course) {
        this.idBooking = idBooking;
        this.date = date;
        this.course = course;
    }

    protected Booking() {}

}
