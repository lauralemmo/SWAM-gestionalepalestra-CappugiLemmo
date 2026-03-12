package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "occurrence")
public class Occurrence {
    //Se è Annotato come @Embeddable non può avere un Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOccurrence;

    private LocalDate date;
    private LocalTime hours;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;



    public Occurrence(LocalDate date, LocalTime hours, Course course) {
        this.date = date;
        this.hours = hours;
        this.course =  course;
    }

    protected Occurrence() {}




    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Occurrence that = (Occurrence) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(hours, that.hours);
    }*/

}
