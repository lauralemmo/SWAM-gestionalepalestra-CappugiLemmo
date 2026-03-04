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
    //private Course course;

    public Booking(Long idBooking, LocalDate date) {
        this.idBooking = idBooking;
        this.date = date;
    }

    protected Booking() {}

}
