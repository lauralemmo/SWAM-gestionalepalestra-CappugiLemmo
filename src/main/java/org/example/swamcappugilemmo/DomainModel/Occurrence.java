package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class Occurrence {
   /* @Id Se è Annotato come @Embeddable non può avere un Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOccurrence;*/

    private LocalDate date;

    private LocalTime hours;

    public Occurrence(LocalDate date, LocalTime hours) {
        this.date = date;
        this.hours = hours;

    }

    protected Occurrence() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Occurrence that = (Occurrence) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(hours, that.hours);
    }
}
