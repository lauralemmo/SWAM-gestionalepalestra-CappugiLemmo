package org.example.swamcappugilemmo.DomainModel;

public enum SubscriptionType {
    MONTHLY(1, "9.99"),
    QUARTERLY(3, "25.99"),
    SEMI_ANNUAL(6, "49.99"),
    ANNUAL(12, "89.99");

    private final int months;
    private final String defaultPrice;
    SubscriptionType(int months, String defaultPrice) {
        this.months = months;
        this.defaultPrice = defaultPrice;
    }
    public int getMonths() { return months; }
    public String getDefaultPrice() { return defaultPrice; }
}