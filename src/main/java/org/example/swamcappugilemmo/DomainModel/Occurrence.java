package org.example.swamcappugilemmo.DomainModel;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "occurrence")
@Getter
@Setter
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

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Occurrence that = (Occurrence) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(hours, that.hours);
    }*/

}
