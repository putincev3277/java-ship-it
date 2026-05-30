package ru.yandex.practicum.delivery;

public class StandardParcel extends Parcel {
    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    protected double getBaseCost() {
        return BASE_COST_STANDARD;
    }

}


