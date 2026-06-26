package org.example.swamcappugilemmo.DomainModel;

public enum SubscriptionType {
    MONTHLY(1, "49.99"),
    QUARTERLY(3, "119.99"),
    SEMI_ANNUAL(6, "219.99"),
    ANNUAL(12, "369.99");

    private final int months;
    private final String defaultPrice;
    SubscriptionType(int months, String defaultPrice) {
        this.months = months;
        this.defaultPrice = defaultPrice;
    }
    public int getMonths() { return months; }
    public String getDefaultPrice() { return defaultPrice; }
}