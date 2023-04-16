package ru.yandex.yandexlavka.model;

public enum CourierType {
    FOOT(2, 3, 10.0f, 2),
    BIKE(3, 2, 20.0f, 4),
    AUTO(4, 1, 40.0f, 7);

    private final int coefficientEarn;
    private final int coefficientRating;
    private final Float weight;
    private final int quantity;

    CourierType(int coefficientEarn, int coefficientRating, Float weight, int quantity) {
        this.coefficientEarn = coefficientEarn;
        this.coefficientRating = coefficientRating;
        this.weight = weight;
        this.quantity = quantity;
    }

    // ** Getters **
    public int getCoefficientEarn() {
        return coefficientEarn;
    }

    public int getCoefficientRating() {
        return coefficientRating;
    }

    public Float getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }
}
