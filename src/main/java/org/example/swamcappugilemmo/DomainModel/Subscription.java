package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class Subscription {
    private LocalDate start_date;
    private LocalDate end_date;
    private String price;
    @Enumerated(EnumType.STRING)
    private SubscriptionType type;
    @Transient
    private boolean active;

    public boolean isActive() {
        if (this.start_date == null || this.end_date == null) {
            this.active = false;
            return false;
        }

        LocalDate today = LocalDate.now();
        // È attivo se oggi NON è prima della data di inizio e NON è dopo la data di fine
        this.active = !today.isBefore(this.start_date) && !today.isAfter(this.end_date);

        return active;
    }

}
