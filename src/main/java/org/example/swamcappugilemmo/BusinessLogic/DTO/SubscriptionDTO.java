package org.example.swamcappugilemmo.BusinessLogic.DTO;

import org.example.swamcappugilemmo.DomainModel.Subscription;

// In org.example.swamcappugilemmo.BusinessLogic.ServiceLayer
public class SubscriptionDTO {
    private String type;
    private String price;
    private String startDate;
    private String endDate;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}