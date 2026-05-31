package ru.yandex.practicum.delivery;

public enum ParcelType {
    STANDARD(2.0),
    PERISHABLE(3.0),
    FRAGILE(4.0);

    private final double baseCost;

    ParcelType(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getBaseCost() {
        return baseCost;
    }
}