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
        this.active = LocalDate.now().isAfter(this.start_date) && LocalDate.now().isBefore(this.end_date);
        return active;

    }

}
