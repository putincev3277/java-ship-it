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
            System.out.printf(
                    "Предупреждение: не удалось добавить посылку %s. Превышен максимальный вес коробки.%n",
                    parcel.getDescription()
            );
        } else {
            parcels.add(parcel);
            currentWeight += parcel.weight;
            System.out.printf(
                    "Посылка %s успешно добавлена в коробку.%n",
                    parcel.getDescription()
            );
        }
    }

    // Возвращает список всех посылок в коробке.

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }
}
