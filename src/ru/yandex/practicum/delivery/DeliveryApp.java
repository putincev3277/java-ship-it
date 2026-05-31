package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp { // Привет, Ирек! спасибо, за комментария!)

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();

    // Список посылок, поддерживающих трекинг (хрупкие посылки)
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    // Три коробки для разных типов посылок
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(20.0);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(15.0);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(10.0);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportAllTrackable();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Сообщить о местоположении всех трекинговых посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("\n Выберите тип посылки:");
        System.out.println("1 — Стандартная ");
        System.out.println("2 — Скоропортящаяся ");
        System.out.println("3 — Хрупкая ");
        int typeChoice = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        System.out.print("Введите вес (целое число): ");
        int weight = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите адрес доставки: ");
        String deliveryAddress = scanner.nextLine();
        System.out.print("Введите день отправки (число): ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (typeChoice) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(standardParcel);
                standardBox.addParcel(standardParcel);
                System.out.println("Стандартная посылка добавлена.");
                break;
            case 2:
                System.out.print("Введите срок годности в днях: ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                allParcels.add(perishableParcel);
                perishableBox.addParcel(perishableParcel);
                System.out.println("Скоропортящаяся посылка добавлена.");
                break;
            case 3:
                FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(fragileParcel);
                fragileBox.addParcel(fragileParcel);
                trackableParcels.add(fragileParcel); // добавляем в список трекинговых
                System.out.println("Хрупкая посылка добавлена.");
                break;
            default:
                System.out.println("Неверный тип посылки.");
        }
    }


    private static void sendParcels() {

        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        // Посчитать общую стоимость всех доставок и вывести на экран
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для расчёта стоимости.");
            return;
        }
        double totalCost = 0;
        System.out.println("Стоимость доставки посылок:");
        for (int i = 0; i < allParcels.size(); i++) {
            double cost = allParcels.get(i).calculateDeliveryCost();
            totalCost += cost;
            System.out.println("Посылка " + (i + 1) + ": " + cost + " руб.");
        }
        System.out.println("Общая стоимость: " + totalCost + " руб.");
    }

    private static void reportAllTrackable() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет трекинговых посылок.");
            return;
        }

        System.out.print("Введите новое местоположение: ");
        String newLocation = scanner.nextLine();

        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
    }
     // Метод для показа содержимого коробок

    private static void showBoxContents() {
        System.out.println("Выберите тип коробки для просмотра:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Скоропортящиеся посылки");
        System.out.println("3 — Хрупкие посылки");
        int boxChoice = Integer.parseInt(scanner.nextLine());

        switch (boxChoice) {
            case 1:
                System.out.println("Содержимое коробки со стандартными посылками:");
                for (StandardParcel parcel : standardBox.getAllParcels()) {
                    System.out.println("- " + parcel.getDescription());
                }
                break;
            case 2:
                System.out.println("Содержимое коробки с скоропортящимися посылками:");
                for (PerishableParcel parcel : perishableBox.getAllParcels()) {
                    System.out.println("- " + parcel.getDescription());
                }
                break;
            case 3:
                System.out.println("Содержимое коробки с хрупкими посылками:");
                for (FragileParcel parcel : fragileBox.getAllParcels()) {
                    System.out.println("- " + parcel.getDescription());
                }
                break;
            default:
                System.out.println("Неверный выбор коробки.");
        }
    }
}

