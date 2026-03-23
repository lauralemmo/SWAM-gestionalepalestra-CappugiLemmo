package org.example.swamcappugilemmo.BusinessLogic.DTO;

import org.example.swamcappugilemmo.DomainModel.Subscription;

import java.time.LocalDate;

// In org.example.swamcappugilemmo.BusinessLogic.ServiceLayer
public class SubscriptionDTO {
    private String type;
    private String price;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}