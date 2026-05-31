package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private final int timeToLive; // срок в днях, за который посылка не испортится

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    protected double getBaseCost() {
        return ParcelType.PERISHABLE.getBaseCost();
    }

    public boolean isExpired(int currentDay) {
        return sendDay + timeToLive < currentDay;
    }

}
