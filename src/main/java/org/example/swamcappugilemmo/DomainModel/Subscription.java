package org.example.swamcappugilemmo.DomainModel;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class Subscription {
    private LocalDate start_date;
    private LocalDate end_date;
    private String price;
    private String type;
    private boolean active;

    public Subscription(LocalDate start_date, LocalDate end_date, String price, String type, boolean active) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.type = type;
        this.active = active;
    }

    protected Subscription() {}

}
