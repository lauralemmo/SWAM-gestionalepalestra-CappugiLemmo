package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;

import java.time.LocalDate;

@Embeddable
public class Subscription {
    private LocalDate start_date;
    private LocalDate end_date;
    private String price;
    @Enumerated(EnumType.STRING)
    private SubscriptionType type;
    @Transient
    private boolean active;

    public Subscription(SubscriptionType type, LocalDate start_date) {
        this.start_date = start_date;
        this.end_date = this.start_date.plusMonths(type.getMonths());
        this.price = type.getDefaultPrice();
        this.type = type;
        this.active = true;
    }

    protected Subscription() {}

    public boolean isActive() {
        this.active = LocalDate.now().isAfter(this.start_date) && LocalDate.now().isBefore(this.end_date);
        return active;

    }
}
