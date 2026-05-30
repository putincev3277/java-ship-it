package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final List<T> parcels;
    private final double maxWeight; // максимальный допустимый вес коробки
    private double currentWeight;   // текущий вес коробки

    // Конструктор коробки для посылок.

    public ParcelBox(double maxWeight) {
        this.maxWeight = maxWeight;
        this.parcels = new ArrayList<>();
        this.currentWeight = 0.0;
    }

    // Добавляет посылку в коробку, если не превышен максимальный вес.

    public void addParcel(T parcel) {
        if (currentWeight + parcel.weight > maxWeight) {
            System.out.println("Предупреждение: не удалось добавить посылку " +
                    parcel.getDescription() + ". Превышен максимальный вес коробки.");
            return;
        }
        parcels.add(parcel);
        currentWeight += parcel.weight;
        System.out.println("Посылка " + parcel.getDescription() + " успешно добавлена в коробку.");
    }

    // Возвращает список всех посылок в коробке.

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }
}
