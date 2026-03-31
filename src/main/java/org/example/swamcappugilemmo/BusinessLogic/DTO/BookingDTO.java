package org.example.swamcappugilemmo.BusinessLogic.DTO;

import java.time.LocalDate;

//DTO per creare una prenotazione, non contiene l' utente, lo conterrà solo quando un utente la selezione
public class BookingDTO {

    private LocalDate date;
    private String course_name;
    private String tax_code;
    private String course;

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

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;   }

}
