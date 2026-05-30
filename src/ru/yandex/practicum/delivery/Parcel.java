package ru.yandex.practicum.delivery;

public abstract class Parcel {
    // Константы базовой стоимости для разных типов посылок
    protected static final double BASE_COST_STANDARD = 2.0;
    protected static final double BASE_COST_PERISHABLE = 3.0;
    protected static final double BASE_COST_FRAGILE = 4.0;

    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;

    }

    public String getDescription() {
        return description;
    }

    protected abstract double getBaseCost();

    public double calculateDeliveryCost() {
        return weight * getBaseCost();
    }

    public void packageItem() {

        System.out.println("Посылка " + description + " упакована");

    }

    public void deliver() {

        System.out.println("Посылка " + description + ", доставлена по адресу " + deliveryAddress);

    }

}
