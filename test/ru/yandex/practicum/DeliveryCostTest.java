package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    private StandardParcel standardParcel;
    private PerishableParcel perishableParcel;
    private FragileParcel fragileParcel;

    @BeforeEach
    void setUp() {
        standardParcel = new StandardParcel("Книга", 2, "Москва", 1);
        perishableParcel = new PerishableParcel("Ягоды", 1, "СПб", 5, 7);
        fragileParcel = new FragileParcel("Ваза", 3, "Казань", 10);
    }

    // Тесты для проверки расчёта стоимости доставки

    @Test
    void testStandardParcelCost() {
        // Стандартный сценарий
        assertEquals(4.0, standardParcel.calculateDeliveryCost(), 0.001);
        // Граничный сценарий: нулевой вес
        StandardParcel zeroWeightParcel = new StandardParcel("Пустая", 0, "Москва", 1);
        assertEquals(0.0, zeroWeightParcel.calculateDeliveryCost(), 0.001);
    }

    @Test
    void testPerishableParcelCost() {
        // Стандартный сценарий
        assertEquals(3.0, perishableParcel.calculateDeliveryCost(), 0.001);
        // Граничный сценарий: нулевой вес
        PerishableParcel zeroWeightParcel = new PerishableParcel("Пустая", 0, "СПб", 5, 7);
        assertEquals(0.0, zeroWeightParcel.calculateDeliveryCost(), 0.001);
    }

    @Test
    void testFragileParcelCost() {
        // Стандартный сценарий
        assertEquals(12.0, fragileParcel.calculateDeliveryCost(), 0.001);
        // Граничный сценарий: нулевой вес
        FragileParcel zeroWeightParcel = new FragileParcel("Пустая", 0, "Казань", 10);
        assertEquals(0.0, zeroWeightParcel.calculateDeliveryCost(), 0.001);
    }

    // Тесты для метода isExpired

    @Test
    void testIsExpired() {
        // Стандартный сценарий: посылка не испортилась
        assertFalse(perishableParcel.isExpired(10)); // sendDay=5, timeToLive=7 → 5+7=12 >= 10
        // Стандартный сценарий: посылка испортилась
        assertTrue(perishableParcel.isExpired(13)); // 12 < 13
        // Граничный сценарий: день, когда срок годности истекает
        assertFalse(perishableParcel.isExpired(12)); // 12 == 12 → не испорчена
    }

    // Тесты для ParcelBox

    @Test
    void testParcelBoxAddParcelWithinWeightLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10.0);

        StandardParcel parcel1 = new StandardParcel("Книга 1", 3, "Москва", 1);
        StandardParcel parcel2 = new StandardParcel("Книга 2", 4, "Москва", 2);

        box.addParcel(parcel1);
        box.addParcel(parcel2);

        List<StandardParcel> parcels = box.getAllParcels();
        assertEquals(2, parcels.size());
        assertEquals("Книга 1", parcels.get(0).getDescription());
        assertEquals("Книга 2", parcels.get(1).getDescription());
    }

    @Test
    void testParcelBoxAddParcelExceedsWeightLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5.0);

        StandardParcel smallParcel = new StandardParcel("Журнал", 2, "Москва", 1);
        StandardParcel largeParcel = new StandardParcel("Энциклопедия", 4, "Москва", 2);

        box.addParcel(smallParcel);
        box.addParcel(largeParcel); // Должен быть отклонён из-за превышения веса

        List<StandardParcel> parcels = box.getAllParcels();
        assertEquals(1, parcels.size()); // Только первая посылка добавлена
        assertEquals("Журнал", parcels.get(0).getDescription());
    }

    @Test
    void testParcelBoxEdgeCaseExactWeightLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5.0);

        StandardParcel exactWeightParcel = new StandardParcel("Точная посылка", 5, "Москва", 1);

        box.addParcel(exactWeightParcel);

        List<StandardParcel> parcels = box.getAllParcels();
        assertEquals(1, parcels.size());
        assertEquals("Точная посылка", parcels.get(0).getDescription());
    }
}


