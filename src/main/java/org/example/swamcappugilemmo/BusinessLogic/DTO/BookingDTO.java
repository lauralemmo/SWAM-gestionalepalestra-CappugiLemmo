package org.example.swamcappugilemmo.BusinessLogic.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

//DTO per creare una prenotazione, non contiene l' utente, lo conterrà solo quando un utente la selezione
public class BookingDTO {

    private String tax_code;
    private Long idCourse;
    private LocalDate date;
    private LocalTime hours;

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTaxCode() {
        return tax_code;
    }
    public void setTaxCode(String tax_code) {
        this.tax_code = tax_code;
    }

    public Long getIdCourse() {
        return idCourse;
    }
    public void setIdCourse(Long course) {
        this.idCourse = course;   }

}
